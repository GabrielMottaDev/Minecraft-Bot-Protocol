package com.lorenzo.packets.server;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class SPacketLoginSuccess extends Packet
{
    public String uuid;
    public String username;

    public SPacketLoginSuccess() throws Exception {
        super(0x02, HandshakeState.LOGIN);
    }

    @Override
    public void read(PacketBufferIn buf) throws Exception {
        uuid = buf.readString();
        username = buf.readString();
    }

    @Override
    public void write(PacketBufferOut buf) throws Exception {
        buf.writeString(uuid);
        buf.writeString(username);
    }
}
