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

package jpcsp.HLE.modules200;

import jpcsp.HLE.HLEFunction;
import jpcsp.HLE.TPointer;

import java.util.Calendar;

import jpcsp.Memory;
import jpcsp.Processor;
import jpcsp.Allegrex.CpuState;
import jpcsp.HLE.Modules;
import jpcsp.HLE.kernel.types.ScePspDateTime;

public class sceRtc extends jpcsp.HLE.modules150.sceRtc {

	@HLEFunction(nid = 0x203CEB0D, version = 200)
	public void sceRtcGetLastReincarnatedTime(Processor processor) {
		CpuState cpu = processor.cpu;
        Memory mem = Processor.memory;

        int tick_addr = cpu._a0;

        if (Modules.log.isDebugEnabled()) {
        	Modules.log.debug("sceRtcGetLastReincarnatedTime");
        }
        // Returns the last tick that was saved upon a battery shutdown.
        // Just return our current tick, since there's no need to mimick such behaviour.
        if(Memory.isAddressGood(tick_addr)) {
            mem.write64(tick_addr, hleGetCurrentTick());
        }
        cpu._v0 = 0;
	}

	@HLEFunction(nid = 0x62685E98, version = 200)
	public void sceRtcGetLastAdjustedTime(Processor processor) {
		CpuState cpu = processor.cpu;
        Memory mem = Processor.memory;

        int tick_addr = cpu._a0;

        if (Modules.log.isDebugEnabled()) {
        	Modules.log.debug("sceRtcGetLastAdjustedTime");
        }
        // Returns the last time that was manually set by the user.
        // Just return our current tick, since there's no need to mimick such behaviour.
        if(Memory.isAddressGood(tick_addr)) {
            mem.write64(tick_addr, hleGetCurrentTick());
        }
        cpu._v0 = 0;
	}

	@HLEFunction(nid = 0x1909C99B, version = 200)
	public int sceRtcSetTime64_t(TPointer dateAddr, long time) {
        ScePspDateTime dateTime = ScePspDateTime.fromUnixTime(time);
        dateTime.write(dateAddr);

        return 0;
	}

	@HLEFunction(nid = 0xE1C93E47, version = 200)
	public void sceRtcGetTime64_t(Processor processor) {
		CpuState cpu = processor.cpu;
        Memory mem = Processor.memory;

        int date_addr = cpu._a0;
        int time_addr = cpu._a1;

        if (Memory.isAddressGood(date_addr) && Memory.isAddressGood(time_addr)) {
            ScePspDateTime dateTime = new ScePspDateTime();
            dateTime.read(mem, date_addr);
            Calendar cal = Calendar.getInstance();
            cal.set(dateTime.year, dateTime.month - 1, dateTime.day, dateTime.hour, dateTime.minute, dateTime.second);
            long unixtime = (cal.getTime().getTime() / 1000L);
            log.debug("sceRtcGetTime64_t psptime:" + dateTime + " unixtime:" + unixtime);
            mem.write64(time_addr, unixtime);
            cpu._v0 = 0;
        } else {
            log.warn("sceRtcGetTime64_t bad address " + String.format("0x%08X 0x%08X", date_addr, time_addr));
            cpu._v0 = -1;
        }
	}

}