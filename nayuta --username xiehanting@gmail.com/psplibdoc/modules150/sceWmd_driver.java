/* This autogenerated file is part of jpcsp. */
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

package jpcsp.HLE.modules150;

import jpcsp.HLE.Modules;
import jpcsp.HLE.modules.HLEModule;
import jpcsp.HLE.modules.HLEModuleFunction;
import jpcsp.HLE.modules.HLEModuleManager;

import jpcsp.Memory;
import jpcsp.Processor;

import jpcsp.Allegrex.CpuState; // New-Style Processor

public class sceWmd_driver implements HLEModule {
	@Override
	public String getName() { return "sceWmd_driver"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceWmd_driver_7A0E484CFunction, 0x7A0E484C);
			mm.addFunction(sceWmd_driver_B7CE9041Function, 0xB7CE9041);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceWmd_driver_7A0E484CFunction);
			mm.removeFunction(sceWmd_driver_B7CE9041Function);
			
		}
	}
	
	
	public void sceWmd_driver_7A0E484C(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceWmd_driver_7A0E484C [0x7A0E484C]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceWmd_driver_B7CE9041(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceWmd_driver_B7CE9041 [0xB7CE9041]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction sceWmd_driver_7A0E484CFunction = new HLEModuleFunction("sceWmd_driver", "sceWmd_driver_7A0E484C") {
		@Override
		public final void execute(Processor processor) {
			sceWmd_driver_7A0E484C(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceWmd_driverModule.sceWmd_driver_7A0E484C(processor);";
		}
	};
    
	public final HLEModuleFunction sceWmd_driver_B7CE9041Function = new HLEModuleFunction("sceWmd_driver", "sceWmd_driver_B7CE9041") {
		@Override
		public final void execute(Processor processor) {
			sceWmd_driver_B7CE9041(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceWmd_driverModule.sceWmd_driver_B7CE9041(processor);";
		}
	};
    
};
