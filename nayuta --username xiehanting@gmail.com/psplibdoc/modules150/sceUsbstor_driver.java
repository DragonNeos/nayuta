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

public class sceUsbstor_driver implements HLEModule {
	@Override
	public String getName() { return "sceUsbstor_driver"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceUsbstorGetStatusFunction, 0x60066CFE);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceUsbstorGetStatusFunction);
			
		}
	}
	
	
	public void sceUsbstorGetStatus(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceUsbstorGetStatus [0x60066CFE]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction sceUsbstorGetStatusFunction = new HLEModuleFunction("sceUsbstor_driver", "sceUsbstorGetStatus") {
		@Override
		public final void execute(Processor processor) {
			sceUsbstorGetStatus(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceUsbstor_driverModule.sceUsbstorGetStatus(processor);";
		}
	};
    
};
