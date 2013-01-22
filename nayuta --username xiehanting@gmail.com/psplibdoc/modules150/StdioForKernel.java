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

public class StdioForKernel implements HLEModule {
	@Override
	public String getName() { return "StdioForKernel"; }
	
	@Override
	public void installModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.addFunction(fdprintfFunction, 0x2CCF071A);
			mm.addFunction(printfFunction, 0xCAB439DF);
			mm.addFunction(fdputcFunction, 0x4F78930A);
			mm.addFunction(putcharFunction, 0xD768752A);
			mm.addFunction(fdputsFunction, 0x36B23B8B);
			mm.addFunction(putsFunction, 0xD97C8CB9);
			mm.addFunction(fdgetcFunction, 0xD2B2A2A7);
			mm.addFunction(getcharFunction, 0x7E338487);
			mm.addFunction(fdgetsFunction, 0x11A5127A);
			mm.addFunction(getsFunction, 0xBFF7E760);
			mm.addFunction(sceKernelStdinFunction, 0x172D316E);
			mm.addFunction(sceKernelStdoutFunction, 0xA6BAB2E9);
			mm.addFunction(sceKernelStderrFunction, 0xF78BA90A);
			mm.addFunction(sceKernelStdoutReopenFunction, 0x98220F3E);
			mm.addFunction(sceKernelStderrReopenFunction, 0xFB5380C5);
			
		}
	}
	
	@Override
	public void uninstallModule(HLEModuleManager mm, int version) {
		if (version >= 150) {
		
			mm.removeFunction(fdprintfFunction);
			mm.removeFunction(printfFunction);
			mm.removeFunction(fdputcFunction);
			mm.removeFunction(putcharFunction);
			mm.removeFunction(fdputsFunction);
			mm.removeFunction(putsFunction);
			mm.removeFunction(fdgetcFunction);
			mm.removeFunction(getcharFunction);
			mm.removeFunction(fdgetsFunction);
			mm.removeFunction(getsFunction);
			mm.removeFunction(sceKernelStdinFunction);
			mm.removeFunction(sceKernelStdoutFunction);
			mm.removeFunction(sceKernelStderrFunction);
			mm.removeFunction(sceKernelStdoutReopenFunction);
			mm.removeFunction(sceKernelStderrReopenFunction);
			
		}
	}
	
	
	public void fdprintf(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function fdprintf [0x2CCF071A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void printf(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function printf [0xCAB439DF]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void fdputc(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function fdputc [0x4F78930A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void putchar(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function putchar [0xD768752A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void fdputs(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function fdputs [0x36B23B8B]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void puts(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function puts [0xD97C8CB9]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void fdgetc(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function fdgetc [0xD2B2A2A7]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void getchar(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function getchar [0x7E338487]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void fdgets(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function fdgets [0x11A5127A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void gets(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function gets [0xBFF7E760]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceKernelStdin(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceKernelStdin [0x172D316E]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceKernelStdout(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceKernelStdout [0xA6BAB2E9]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceKernelStderr(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceKernelStderr [0xF78BA90A]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceKernelStdoutReopen(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceKernelStdoutReopen [0x98220F3E]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public void sceKernelStderrReopen(Processor processor) {
		CpuState cpu = processor.cpu;

		Modules.log.debug("Unimplemented NID function sceKernelStderrReopen [0xFB5380C5]");

		cpu.gpr[2] = 0xDEADC0DE;
	}
    
	public final HLEModuleFunction fdprintfFunction = new HLEModuleFunction("StdioForKernel", "fdprintf") {
		@Override
		public final void execute(Processor processor) {
			fdprintf(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.fdprintf(processor);";
		}
	};
    
	public final HLEModuleFunction printfFunction = new HLEModuleFunction("StdioForKernel", "printf") {
		@Override
		public final void execute(Processor processor) {
			printf(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.printf(processor);";
		}
	};
    
	public final HLEModuleFunction fdputcFunction = new HLEModuleFunction("StdioForKernel", "fdputc") {
		@Override
		public final void execute(Processor processor) {
			fdputc(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.fdputc(processor);";
		}
	};
    
	public final HLEModuleFunction putcharFunction = new HLEModuleFunction("StdioForKernel", "putchar") {
		@Override
		public final void execute(Processor processor) {
			putchar(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.putchar(processor);";
		}
	};
    
	public final HLEModuleFunction fdputsFunction = new HLEModuleFunction("StdioForKernel", "fdputs") {
		@Override
		public final void execute(Processor processor) {
			fdputs(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.fdputs(processor);";
		}
	};
    
	public final HLEModuleFunction putsFunction = new HLEModuleFunction("StdioForKernel", "puts") {
		@Override
		public final void execute(Processor processor) {
			puts(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.puts(processor);";
		}
	};
    
	public final HLEModuleFunction fdgetcFunction = new HLEModuleFunction("StdioForKernel", "fdgetc") {
		@Override
		public final void execute(Processor processor) {
			fdgetc(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.fdgetc(processor);";
		}
	};
    
	public final HLEModuleFunction getcharFunction = new HLEModuleFunction("StdioForKernel", "getchar") {
		@Override
		public final void execute(Processor processor) {
			getchar(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.getchar(processor);";
		}
	};
    
	public final HLEModuleFunction fdgetsFunction = new HLEModuleFunction("StdioForKernel", "fdgets") {
		@Override
		public final void execute(Processor processor) {
			fdgets(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.fdgets(processor);";
		}
	};
    
	public final HLEModuleFunction getsFunction = new HLEModuleFunction("StdioForKernel", "gets") {
		@Override
		public final void execute(Processor processor) {
			gets(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.gets(processor);";
		}
	};
    
	public final HLEModuleFunction sceKernelStdinFunction = new HLEModuleFunction("StdioForKernel", "sceKernelStdin") {
		@Override
		public final void execute(Processor processor) {
			sceKernelStdin(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.sceKernelStdin(processor);";
		}
	};
    
	public final HLEModuleFunction sceKernelStdoutFunction = new HLEModuleFunction("StdioForKernel", "sceKernelStdout") {
		@Override
		public final void execute(Processor processor) {
			sceKernelStdout(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.sceKernelStdout(processor);";
		}
	};
    
	public final HLEModuleFunction sceKernelStderrFunction = new HLEModuleFunction("StdioForKernel", "sceKernelStderr") {
		@Override
		public final void execute(Processor processor) {
			sceKernelStderr(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.sceKernelStderr(processor);";
		}
	};
    
	public final HLEModuleFunction sceKernelStdoutReopenFunction = new HLEModuleFunction("StdioForKernel", "sceKernelStdoutReopen") {
		@Override
		public final void execute(Processor processor) {
			sceKernelStdoutReopen(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.sceKernelStdoutReopen(processor);";
		}
	};
    
	public final HLEModuleFunction sceKernelStderrReopenFunction = new HLEModuleFunction("StdioForKernel", "sceKernelStderrReopen") {
		@Override
		public final void execute(Processor processor) {
			sceKernelStderrReopen(processor);
		}
		@Override
		public final String compiledString() {
			return "jpcsp.HLE.Modules.StdioForKernelModule.sceKernelStderrReopen(processor);";
		}
	};
    
};
