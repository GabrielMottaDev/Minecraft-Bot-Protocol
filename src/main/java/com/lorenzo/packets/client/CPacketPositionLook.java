package com.lorenzo.packets.client;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class CPacketPositionLook extends Packet {

    public double xPos;
    public double yPos;
    public double zPos;
    public float yaw;
    public float pitch;
    public byte flags;

    public CPacketPositionLook() {
        super(0x08, HandshakeState.PLAY);
    }

    @Override
    public void write(PacketBufferOut packetBufferOut) throws Exception {
        packetBufferOut.writeDouble(xPos);
        packetBufferOut.writeDouble(yPos);
        packetBufferOut.writeDouble(zPos);
        packetBufferOut.writeFloat(yaw);
        packetBufferOut.writeFloat(pitch);
        packetBufferOut.writeByte(flags);
        super.write(packetBufferOut);
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        this.xPos = packetBufferIn.readDoule();
        this.yPos = packetBufferIn.readDoule();
        this.zPos = packetBufferIn.readDoule();
        this.yaw = packetBufferIn.readFloat();
        this.pitch = packetBufferIn.readFloat();
        this.flags = packetBufferIn.readByte();
        super.read(packetBufferIn);
    }
}
