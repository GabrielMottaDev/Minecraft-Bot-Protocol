package com.lorenzo.packets.server;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class SPacketKeepAlive extends Packet {

    public int randomID;

    public SPacketKeepAlive() throws Exception {
        super(0x00, HandshakeState.PLAY);
    }

    @Override
    public void write(PacketBufferOut packetBufferOut) throws Exception {
        packetBufferOut.writeVarInt(randomID);
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        this.randomID = packetBufferIn.readVarInt();
    }
}
