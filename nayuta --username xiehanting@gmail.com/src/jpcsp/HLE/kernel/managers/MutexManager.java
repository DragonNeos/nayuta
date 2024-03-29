/*
This file is part of jpcsp.

Jpcsp is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jpcsp is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */
package jpcsp.HLE.kernel.managers;

import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_MUTEX_NOT_FOUND;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_MUTEX_LOCKED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_MUTEX_RECURSIVE_NOT_ALLOWED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_MUTEX_UNLOCKED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_MUTEX_UNLOCK_UNDERFLOW;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_CANCELLED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_DELETE;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_STATUS_RELEASED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_TIMEOUT;
import static jpcsp.HLE.kernel.types.SceKernelThreadInfo.PSP_THREAD_READY;
import static jpcsp.HLE.kernel.types.SceKernelThreadInfo.PSP_WAIT_MUTEX;

import java.util.HashMap;
import java.util.Iterator;

import jpcsp.Emulator;
import jpcsp.Memory;
import jpcsp.Processor;
import jpcsp.Allegrex.CpuState;
import jpcsp.HLE.Modules;
import jpcsp.HLE.kernel.types.IWaitStateChecker;
import jpcsp.HLE.kernel.types.SceKernelErrors;
import jpcsp.HLE.kernel.types.SceKernelMutexInfo;
import jpcsp.HLE.kernel.types.SceKernelThreadInfo;
import jpcsp.HLE.kernel.types.ThreadWaitInfo;
import jpcsp.HLE.modules.ThreadManForUser;
import jpcsp.util.Utilities;

import org.apache.log4j.Logger;

public class MutexManager {

    protected static Logger log = Modules.getLogger("ThreadManForUser");

    private HashMap<Integer, SceKernelMutexInfo> mutexMap;
    private MutexWaitStateChecker mutexWaitStateChecker;

    private final static int PSP_MUTEX_ATTR_FIFO = 0;
    private final static int PSP_MUTEX_ATTR_PRIORITY = 0x100;
    private final static int PSP_MUTEX_ATTR_ALLOW_RECURSIVE = 0x200;

    public void reset() {
        mutexMap = new HashMap<Integer, SceKernelMutexInfo>();
        mutexWaitStateChecker = new MutexWaitStateChecker();
    }

    /** Don't call this unless thread.waitType == PSP_WAIT_MUTEX
     * @return true if the thread was waiting on a valid mutex */
    private boolean removeWaitingThread(SceKernelThreadInfo thread) {
        // Update numWaitThreads
        SceKernelMutexInfo info = mutexMap.get(thread.wait.Mutex_id);
        if (info != null) {
            info.numWaitThreads--;
            if (info.numWaitThreads < 0) {
                log.warn("removing waiting thread " + Integer.toHexString(thread.uid) + ", mutex " + Integer.toHexString(info.uid) + " numWaitThreads underflowed");
                info.numWaitThreads = 0;
            }
            return true;
        }
        return false;
    }

    /** Don't call this unless thread.wait.waitingOnMutex == true */
    public void onThreadWaitTimeout(SceKernelThreadInfo thread) {
        // Untrack
        if (removeWaitingThread(thread)) {
            // Return WAIT_TIMEOUT
            thread.cpuContext._v0 = ERROR_KERNEL_WAIT_TIMEOUT;
        } else {
            log.warn("Mutex deleted while we were waiting for it! (timeout expired)");
            // Return WAIT_DELETE
            thread.cpuContext._v0 = ERROR_KERNEL_WAIT_DELETE;
        }
    }

    public void onThreadWaitReleased(SceKernelThreadInfo thread) {
        // Untrack
        if (removeWaitingThread(thread)) {
            // Return ERROR_WAIT_STATUS_RELEASED
            thread.cpuContext._v0 = ERROR_KERNEL_WAIT_STATUS_RELEASED;
        } else {
            log.warn("EventFlag deleted while we were waiting for it!");
            // Return WAIT_DELETE
            thread.cpuContext._v0 = ERROR_KERNEL_WAIT_DELETE;
        }
    }

    public void onThreadDeleted(SceKernelThreadInfo thread) {
        if (thread.isWaitingForType(PSP_WAIT_MUTEX)) {
            // decrement numWaitThreads
            removeWaitingThread(thread);
        }
    }

