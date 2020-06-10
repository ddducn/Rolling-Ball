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

    public double distance(double x, double y) {
        return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

    public boolean inRange(double min1, double max1, double min2, double max2) {
        return Math.max(min1, max1) >= Math.min(min2, max2) &&
                Math.min(min1, max1) <= Math.max(min2, max2);
    }

    public boolean rectIntersect(Rectangle rect) {
        return inRange(x - r / Math.sqrt(2), x + r / Math.sqrt(2), rect.getX(), rect.getX() + rect.getWidth()) &&
                inRange(y - r / Math.sqrt(2), y + r / Math.sqrt(2), rect.getY(), rect.getY() + rect.getHeight());
    }

    public boolean circleIntersect(Circle circle) {
       return distance(circle.getX(), circle.getY()) <= r + circle.r;
    }
}
