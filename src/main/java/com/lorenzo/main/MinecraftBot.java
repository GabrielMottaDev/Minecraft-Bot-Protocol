package com.lorenzo.main;

import com.lorenzo.bot.Client;
import com.lorenzo.bot.Player;
import com.lorenzo.handler.PacketHandler;

public class MinecraftBot {

    private static PacketHandler packetHandler;
    private static Client minecraftClient;
    private static Player botPlayer;

    public static void main(String... args) throws Exception {
        packetHandler = new PacketHandler();
        botPlayer = new Player();
        try {
            minecraftClient = new Client("coyote.aternos.org", 10811, "LorenzoBot");
            new Thread() {
                @Override
                public void run() {
                    try {
                        minecraftClient.connectToServer();
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();
            Thread.sleep(500L);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Player getBotPlayer() {
        return botPlayer;
    }

    public static Client getMinecraftClient() {
        return minecraftClient;
    }

    public static PacketHandler getPacketHandler() {
        return packetHandler;
    }

}
