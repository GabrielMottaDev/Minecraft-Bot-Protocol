package com.lorenzo.utils;

public class Vec3i {

    public Number xPos;
    public Number yPos;
    public Number zPos;

    public Vec3i(Number xPos, Number yPos, Number zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public Number getX() {
        return xPos;
    }

    public Number getY() {
        return yPos;
    }

    public Number getZ() {
        return zPos;
    }

    public void setX(Number xPos) {
        this.xPos = xPos;
    }

    public void setY(Number yPos) {
        this.yPos = yPos;
    }

    public void setZ(Number zPos) {
        this.zPos = zPos;
    }
}
