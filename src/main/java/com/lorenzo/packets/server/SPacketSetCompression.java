package com.lorenzo.packets.server;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class SPacketSetCompression extends Packet {

    public int compressionThreshold;

    public SPacketSetCompression() throws Exception {
        super(0x03, HandshakeState.LOGIN);
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        compressionThreshold = packetBufferIn.readVarInt();
    }

    @Override
    public void write(PacketBufferOut packetBufferOut) throws Exception {
        packetBufferOut.writeVarInt(compressionThreshold);
    }
}
