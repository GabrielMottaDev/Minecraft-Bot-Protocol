package com.lorenzo.packets.client;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

import java.io.IOException;

public class CPacketLoginStart extends Packet {

    public String username;

    public CPacketLoginStart(String username) throws IOException {
        super(0x00, HandshakeState.LOGIN);
        this.username = username;
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        username = packetBufferIn.readString();
    }

    @Override
    public void write(PacketBufferOut packetBuffer) throws Exception {
        packetBuffer.writeString(username);
    }
}