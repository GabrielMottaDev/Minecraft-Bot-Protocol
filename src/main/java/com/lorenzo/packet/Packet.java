package com.lorenzo.packet;

import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public class Packet implements IPacket{

    public enum HandshakeState {
        LOGIN, PLAY
    }

    public enum HandshakeType {

        STATE(1), LOGIN(2);

        public int nextState;

        HandshakeType(int nextState) {
            this.nextState = nextState;
        }

        public int getNextState() {
            return nextState;
        }
    }

    private int packetID;
    private HandshakeState handshakeState;
    public Packet(int packetID, HandshakeState handshakeState) {
        this.packetID = packetID;
        this.handshakeState = handshakeState;
    }

    public int getPacketID() {
        return packetID;
    }

    public void read(PacketBufferIn packetBufferIn) throws Exception {}

    public void write(PacketBufferOut packetBufferOut) throws Exception {}
}
