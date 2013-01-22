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

public class sceNetAdhocMatching implements HLEModule {
	@Override
	public String getName() { return "sceNetAdhocMatching"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceNetAdhocMatchingInitFunction, 0x2A2A1E07);
			mm.addFunction(sceNetAdhocMatchingTermFunction, 0x7945ECDA);
			mm.addFunction(sceNetAdhocMatchingCreateFunction, 0xCA5EDA6F);
			mm.addFunction(sceNetAdhocMatchingStartFunction, 0x93EF3843);
			mm.addFunction(sceNetAdhocMatchingStopFunction, 0x32B156B3);
			mm.addFunction(sceNetAdhocMatchingDeleteFunction, 0xF16EAF4F);
			mm.addFunction(sceNetAdhocMatchingSelectTargetFunction, 0x5E3D4B79);
			mm.addFunction(sceNetAdhocMatchingCancelTargetFunction, 0xEA3C6108);
			mm.addFunction(sceNetAdhocMatchingSetHelloOptFunction, 0xB58E61B7);
			mm.addFunction(sceNetAdhocMatchingGetHelloOptFunction, 0xB5D96C2A);
			mm.addFunction(sceNetAdhocMatchingGetMembersFunction, 0xC58BCD9E);
			mm.addFunction(sceNetAdhocMatchingGetPoolMaxAllocFunction, 0x40F8F435);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceNetAdhocMatchingInitFunction);
			mm.removeFunction(sceNetAdhocMatchingTermFunction);
			mm.removeFunction(sceNetAdhocMatchingCreateFunction);
			mm.removeFunction(sceNetAdhocMatchingStartFunction);
			mm.removeFunction(sceNetAdhocMatchingStopFunction);
			mm.removeFunction(sceNetAdhocMatchingDeleteFunction);
			mm.removeFunction(sceNetAdhocMatchingSelectTargetFunction);
			mm.removeFunction(sceNetAdhocMatchingCancelTargetFunction);
			mm.removeFunction(sceNetAdhocMatchingSetHelloOptFunction);
			mm.removeFunction(sceNetAdhocMatchingGetHelloOptFunction);
			mm.removeFunction(sceNetAdhocMatchingGetMembersFunction);
			mm.removeFunction(sceNetAdhocMatchingGetPoolMaxAllocFunction);
			
		}
	}
	
	
	public void sceNetAdhocMatchingInit(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingInit [0x2A2A1E07]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingTerm(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingTerm [0x7945ECDA]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingCreate(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingCreate [0xCA5EDA6F]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingStart(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingStart [0x93EF3843]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingStop(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingStop [0x32B156B3]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingDelete(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingDelete [0xF16EAF4F]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingSelectTarget(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingSelectTarget [0x5E3D4B79]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingCancelTarget(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingCancelTarget [0xEA3C6108]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingSetHelloOpt(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingSetHelloOpt [0xB58E61B7]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingGetHelloOpt(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingGetHelloOpt [0xB5D96C2A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingGetMembers(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingGetMembers [0xC58BCD9E]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceNetAdhocMatchingGetPoolMaxAlloc(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceNetAdhocMatchingGetPoolMaxAlloc [0x40F8F435]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction sceNetAdhocMatchingInitFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingInit") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingInit(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingInit(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingTermFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingTerm") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingTerm(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingTerm(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingCreateFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingCreate") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingCreate(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingCreate(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingStartFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingStart") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingStart(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingStart(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingStopFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingStop") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingStop(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingStop(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingDeleteFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingDelete") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingDelete(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingDelete(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingSelectTargetFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingSelectTarget") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingSelectTarget(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingSelectTarget(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingCancelTargetFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingCancelTarget") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingCancelTarget(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingCancelTarget(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingSetHelloOptFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingSetHelloOpt") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingSetHelloOpt(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingSetHelloOpt(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingGetHelloOptFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingGetHelloOpt") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingGetHelloOpt(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingGetHelloOpt(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingGetMembersFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingGetMembers") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingGetMembers(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingGetMembers(processor);";
		}
	};
    
	public final HLEModuleFunction sceNetAdhocMatchingGetPoolMaxAllocFunction = new HLEModuleFunction("sceNetAdhocMatching", "sceNetAdhocMatchingGetPoolMaxAlloc") {
		@Override
		public final void execute(Processor processor) {
			sceNetAdhocMatchingGetPoolMaxAlloc(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceNetAdhocMatchingModule.sceNetAdhocMatchingGetPoolMaxAlloc(processor);";
		}
	};
    
};