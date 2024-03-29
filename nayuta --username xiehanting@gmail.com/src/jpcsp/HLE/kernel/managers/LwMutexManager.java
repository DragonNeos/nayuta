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

import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_LWMUTEX_NOT_FOUND;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_LWMUTEX_LOCKED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_LWMUTEX_RECURSIVE_NOT_ALLOWED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_LWMUTEX_UNLOCKED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_LWMUTEX_UNLOCK_UNDERFLOW;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_DELETE;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_STATUS_RELEASED;
import static jpcsp.HLE.kernel.types.SceKernelErrors.ERROR_KERNEL_WAIT_TIMEOUT;
import static jpcsp.HLE.kernel.types.SceKernelThreadInfo.PSP_THREAD_READY;
import static jpcsp.HLE.kernel.types.SceKernelThreadInfo.PSP_WAIT_LWMUTEX;

import java.util.HashMap;
import java.util.Iterator;

import jpcsp.Memory;
import jpcsp.Processor;
import jpcsp.HLE.Modules;
import jpcsp.HLE.kernel.types.IWaitStateChecker;
import jpcsp.HLE.kernel.types.SceKernelLwMutexInfo;
import jpcsp.HLE.kernel.types.SceKernelThreadInfo;
import jpcsp.HLE.kernel.types.ThreadWaitInfo;
import jpcsp.HLE.modules.ThreadManForUser;
import jpcsp.util.Utilities;

import org.apache.log4j.Logger;

public class LwMutexManager {

    protected static Logger log = Modules.getLogger("ThreadManForUser");

    private HashMap<Integer, SceKernelLwMutexInfo> lwMutexMap;
    private LwMutexWaitStateChecker lwMutexWaitStateChecker;

    private final static int PSP_LWMUTEX_ATTR_FIFO = 0;
    private final static int PSP_LWMUTEX_ATTR_PRIORITY = 0x100;
    private final static int PSP_LWMUTEX_ATTR_ALLOW_RECURSIVE = 0x200;

    public void reset() {
        lwMutexMap = new HashMap<Integer, SceKernelLwMutexInfo>();
        lwMutexWaitStateChecker = new LwMutexWaitStateChecker();
    }

    private boolean removeWaitingThread(SceKernelThreadInfo thread) {
        // Update numWaitThreads
        SceKernelLwMutexInfo info = lwMutexMap.get(thread.wait.LwMutex_id);
        if (info != null) {
            info.numWaitThreads--;
            if (info.numWaitThreads < 0) {
                log.warn("removing waiting thread " + Integer.toHexString(thread.uid) + ", lwmutex " + Integer.toHexString(info.uid) + " numWaitThreads underflowed");
                info.numWaitThreads = 0;
            }
            return true;
        }
        return false;
    }

