package com.lorenzo.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
public class PacketBufferOut {

    private ByteArrayOutputStream baos;
    private DataOutputStream dos;
    
    public PacketBufferOut() throws IOException
    {
        dos = new DataOutputStream(baos = new ByteArrayOutputStream());
    }
    public void writeVarInt(int val) throws Exception
    {
        while ((val & ~0x7F) != 0) {
            dos.writeByte((val & 0x7F) | 0x80);
            val >>>= 7;
        }
        dos.writeByte(val);
    }

    public void writeFloat(float val) throws Exception{
        dos.writeFloat(val);
    }

    public void writeInt(int val) throws Exception {
        dos.writeInt(val);
    }

    public void writeDouble(double val) throws Exception {
        dos.writeDouble(val);
    }

    public void writeString(String val) throws Exception
    {
        byte[] buf = val.getBytes("UTF-8");

        writeVarInt(buf.length);
        dos.write(buf);
    }

    public void writeUShort(int val) throws Exception
    {
        dos.writeShort(val);
    }
    
    public void writeByte(byte val) throws Exception
    {
        dos.writeByte(val & 0xFF);
    }

    public void writeBoolean(boolean val) throws Exception {
        dos.writeBoolean(val);
    }

    public void writeUByte(int val) throws Exception
    {
        dos.writeByte(val);
    }

    public void writePosition(Vec3i pos) throws Exception {
        dos.writeLong(((pos.getX().longValue() & 0x3FFFFFFL) << 38) | ((pos.getY().longValue() & 0xFFFL) << 26) | (pos.getZ().longValue() & 0x3FFFFFFL));
    }

    public byte[] buildPacket(int id, int compThreshold) throws Exception
    {
        dos.flush();
        byte[] data = appendVarInt(baos.toByteArray(), id); //Id, Data
        
        if (compThreshold >= 0) {
            return buildPacketWithCompression(data, id, compThreshold);
        }
        return appendVarInt(data, data.length); //Length, Id, Data
    }
    
    private byte[] buildPacketWithCompression(byte[] data, int id, int threshold)
    {
        int realLen = data.length;
        if (realLen < threshold) {
            realLen = 0;
        } else {
            data = IOUtils.deflate(data);
        }

        int realLenSize = IOUtils.getVarIntSize(realLen);
        int len = realLenSize + data.length;
        int lenSize = IOUtils.getVarIntSize(len);

        byte[] comp = new byte[len + lenSize];
        IOUtils.putVarInt(comp, 0, len);
        IOUtils.putVarInt(comp, lenSize, realLen);
        System.arraycopy(data, 00, comp, realLenSize + lenSize, data.length);

        return comp;
    }
    
    //adiciona uma VarInt no comeco da array 'data'
    private byte[] appendVarInt(byte[] data, int value) throws Exception
    {
        int valSize = IOUtils.getVarIntSize(value);

        byte[] packet = new byte[data.length + valSize];
        IOUtils.putVarInt(packet, 0, value);
        System.arraycopy(data, 0, packet, valSize, data.length);

        return packet;
    }
}
