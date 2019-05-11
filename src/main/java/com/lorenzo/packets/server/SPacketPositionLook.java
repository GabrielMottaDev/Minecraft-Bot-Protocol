package com.lorenzo.packets.server;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class SPacketPositionLook extends Packet {

    private double posX;
    private double feetY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public SPacketPositionLook(double posX, double feetY, double posZ, float yaw, float pitch, boolean onGround) {
        super(0x06, HandshakeState.PLAY);
        this.posX = posX;
        this.feetY = feetY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        this.posX = packetBufferIn.readDoule();
        this.feetY = packetBufferIn.readDoule();
        this.posZ = packetBufferIn.readDoule();
        this.yaw = packetBufferIn.readFloat();
        this.pitch = packetBufferIn.readFloat();
        this.onGround = packetBufferIn.readBoolean();
        super.read(packetBufferIn);
    }

    @Override
    public void write(PacketBufferOut packetBufferOut) throws Exception {
        packetBufferOut.writeDouble(posX);
        packetBufferOut.writeDouble(feetY);
        packetBufferOut.writeDouble(posZ);
        packetBufferOut.writeFloat(yaw);
        packetBufferOut.writeFloat(pitch);
        packetBufferOut.writeBoolean(onGround);
        super.write(packetBufferOut);
    }


    public double getX() {
        return posX;
    }

    public double getFeetY() {
        return feetY;
    }

    public double getZ() {
        return posZ;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setFeetY(double feetY) {
        this.feetY = feetY;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
