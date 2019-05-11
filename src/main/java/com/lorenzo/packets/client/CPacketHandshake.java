package com.lorenzo.packets.client;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

import java.io.IOException;

public class CPacketHandshake extends Packet {

    public int protocolVersion;
    public String serverAdress;
    public int serverPort;
    public HandshakeType connectionState;

    public CPacketHandshake(int protocolVersion, String serverAdress, int serverPort, HandshakeType connectionState) throws IOException {
        super(0x00, HandshakeState.LOGIN);
        this.protocolVersion = protocolVersion;
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
        this.connectionState = connectionState;
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        protocolVersion = packetBufferIn.readVarInt();
        serverAdress = packetBufferIn.readString();
        serverPort = packetBufferIn.readShort();
        connectionState.nextState = packetBufferIn.readVarInt();
    }

    @Override
    public void write(PacketBufferOut packetBuffer) throws Exception {
        packetBuffer.writeVarInt(protocolVersion);
        packetBuffer.writeString(serverAdress);
        packetBuffer.writeUShort(serverPort);
        packetBuffer.writeVarInt(connectionState.getNextState());
    }
}
