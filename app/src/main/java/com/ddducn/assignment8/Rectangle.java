package com.ddducn.assignment8;

public class Rectangle extends VisibleObject {
    private double width;
    private double height;

    /**
     * @param x top left x
     * @param y top left y
     * @param width rect width
     * @param height rect height
     */
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
