package com.lorenzo.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class PacketBufferIn {

    private DataInputStream dis;

    public PacketBufferIn(byte[] data) {
        dis = new DataInputStream(new ByteArrayInputStream(data));
    }

    public int readVarInt() throws Exception {
        return IOUtils.readVarInt(dis);
    }

    public String readString() throws Exception
    {
        byte[] buf = new byte[IOUtils.readVarInt(dis)];
        dis.readFully(buf);
        return new String(buf, "UTF-8");
    }

    public short readShort() throws Exception 
    {
        return dis.readShort();
    }

    public double readDoule() throws Exception {
        return dis.readDouble();
    }

    public float readFloat() throws Exception{
        return dis.readFloat();
    }

    public byte readByte() throws Exception 
    {
        return dis.readByte();
    }

    public Vec3i readPosition() throws Exception {
        long unsignedByte = readLong();
        long x = unsignedByte >> 38;
        long y = (unsignedByte >> 26) & 0xFFF;
        long z = unsignedByte << 38 >> 38;
        return new Vec3i(x, y, z);
    }

    public long readLong() throws Exception {
        return dis.readLong();
    }

    public boolean readBoolean() throws Exception {
        return dis.readBoolean();
    }

    public int readInt() throws Exception {
        return dis.readInt();
    }

    public int readUByte() throws Exception 
    {
        return dis.readByte() & 0xFF;
    }
}