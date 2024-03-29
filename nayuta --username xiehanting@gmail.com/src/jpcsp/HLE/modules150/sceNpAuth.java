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

import jpcsp.HLE.HLEFunction;
import jpcsp.HLE.HLELogging;
import jpcsp.HLE.TPointer32;
import jpcsp.HLE.Modules;
import jpcsp.HLE.modules.HLEModule;

import org.apache.log4j.Logger;

@HLELogging
public class sceNpAuth extends HLEModule {
    public static Logger log = Modules.getLogger("sceNpAuth");

    @Override
    public String getName() {
        return "sceNpAuth";
    }

    private int npMemSize;     // Memory allocated by the NP utility.
    private int npMaxMemSize;  // Maximum memory used by the NP utility.
    private int npFreeMemSize; // Free memory available to use by the NP utility.

    @HLEFunction(nid = 0xA1DE86F8, version = 150, checkInsideInterrupt = true)
    public int sceNpAuth_A1DE86F8(int poolSize, int stackSize, int threadPriority) {
        npMemSize = poolSize;
        npMaxMemSize = poolSize / 2;    // Dummy
        npFreeMemSize = poolSize - 16;  // Dummy.

        return 0;
    }

    @HLEFunction(nid = 0xCD86A656, version = 150, checkInsideInterrupt = true)
    public int sceNpAuth_CD86A656(TPointer32 memStatAddr) {
        memStatAddr.setValue(0, npMemSize);
        memStatAddr.setValue(4, npMaxMemSize);
        memStatAddr.setValue(8, npFreeMemSize);

        return 0;
    }
}