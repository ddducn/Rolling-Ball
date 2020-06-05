package com.ddducn.assignment8;

public class Rectangle extends VisibleObject {
    private double width;
    private double height;

    /**
     * @param x - left corner x
     * @param y - right corner y
     * @param width - rect width
     * @param height - rect height
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

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
