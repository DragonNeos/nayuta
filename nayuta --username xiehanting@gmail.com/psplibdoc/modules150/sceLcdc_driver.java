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

public class sceLcdc_driver implements HLEModule {
	@Override
	public String getName() { return "sceLcdc_driver"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(sceLcdcInitFunction, 0xB55500A3);
			mm.addFunction(sceLcdcEndFunction, 0x0E8E8774);
			mm.addFunction(sceLcdcSuspendFunction, 0xDCD51769);
			mm.addFunction(sceLcdcResumeFunction, 0xC6F10C77);
			mm.addFunction(sceLcdcEnableFunction, 0xA182B32C);
			mm.addFunction(sceLcdcDisableFunction, 0xA0032C3D);
			mm.addFunction(sceLcdcCheckModeFunction, 0x73A3A01D);
			mm.addFunction(sceLcdcSetModeFunction, 0x0BC2B5E2);
			mm.addFunction(sceLcdcResetFunction, 0xA2E70DA6);
			mm.addFunction(sceLcdcGetPixelClockFreqFunction, 0x965D1633);
			mm.addFunction(sceLcdcGetHsyncFreqFunction, 0xB3BA421A);
			mm.addFunction(sceLcdcGetVsyncFreqFunction, 0x3107417C);
			mm.addFunction(sceLcdcInsertDisplay_defaultFunction, 0x551C5BC3);
			mm.addFunction(sceLcdcReadHPCFunction, 0xFE5A8859);
			mm.addFunction(sceLcdcReadVPCFunction, 0x7BDC15C8);
			mm.addFunction(sceLcdc_driver_E08B076BFunction, 0xE08B076B);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(sceLcdcInitFunction);
			mm.removeFunction(sceLcdcEndFunction);
			mm.removeFunction(sceLcdcSuspendFunction);
			mm.removeFunction(sceLcdcResumeFunction);
			mm.removeFunction(sceLcdcEnableFunction);
			mm.removeFunction(sceLcdcDisableFunction);
			mm.removeFunction(sceLcdcCheckModeFunction);
			mm.removeFunction(sceLcdcSetModeFunction);
			mm.removeFunction(sceLcdcResetFunction);
			mm.removeFunction(sceLcdcGetPixelClockFreqFunction);
			mm.removeFunction(sceLcdcGetHsyncFreqFunction);
			mm.removeFunction(sceLcdcGetVsyncFreqFunction);
			mm.removeFunction(sceLcdcInsertDisplay_defaultFunction);
			mm.removeFunction(sceLcdcReadHPCFunction);
			mm.removeFunction(sceLcdcReadVPCFunction);
			mm.removeFunction(sceLcdc_driver_E08B076BFunction);
			
		}
	}
	
	
	public void sceLcdcInit(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcInit [0xB55500A3]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcEnd(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcEnd [0x0E8E8774]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcSuspend(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcSuspend [0xDCD51769]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcResume(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcResume [0xC6F10C77]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcEnable(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcEnable [0xA182B32C]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcDisable(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcDisable [0xA0032C3D]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcCheckMode(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcCheckMode [0x73A3A01D]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcSetMode(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcSetMode [0x0BC2B5E2]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcReset(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcReset [0xA2E70DA6]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcGetPixelClockFreq(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcGetPixelClockFreq [0x965D1633]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcGetHsyncFreq(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcGetHsyncFreq [0xB3BA421A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcGetVsyncFreq(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcGetVsyncFreq [0x3107417C]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcInsertDisplay_default(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcInsertDisplay_default [0x551C5BC3]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcReadHPC(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcReadHPC [0xFE5A8859]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdcReadVPC(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdcReadVPC [0x7BDC15C8]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceLcdc_driver_E08B076B(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceLcdc_driver_E08B076B [0xE08B076B]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction sceLcdcInitFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcInit") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcInit(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcInit(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcEndFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcEnd") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcEnd(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcEnd(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcSuspendFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcSuspend") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcSuspend(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcSuspend(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcResumeFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcResume") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcResume(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcResume(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcEnableFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcEnable") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcEnable(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcEnable(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcDisableFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcDisable") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcDisable(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcDisable(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcCheckModeFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcCheckMode") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcCheckMode(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcCheckMode(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcSetModeFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcSetMode") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcSetMode(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcSetMode(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcResetFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcReset") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcReset(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcReset(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcGetPixelClockFreqFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcGetPixelClockFreq") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcGetPixelClockFreq(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcGetPixelClockFreq(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcGetHsyncFreqFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcGetHsyncFreq") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcGetHsyncFreq(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcGetHsyncFreq(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcGetVsyncFreqFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcGetVsyncFreq") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcGetVsyncFreq(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcGetVsyncFreq(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcInsertDisplay_defaultFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcInsertDisplay_default") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcInsertDisplay_default(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcInsertDisplay_default(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcReadHPCFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcReadHPC") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcReadHPC(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcReadHPC(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdcReadVPCFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdcReadVPC") {
		@Override
		public final void execute(Processor processor) {
			sceLcdcReadVPC(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdcReadVPC(processor);";
		}
	};
    
	public final HLEModuleFunction sceLcdc_driver_E08B076BFunction = new HLEModuleFunction("sceLcdc_driver", "sceLcdc_driver_E08B076B") {
		@Override
		public final void execute(Processor processor) {
			sceLcdc_driver_E08B076B(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.sceLcdc_driverModule.sceLcdc_driver_E08B076B(processor);";
		}
	};
    
};