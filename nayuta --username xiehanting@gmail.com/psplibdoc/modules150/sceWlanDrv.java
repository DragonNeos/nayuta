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

public class sceWlanDrv implements HLEModule {
	@Override
	public String getName() { return "sceWlanDrv"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceWlanDevIsPowerOnFunction, 0x93440B11);
			mm.addFunction(sceWlanGetSwitchStateFunction, 0xD7763699);
			mm.addFunction(sceWlanGetEtherAddrFunction, 0x0C622081);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceWlanDevIsPowerOnFunction);
			mm.removeFunction(sceWlanGetSwitchStateFunction);
			mm.removeFunction(sceWlanGetEtherAddrFunction);
			
		}
	}
	
	
	public void sceWlanDevIsPowerOn(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceWlanDevIsPowerOn [0x93440B11]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceWlanGetSwitchState(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceWlanGetSwitchState [0xD7763699]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceWlanGetEtherAddr(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceWlanGetEtherAddr [0x0C622081]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction sceWlanDevIsPowerOnFunction = new HLEModuleFunction("sceWlanDrv", "sceWlanDevIsPowerOn") {
		@Override
		public final void execute(Processor processor) {
			sceWlanDevIsPowerOn(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceWlanDrvModule.sceWlanDevIsPowerOn(processor);";
		}
	};
    
	public final HLEModuleFunction sceWlanGetSwitchStateFunction = new HLEModuleFunction("sceWlanDrv", "sceWlanGetSwitchState") {
		@Override
		public final void execute(Processor processor) {
			sceWlanGetSwitchState(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceWlanDrvModule.sceWlanGetSwitchState(processor);";
		}
	};
    
	public final HLEModuleFunction sceWlanGetEtherAddrFunction = new HLEModuleFunction("sceWlanDrv", "sceWlanGetEtherAddr") {
		@Override
		public final void execute(Processor processor) {
			sceWlanGetEtherAddr(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceWlanDrvModule.sceWlanGetEtherAddr(processor);";
		}
	};
    
};
