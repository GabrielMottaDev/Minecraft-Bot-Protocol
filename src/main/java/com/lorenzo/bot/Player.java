package com.lorenzo.bot;

public class Player {

    public String userUUID;
    public String userName;
    public int entityID;
    public int gameMode;
    public byte dimension;
    public int difficulty;
    public double xPos;
    public double yPos;
    public double zPos;
    public float pitch;
    public float yaw;

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getZ() {
        return zPos;
    }

    public void setPosition(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public byte getDimension() {
        return dimension;
    }

    public void setDimension(byte dimension) {
        this.dimension = dimension;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }
}
