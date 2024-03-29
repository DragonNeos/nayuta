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
package jpcsp.HLE.modules395;

import jpcsp.HLE.HLEFunction;

public class SysMemUserForUser extends jpcsp.HLE.modules380.SysMemUserForUser {
	@HLEFunction(nid = 0xEBD5C3E6, version = 395)
	public int sceKernelSetCompiledSdkVersion395(int sdkVersion) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("sceKernelSetCompiledSdkVersion395: sdkVersion=%08X", sdkVersion));
        }

        hleSetCompiledSdkVersion(sdkVersion);

        return 0;
	}

}
