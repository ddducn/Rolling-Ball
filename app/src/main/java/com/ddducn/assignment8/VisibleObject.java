package com.ddducn.assignment8;

abstract public class VisibleObject {
    protected double x;
    protected double y;
    protected int color;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void moveX(double dis) {
        if (dis == 0) return;
        x += dis;
    }

    public void moveY(double dis) {
        if (dis == 0) return;
        y += dis;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