    private void onMutexDeletedCancelled(int mid, int result) {
        ThreadManForUser threadMan = Modules.ThreadManForUserModule;
        boolean reschedule = false;

        for (Iterator<SceKernelThreadInfo> it = threadMan.iterator(); it.hasNext();) {
            SceKernelThreadInfo thread = it.next();
            if (thread.isWaitingForType(PSP_WAIT_MUTEX) &&
                    thread.wait.Mutex_id == mid) {
                thread.cpuContext._v0 = result;
                threadMan.hleChangeThreadState(thread, PSP_THREAD_READY);
                reschedule = true;
            }
        }
        // Reschedule only if threads waked up.
        if (reschedule) {
            threadMan.hleRescheduleCurrentThread();
        }
    }

    private void onMutexDeleted(int mid) {
        onMutexDeletedCancelled(mid, ERROR_KERNEL_WAIT_DELETE);
    }

    private void onMutexCancelled(int mid) {
        onMutexDeletedCancelled(mid, ERROR_KERNEL_WAIT_CANCELLED);
    }

    private void onMutexModified(SceKernelMutexInfo info) {
        ThreadManForUser threadMan = Modules.ThreadManForUserModule;
        boolean reschedule = false;

        if ((info.attr & PSP_MUTEX_ATTR_PRIORITY) == PSP_MUTEX_ATTR_FIFO) {
            for (Iterator<SceKernelThreadInfo> it = Modules.ThreadManForUserModule.iterator(); it.hasNext();) {
                SceKernelThreadInfo thread = it.next();
                if (thread.isWaitingForType(PSP_WAIT_MUTEX) &&
                        thread.wait.Mutex_id == info.uid &&
                        tryLockMutex(info, thread.wait.Mutex_count, thread)) {
                    // New thread is taking control of Mutex.
                    info.threadid = thread.uid;
                    // Update numWaitThreads
                    info.numWaitThreads--;
                    // Return success or failure
                    thread.cpuContext._v0 = 0;
                    // Wakeup
                    threadMan.hleChangeThreadState(thread, PSP_THREAD_READY);
                }
            }
        } else if ((info.attr & PSP_MUTEX_ATTR_PRIORITY) == PSP_MUTEX_ATTR_PRIORITY) {
            for (Iterator<SceKernelThreadInfo> it = Modules.ThreadManForUserModule.iteratorByPriority(); it.hasNext();) {
                SceKernelThreadInfo thread = it.next();
                if (thread.isWaitingForType(PSP_WAIT_MUTEX) &&
                        thread.wait.Mutex_id == info.uid &&
                        tryLockMutex(info, thread.wait.Mutex_count, thread)) {
                    // New thread is taking control of Mutex.
                    info.threadid = thread.uid;
                    // Update numWaitThreads
                    info.numWaitThreads--;
                    // Return success or failure
                    thread.cpuContext._v0 = 0;
                    // Wakeup
                    threadMan.hleChangeThreadState(thread, PSP_THREAD_READY);
                }
            }
        }
        // Reschedule only if threads waked up.
        if (reschedule) {
            Modules.ThreadManForUserModule.hleRescheduleCurrentThread();
        }
    }

    private boolean tryLockMutex(SceKernelMutexInfo info, int count, SceKernelThreadInfo thread) {
        if (info.lockedCount == 0) {
            // If the mutex is not locked, allow this thread to lock it.
            info.threadid = thread.uid;
            info.lockedCount += count;
            return true;
        } else if (info.threadid == thread.uid) {
            // If the mutex is already locked, but it's trying to be locked by the same thread
            // that acquired it initially, check if recursive locking is allowed.
            // If not, return an error.
            if (((info.attr & PSP_MUTEX_ATTR_ALLOW_RECURSIVE) == PSP_MUTEX_ATTR_ALLOW_RECURSIVE)) {
                info.lockedCount += count;
                return true;
            }
        }
        return false;
    }

    public void sceKernelCreateMutex(int name_addr, int attr, int count, int option_addr) {
        CpuState cpu = Emulator.getProcessor().cpu;
        Memory mem = Processor.memory;

        String name = "";
        if (Memory.isAddressGood(name_addr)) {
            name = Utilities.readStringNZ(mem, name_addr, 32);
        }

        if (log.isDebugEnabled()) {
            log.debug("sceKernelCreateMutex(name='" + name + "',attr=0x" + Integer.toHexString(attr) + ",count=0x" + Integer.toHexString(count) + ",option_addr=0x" + Integer.toHexString(option_addr) + ")");
        }

        SceKernelMutexInfo info = new SceKernelMutexInfo(name, count, attr);
        mutexMap.put(info.uid, info);

        // If the initial count is 0, the mutex is not acquired.
        if (count > 0) {
            info.threadid = Modules.ThreadManForUserModule.getCurrentThreadID();
        }

        cpu._v0 = info.uid;
    }

