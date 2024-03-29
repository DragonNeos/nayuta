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
package jpcsp.HLE.kernel.types;

import java.util.Comparator;

public class SceKernelMsgPacket extends pspAbstractMemoryMappedStructure implements Comparator<SceKernelMsgPacket> {
	public int nextMsgPacketAddr;
	int msgPriority; // SceUChar

	@Override
	protected void read() {
		nextMsgPacketAddr = read32();
		msgPriority = read8();
		readUnknown(3);
	}

	@Override
	protected void write() {
		write32(nextMsgPacketAddr);
		write8((byte) msgPriority);
		writeUnknown(3);
	}

    @Override
	public int sizeof() {
		return 8;
	}

    @Override
    public int compare(SceKernelMsgPacket m1, SceKernelMsgPacket m2) {
        return m1.msgPriority - m2.msgPriority;
    }
}