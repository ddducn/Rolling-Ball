package com.ddducn.assignment8;

public class Circle extends VisibleObject {
    private double r;
    private int score = 0;

    /**
     * @param x - center x
     * @param y - center y
     * @param r - radius
     */
    public Circle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
