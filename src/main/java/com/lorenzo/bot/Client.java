package com.lorenzo.bot;

import com.lorenzo.handler.PacketHandler;
import com.lorenzo.main.MinecraftBot;
import com.lorenzo.packet.Packet;
import com.lorenzo.packets.client.CPacketHandshake;
import com.lorenzo.packets.client.CPacketLoginStart;
import com.lorenzo.packets.client.CPacketPositionLook;
import com.lorenzo.packets.server.*;
import com.lorenzo.utils.IOUtils;
import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

import java.io.DataInputStream;
import java.net.Socket;

public class Client {

    private Socket socketConnection;
    public String serverAddress;
    public int serverPort;
    public String botName;
    public Packet.HandshakeState handshakeState = Packet.HandshakeState.LOGIN;
    public int compressionThreshold = -1;
    private static boolean isConnected;

    final int FLAG_X = 0x01;
    final int FLAG_Y = 0x02;
    final int FLAG_Z = 0x04;
    final int FLAG_Y_ROT = 0x08;
    final int FLAG_X_ROT = 0x10;

    public Client(String serverAddress, int serverPort, String botName) throws Exception {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.botName = botName;
        socketConnection = new Socket(serverAddress, serverPort);
    }

    public void connectToServer() throws Exception {
        sendPacket(new CPacketHandshake(47, serverAddress, serverPort, Packet.HandshakeType.LOGIN));
        sendPacket(new CPacketLoginStart(botName));
        new Thread() {
            @Override
            public void run() {
                try {
                    tickLoop();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        packetLoop();
    }

    public void tickLoop() throws Exception {
        while(isConnected) {
            if(handshakeState == Packet.HandshakeState.PLAY) {
                try {
                    sendPacket(new SPacketPositionLook(MinecraftBot.getBotPlayer().getX(), MinecraftBot.getBotPlayer().getY(), MinecraftBot.getBotPlayer().getZ(), MinecraftBot.getBotPlayer().getYaw(), MinecraftBot.getBotPlayer().getPitch(), false));
                    Thread.sleep(500L);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void packetLoop() throws Exception{
        DataInputStream dataIn = new DataInputStream(socketConnection.getInputStream());
        PacketHandler handler = MinecraftBot.getPacketHandler();
        while(true) {
            byte[] data = readPacket(dataIn);
            PacketBufferIn buffer = new PacketBufferIn(data);
            int packetId = buffer.readVarInt();

            Packet packet = handler.createPacketInstance(packetId, handshakeState);
            if (packet != null) {
                packet.read(buffer);
                handlePacket(packet);
            }
        }
    }


    private void handlePacket(Packet packet) throws Exception {
        if (packet instanceof SPacketSetCompression) {
            SPacketSetCompression compPkt = (SPacketSetCompression)packet;
            compressionThreshold = compPkt.compressionThreshold;
        }
        if (packet instanceof SPacketLoginSuccess) {
            handshakeState = Packet.HandshakeState.PLAY;
            SPacketLoginSuccess packetLoginSuccess = (SPacketLoginSuccess)packet;
            MinecraftBot.getBotPlayer().setUserUUID(packetLoginSuccess.uuid);
            MinecraftBot.getBotPlayer().setUserName(packetLoginSuccess.username);
        }
        if(packet instanceof SPacketJoinGame) {
            SPacketJoinGame packetJoinGame = (SPacketJoinGame)packet;
            MinecraftBot.getBotPlayer().setEntityID(packetJoinGame.entityID);
            MinecraftBot.getBotPlayer().setDifficulty(packetJoinGame.difficulty);
            MinecraftBot.getBotPlayer().setDimension(packetJoinGame.dimension);
            isConnected = true;
        }
        if (packet instanceof SPacketKeepAlive) {
            SPacketKeepAlive keepAlivePkt = (SPacketKeepAlive)packet;
            sendPacket(keepAlivePkt);
        }
        if(packet instanceof CPacketPositionLook) {
            CPacketPositionLook packetPositionLook = (CPacketPositionLook)packet;
            byte flags = packetPositionLook.flags;
            double x = packetPositionLook.xPos;
            double y = packetPositionLook.yPos;
            double z = packetPositionLook.zPos;
            float pitch = packetPositionLook.pitch;
            float yaw = packetPositionLook.yaw;
            if ((flags & FLAG_X) != 0) {
                x += MinecraftBot.getBotPlayer().getX();
            }
            if ((flags & FLAG_Y) != 0) {
                y += MinecraftBot.getBotPlayer().getY();
            }
            if ((flags & FLAG_Z) != 0) {
                z += MinecraftBot.getBotPlayer().getZ();
            }
            if ((flags & FLAG_X_ROT) != 0) {
                pitch += MinecraftBot.getBotPlayer().getPitch();
            }
            if ((flags & FLAG_Y_ROT) != 0) {
                yaw += MinecraftBot.getBotPlayer().getYaw();
            }
            MinecraftBot.getBotPlayer().setPosition(x, y, z);
            sendPacket(new SPacketPositionLook(x, y, z, pitch, yaw, false));

        }
        System.out.println("X" + MinecraftBot.getBotPlayer().getX() + " Y: " + MinecraftBot.getBotPlayer().getY() + " Z:" + MinecraftBot.getBotPlayer().getZ());
        System.out.println("Username: " + MinecraftBot.getBotPlayer().getUserName() + "\nUUID " + MinecraftBot.getBotPlayer().getUserUUID() + "\nEntityID: " + MinecraftBot.getBotPlayer().getEntityID());
    }

    private byte[] readPacket(DataInputStream dis) throws Exception {
        int length = IOUtils.readVarInt(dis);
        if(compressionThreshold < 0) {
            byte[] data = new byte[length];
            dis.readFully(data);
            return data;
        } else {
            int realLen = IOUtils.readVarInt(dis);
            length -= IOUtils.getVarIntSize(realLen);

            byte[] comp = new byte[length];
            dis.readFully(comp);
            if (realLen == 0) {
                return comp;
            }
            return IOUtils.inflate(comp);
        }
    }

    public void sendPacket(Packet packet) {
        try {
            PacketBufferOut buf = new PacketBufferOut();
            packet.write(buf);
            byte[] data = buf.buildPacket(packet.getPacketID(), compressionThreshold);
            socketConnection.getOutputStream().write(data);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