    public void sceKernelDeleteMutex(int uid) {
        CpuState cpu = Emulator.getProcessor().cpu;

        if (log.isDebugEnabled()) {
            log.debug(String.format("sceKernelDeleteMutex uid=%x", uid));
        }

        SceKernelMutexInfo info = mutexMap.remove(uid);
        if (info == null) {
            log.warn("sceKernelDeleteMutex unknown UID " + Integer.toHexString(uid));
            cpu._v0 = ERROR_KERNEL_MUTEX_NOT_FOUND;
        } else {
            cpu._v0 = 0;
            onMutexDeleted(uid);
        }
    }

    private void hleKernelLockMutex(int uid, int count, int timeout_addr, boolean wait, boolean doCallbacks) {
        CpuState cpu = Emulator.getProcessor().cpu;

        SceKernelMutexInfo info = mutexMap.get(uid);
        if (info == null) {
            log.warn(String.format("hleKernelLockMutex uid=%d, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - unknown UID", uid, count, timeout_addr, wait, doCallbacks));
            cpu._v0 = ERROR_KERNEL_MUTEX_NOT_FOUND;
        } else if (count <= 0) {
            log.warn(String.format("hleKernelLockMutex uid=%d, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - illegal count", uid, count, timeout_addr, wait, doCallbacks));
        	cpu._v0 = SceKernelErrors.ERROR_KERNEL_ILLEGAL_COUNT;
        } else if (count > 1 && (info.attr & PSP_MUTEX_ATTR_ALLOW_RECURSIVE) == 0) {
            log.warn(String.format("hleKernelLockMutex uid=%d, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - illegal count", uid, count, timeout_addr, wait, doCallbacks));
        	cpu._v0 = SceKernelErrors.ERROR_KERNEL_ILLEGAL_COUNT;
        } else {
            ThreadManForUser threadMan = Modules.ThreadManForUserModule;
            SceKernelThreadInfo currentThread = threadMan.getCurrentThread();

            if (!tryLockMutex(info, count, currentThread)) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("hleKernelLockMutex %s, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - fast check failed", info.toString(), count, timeout_addr, wait, doCallbacks));
                }
                if (wait && info.threadid != currentThread.uid) {
                    // Failed, but it's ok, just wait a little
                    info.numWaitThreads++;
                    // Wait on a specific mutex
                    currentThread.wait.Mutex_id = uid;
                    currentThread.wait.Mutex_count = count;
                    threadMan.hleKernelThreadEnterWaitState(PSP_WAIT_MUTEX, uid, mutexWaitStateChecker, timeout_addr, doCallbacks);
                } else {
                    if ((info.attr & PSP_MUTEX_ATTR_ALLOW_RECURSIVE) != PSP_MUTEX_ATTR_ALLOW_RECURSIVE) {
                        cpu._v0 = ERROR_KERNEL_MUTEX_RECURSIVE_NOT_ALLOWED;
                    } else {
                        cpu._v0 = ERROR_KERNEL_MUTEX_LOCKED;
                    }
                }
            } else {
                // Success, do not reschedule the current thread.
                if (log.isDebugEnabled()) {
                    log.debug(String.format("hleKernelLockMutex %s, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - fast check succeeded", info.toString(), count, timeout_addr, wait, doCallbacks));
                }
                cpu._v0 = 0;
            }
        }
    }

    public void sceKernelLockMutex(int uid, int count, int timeout_addr) {
        if (log.isDebugEnabled()) {
            log.debug("sceKernelLockMutex redirecting to hleKernelLockMutex");
        }
        hleKernelLockMutex(uid, count, timeout_addr, true, false);
    }

    public void sceKernelLockMutexCB(int uid, int count, int timeout_addr) {
        if (log.isDebugEnabled()) {
            log.debug("sceKernelLockMutex redirecting to hleKernelLockMutex");
        }
        hleKernelLockMutex(uid, count, timeout_addr, true, true);
    }

    public void sceKernelTryLockMutex(int uid, int count) {
        if (log.isDebugEnabled()) {
            log.debug("sceKernelTryLockMutex redirecting to hleKernelLockMutex");
        }
        hleKernelLockMutex(uid, count, 0, false, false);
    }

    public void sceKernelUnlockMutex(int uid, int count) {
        CpuState cpu = Emulator.getProcessor().cpu;

        if (log.isDebugEnabled()) {
            log.debug("sceKernelUnlockMutex(uid=" + Integer.toHexString(uid) + ", count=" + count + ")");
        }

        SceKernelMutexInfo info = mutexMap.get(uid);
        if (info == null) {
            log.warn("sceKernelUnlockMutex unknown uid");
            cpu._v0 = ERROR_KERNEL_MUTEX_NOT_FOUND;
        } else if (info.lockedCount == 0) {
        	// log only as debug to avoid warning spams on some games
            log.debug("sceKernelUnlockMutex not locked");
            cpu._v0 = ERROR_KERNEL_MUTEX_UNLOCKED;
        } else if ((info.lockedCount - count) < 0) {
            log.warn("sceKernelUnlockMutex underflow");
            cpu._v0 = ERROR_KERNEL_MUTEX_UNLOCK_UNDERFLOW;
        } else {
            info.lockedCount -= count;
            cpu._v0 = 0;
            if (info.lockedCount == 0) {
            	info.threadid = 0;
                onMutexModified(info);
            }
        }
    }

    public void sceKernelCancelMutex(int uid, int newcount, int numWaitThreadAddr) {
        CpuState cpu = Emulator.getProcessor().cpu;
        Memory mem = Memory.getInstance();

        if (log.isDebugEnabled()) {
            log.debug("sceKernelCancelMutex uid=" + Integer.toHexString(uid) + ", newcount=" + newcount + ", numWaitThreadAddr=0x" + Integer.toHexString(numWaitThreadAddr));
        }

        SceKernelMutexInfo info = mutexMap.get(uid);
        if (info == null) {
            log.warn("sceKernelCancelMutex unknown UID " + Integer.toHexString(uid));
            cpu._v0 = ERROR_KERNEL_MUTEX_NOT_FOUND;
        } else if (info.lockedCount == 0) {
            log.warn("sceKernelCancelMutex UID " + Integer.toHexString(uid) + " not locked");
            cpu._v0 = -1;
        } else {
            // Write previous numWaitThreads count.
            if (Memory.isAddressGood(numWaitThreadAddr)) {
                mem.write32(numWaitThreadAddr, info.numWaitThreads);
            }
            // Set new count.
            if (newcount == -1) {
                info.lockedCount = info.initCount;
            } else {
                info.lockedCount = newcount;
            }
            cpu._v0 = 0;
            onMutexCancelled(uid);
        }
    }

    public void sceKernelReferMutexStatus(int uid, int addr) {
        CpuState cpu = Emulator.getProcessor().cpu;

        if (log.isDebugEnabled()) {
            log.debug("sceKernelReferMutexStatus uid=" + Integer.toHexString(uid) + "addr=" + String.format("0x%08X", addr));
        }

        SceKernelMutexInfo info = mutexMap.get(uid);
        if (info == null) {
            log.warn("sceKernelReferMutexStatus unknown UID " + Integer.toHexString(uid));
            cpu._v0 = ERROR_KERNEL_MUTEX_NOT_FOUND;
        } else {
            Memory mem = Memory.getInstance();
            if (Memory.isAddressGood(addr)) {
                info.write(mem, addr);
                cpu._v0 = 0;
            } else {
                log.warn("sceKernelReferMutexStatus bad address 0x" + Integer.toHexString(addr));
                cpu._v0 = -1;
            }
        }
    }

    private class MutexWaitStateChecker implements IWaitStateChecker {

        @Override
        public boolean continueWaitState(SceKernelThreadInfo thread, ThreadWaitInfo wait) {
            // Check if the thread has to continue its wait state or if the mutex
            // has been unlocked during the callback execution.
            SceKernelMutexInfo info = mutexMap.get(wait.Mutex_id);
            if (info == null) {
                thread.cpuContext._v0 = ERROR_KERNEL_MUTEX_NOT_FOUND;
                return false;
            }

            // Check the mutex.
            if (tryLockMutex(info, wait.Mutex_count, thread)) {
                info.numWaitThreads--;
                thread.cpuContext._v0 = 0;
                return false;
            }

            return true;
        }
    }
    public static final MutexManager singleton = new MutexManager();

    private MutexManager() {
    }

}