    public void onThreadWaitTimeout(SceKernelThreadInfo thread) {
        // Untrack
        if (removeWaitingThread(thread)) {
            // Return WAIT_TIMEOUT
            thread.cpuContext._v0 = ERROR_KERNEL_WAIT_TIMEOUT;
        } else {
            log.warn("LwMutex deleted while we were waiting for it! (timeout expired)");
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
        if (thread.isWaitingForType(PSP_WAIT_LWMUTEX)) {
            // decrement numWaitThreads
            removeWaitingThread(thread);
        }
    }

    private void onLwMutexDeleted(int lwmid) {
        ThreadManForUser threadMan = Modules.ThreadManForUserModule;
        boolean reschedule = false;

        for (Iterator<SceKernelThreadInfo> it = threadMan.iterator(); it.hasNext();) {
            SceKernelThreadInfo thread = it.next();
            if (thread.isWaitingForType(PSP_WAIT_LWMUTEX) &&
                    thread.wait.LwMutex_id == lwmid) {
                thread.cpuContext._v0 = ERROR_KERNEL_WAIT_DELETE;
                threadMan.hleChangeThreadState(thread, PSP_THREAD_READY);
                reschedule = true;
            }
        }
        // Reschedule only if threads waked up.
        if (reschedule) {
            threadMan.hleRescheduleCurrentThread();
        }
    }

    private void onLwMutexModified(SceKernelLwMutexInfo info) {
        ThreadManForUser threadMan = Modules.ThreadManForUserModule;
        boolean reschedule = false;

        if ((info.attr & PSP_LWMUTEX_ATTR_PRIORITY) == PSP_LWMUTEX_ATTR_FIFO) {
            for (Iterator<SceKernelThreadInfo> it = Modules.ThreadManForUserModule.iterator(); it.hasNext();) {
                SceKernelThreadInfo thread = it.next();
                if (thread.isWaitingForType(PSP_WAIT_LWMUTEX) &&
                        thread.wait.LwMutex_id == info.uid &&
                        tryLockLwMutex(info, thread.wait.LwMutex_count, thread)) {
                    // New thread is taking control of LwMutex.
                    info.threadid = thread.uid;
                    // Update numWaitThreads
                    info.numWaitThreads--;
                    // Return success or failure
                    thread.cpuContext._v0 = 0;
                    // Wakeup
                    threadMan.hleChangeThreadState(thread, PSP_THREAD_READY);
                    reschedule = true;
                }
            }
        } else if ((info.attr & PSP_LWMUTEX_ATTR_PRIORITY) == PSP_LWMUTEX_ATTR_PRIORITY) {
            for (Iterator<SceKernelThreadInfo> it = Modules.ThreadManForUserModule.iteratorByPriority(); it.hasNext();) {
                SceKernelThreadInfo thread = it.next();
                if (thread.isWaitingForType(PSP_WAIT_LWMUTEX) &&
                        thread.wait.LwMutex_id == info.uid &&
                        tryLockLwMutex(info, thread.wait.LwMutex_count, thread)) {
                    // New thread is taking control of LwMutex.
                    info.threadid = thread.uid;
                    // Update numWaitThreads
                    info.numWaitThreads--;
                    // Return success or failure
                    thread.cpuContext._v0 = 0;
                    // Wakeup
                    threadMan.hleChangeThreadState(thread, PSP_THREAD_READY);
                    reschedule = true;
                }
            }
        }
        // Reschedule only if threads waked up.
        if (reschedule) {
            Modules.ThreadManForUserModule.hleRescheduleCurrentThread();
        }
    }

    private boolean tryLockLwMutex(SceKernelLwMutexInfo info, int count, SceKernelThreadInfo thread) {
        if (info.lockedCount == 0) {
            // If the lwmutex is not locked, allow this thread to lock it.
            info.threadid = thread.uid;
            info.lockedCount += count;
            return true;
        } else if (info.threadid == thread.uid) {
            // If the lwmutex is already locked, but it's trying to be locked by the same thread
            // that acquired it initially, check if recursive locking is allowed.
            // If not, return an error.
            if (((info.attr & PSP_LWMUTEX_ATTR_ALLOW_RECURSIVE) == PSP_LWMUTEX_ATTR_ALLOW_RECURSIVE)) {
                info.lockedCount += count;
                return true;
            }
        }
        return false;
    }

    private int hleKernelLockLwMutex(int uid, int count, int timeout_addr, boolean wait, boolean doCallbacks) {
        SceKernelLwMutexInfo info = lwMutexMap.get(uid);
        if (info == null) {
            log.warn(String.format("hleKernelLockLwMutex uid=%d, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b -  - unknown UID", uid, count, timeout_addr, wait, doCallbacks));
            return ERROR_KERNEL_LWMUTEX_NOT_FOUND;
        }

        ThreadManForUser threadMan = Modules.ThreadManForUserModule;
        SceKernelThreadInfo currentThread = threadMan.getCurrentThread();
        if (!tryLockLwMutex(info, count, currentThread)) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("hleKernelLockLwMutex %s, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - fast check failed", info.toString(), count, timeout_addr, wait, doCallbacks));
            }
            if (wait && info.threadid != currentThread.uid) {
                // Failed, but it's ok, just wait a little
                info.numWaitThreads++;
                // Wait on a specific lwmutex
                currentThread.wait.LwMutex_id = uid;
                currentThread.wait.LwMutex_count = count;
                threadMan.hleKernelThreadEnterWaitState(PSP_WAIT_LWMUTEX, uid, lwMutexWaitStateChecker, timeout_addr, doCallbacks);
            } else {
                if ((info.attr & PSP_LWMUTEX_ATTR_ALLOW_RECURSIVE) != PSP_LWMUTEX_ATTR_ALLOW_RECURSIVE) {
                    return ERROR_KERNEL_LWMUTEX_RECURSIVE_NOT_ALLOWED;
                }
                return ERROR_KERNEL_LWMUTEX_LOCKED;
            }
        } else {
            // Success, do not reschedule the current thread.
            if (log.isDebugEnabled()) {
                log.debug(String.format("hleKernelLockLwMutex %s, count=%d, timeout_addr=0x%08X, wait=%b, doCallbacks=%b - fast check succeeded", info.toString(), count, timeout_addr, wait, doCallbacks));
            }
        }

        return 0;
    }

    public int sceKernelCreateLwMutex(int workAreaAddr, int name_addr, int attr, int count, int option_addr) {
        Memory mem = Processor.memory;

        String name = Utilities.readStringNZ(mem, name_addr, 32);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelCreateLwMutex (workAreaAddr='" + Integer.toHexString(workAreaAddr) + "', name='" + name + "', attr=0x" + Integer.toHexString(attr) + ", count=0x" + Integer.toHexString(count) + ", option_addr=0x" + Integer.toHexString(option_addr) + ")");
        }

        SceKernelLwMutexInfo info = new SceKernelLwMutexInfo(workAreaAddr, name, count, attr);
        lwMutexMap.put(info.uid, info);

        // If the initial count is 0, the lwmutex is not acquired.
        if(count > 0) {
            info.threadid = Modules.ThreadManForUserModule.getCurrentThreadID();
        }

        // Return 0 in case of no error, do not return the UID of the created mutex
        return 0;
    }

    public int sceKernelDeleteLwMutex(int workAreaAddr) {
        Memory mem = Processor.memory;

        int uid = mem.read32(workAreaAddr);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelDeleteLwMutex (workAreaAddr='" + Integer.toHexString(workAreaAddr) + ")");
        }

        SceKernelLwMutexInfo info = lwMutexMap.remove(uid);
        if (info == null) {
            log.warn("sceKernelDeleteLwMutex unknown UID " + Integer.toHexString(uid));
            return ERROR_KERNEL_LWMUTEX_NOT_FOUND;
        }

        mem.write32(workAreaAddr, 0);  // Clear uid.
        onLwMutexDeleted(uid);

        return 0;
    }

    public int sceKernelLockLwMutex(int workAreaAddr, int count, int timeout_addr) {
        Memory mem = Processor.memory;

        int uid = mem.read32(workAreaAddr);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelLockLwMutex redirecting to hleKernelLockLwMutex");
        }
        return hleKernelLockLwMutex(uid, count, timeout_addr, true, false);
    }

    public int sceKernelLockLwMutexCB(int workAreaAddr, int count, int timeout_addr) {
        Memory mem = Processor.memory;

        int uid = mem.read32(workAreaAddr);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelLockLwMutexCB redirecting to hleKernelLockLwMutex");
        }
        return hleKernelLockLwMutex(uid, count, timeout_addr, true, true);
    }

    public int sceKernelTryLockLwMutex(int workAreaAddr, int count) {
        Memory mem = Processor.memory;

        int uid = mem.read32(workAreaAddr);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelTryLockLwMutex redirecting to hleKernelLockLwMutex");
        }
        return hleKernelLockLwMutex(uid, count, 0, false, false);
    }

    public int sceKernelUnlockLwMutex(int workAreaAddr, int count) {
        Memory mem = Processor.memory;

        int uid = mem.read32(workAreaAddr);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelUnlockLwMutex (workAreaAddr=0x" + Integer.toHexString(workAreaAddr) + ", count=" + count + ")");
        }

        SceKernelLwMutexInfo info = lwMutexMap.get(uid);
        if (info == null) {
            log.warn("sceKernelUnlockLwMutex unknown uid");
            return ERROR_KERNEL_LWMUTEX_NOT_FOUND;
        }
        if (info.lockedCount == 0) {
            log.debug("sceKernelUnlockLwMutex not locked");
            return ERROR_KERNEL_LWMUTEX_UNLOCKED;
        }
        if (info.lockedCount < 0) {
            log.warn("sceKernelUnlockLwMutex underflow");
            return ERROR_KERNEL_LWMUTEX_UNLOCK_UNDERFLOW;
        }

        info.lockedCount -= count;
        if (info.lockedCount == 0) {
            onLwMutexModified(info);
        }

        return 0;
    }

    public int sceKernelReferLwMutexStatus(int workAreaAddr, int addr) {
        Memory mem = Processor.memory;

        int uid = mem.read32(workAreaAddr);

        if (log.isDebugEnabled()) {
            log.debug("sceKernelReferLwMutexStatus (workAreaAddr=0x" + Integer.toHexString(workAreaAddr) + ", addr=0x" + addr + ")");
        }

        SceKernelLwMutexInfo info = lwMutexMap.get(uid);
        if (info == null) {
            log.warn("sceKernelReferLwMutexStatus unknown UID " + Integer.toHexString(uid));
            return ERROR_KERNEL_LWMUTEX_NOT_FOUND;
        }
        if (!Memory.isAddressGood(addr)) {
            log.warn("sceKernelReferLwMutexStatus bad address 0x" + Integer.toHexString(addr));
            return -1;
        }

        info.write(mem, addr);

        return 0;
    }

    public int sceKernelReferLwMutexStatusByID(int uid, int addr) {
        Memory mem = Processor.memory;

        if (log.isDebugEnabled()) {
            log.debug("sceKernelReferLwMutexStatus (uid=0x" + Integer.toHexString(uid) + ", addr=0x" + addr + ")");
        }

        SceKernelLwMutexInfo info = lwMutexMap.get(uid);
        if (info == null) {
            log.warn("sceKernelReferLwMutexStatus unknown UID " + Integer.toHexString(uid));
            return ERROR_KERNEL_LWMUTEX_NOT_FOUND;
        }
        if (!Memory.isAddressGood(addr)) {
            log.warn("sceKernelReferLwMutexStatus bad address 0x" + Integer.toHexString(addr));
            return -1;
        }

        info.write(mem, addr);

        return 0;
    }

    private class LwMutexWaitStateChecker implements IWaitStateChecker {

        @Override
        public boolean continueWaitState(SceKernelThreadInfo thread, ThreadWaitInfo wait) {
            // Check if the thread has to continue its wait state or if the lwmutex
            // has been unlocked during the callback execution.
            SceKernelLwMutexInfo info = lwMutexMap.get(wait.LwMutex_id);
            if (info == null) {
                thread.cpuContext._v0 = ERROR_KERNEL_LWMUTEX_NOT_FOUND;
                return false;
            }

            // Check the lwmutex.
            if (tryLockLwMutex(info, wait.LwMutex_count, thread)) {
                info.numWaitThreads--;
                thread.cpuContext._v0 = 0;
                return false;
            }

            return true;
        }
    }
    public static final LwMutexManager singleton = new LwMutexManager();

    private LwMutexManager() {
    }
}