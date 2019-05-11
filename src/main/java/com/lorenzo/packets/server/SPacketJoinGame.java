package com.lorenzo.packets.server;

import com.lorenzo.packet.Packet;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class SPacketJoinGame extends Packet {

    public int entityID;
    public int gameMode;
    public byte dimension;
    public int difficulty;
    public int maxPlayers;
    public String levelType;
    public boolean reducedInfo;

    public SPacketJoinGame() throws Exception{
        super(0x01, HandshakeState.PLAY);
    }

    @Override
    public void read(PacketBufferIn packetBufferIn) throws Exception {
        this.entityID = packetBufferIn.readInt();
        this.gameMode = packetBufferIn.readUByte();
        this.dimension = packetBufferIn.readByte();
        this.difficulty = packetBufferIn.readUByte();
        this.maxPlayers = packetBufferIn.readUByte();
        this.levelType = packetBufferIn.readString();
        this.reducedInfo = packetBufferIn.readBoolean();
    }

    @Override
    public void write(PacketBufferOut packetBufferOut) throws Exception {
        packetBufferOut.writeVarInt(entityID);
        packetBufferOut.writeUByte(gameMode);
        packetBufferOut.writeUByte(dimension);
        packetBufferOut.writeUByte(difficulty);
        packetBufferOut.writeUByte(maxPlayers);
        packetBufferOut.writeString(levelType);
        packetBufferOut.writeBoolean(reducedInfo);
    }
}
