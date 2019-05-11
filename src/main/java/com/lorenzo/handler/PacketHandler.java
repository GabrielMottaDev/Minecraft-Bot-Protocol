package com.lorenzo.handler;

import com.lorenzo.packet.Packet;
import com.lorenzo.packets.client.CPacketPositionLook;
import com.lorenzo.packets.server.SPacketJoinGame;
import com.lorenzo.packets.server.SPacketKeepAlive;
import com.lorenzo.packets.server.SPacketLoginSuccess;
import com.lorenzo.packets.server.SPacketSetCompression;

import java.util.HashMap;

public class PacketHandler {

    private HashMap<Integer, Class<? extends Packet>> loginPackets = new HashMap<Integer, Class<? extends Packet>>();
    private HashMap<Integer, Class<? extends Packet>> playPackets = new HashMap<Integer, Class<? extends Packet>>();

    public PacketHandler() {
        loginPackets.put(0x02, SPacketLoginSuccess.class);
        loginPackets.put(0x03, SPacketSetCompression.class);

        playPackets.put(0x00, SPacketKeepAlive.class);
        playPackets.put(0x01, SPacketJoinGame.class);
        playPackets.put(0x08, CPacketPositionLook.class);
    }

    public Packet createPacketInstance(int packetID, Packet.HandshakeState connState) throws Exception {
        Class cls = null;

        switch(connState){
            case LOGIN: cls = loginPackets.get(packetID); break;
            case PLAY: cls = playPackets.get(packetID); break;
        }

        if (cls == null) {
            return null;
        }
        return (Packet)cls.newInstance();
    }
}
