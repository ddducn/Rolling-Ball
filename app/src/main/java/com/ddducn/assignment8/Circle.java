package com.ddducn.assignment8;

public class Circle extends VisibleObject {
    private double r;
    private int score;

    private final double SQRT_2 = Math.sqrt(2);

    /**
     * @param x center x
     * @param y center y
     * @param r radius
     */
    public Circle(double x, double y, double r, int score) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.score = score;
    }

    public double getR() {
        return r;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * append score to current
     * @param newScore new score
     */
    public void addScore(int newScore) {
        if (newScore <= 0) return;
        score += newScore;
    }

    /**
     * distance between center of circle to other x, y
     * @param x other x
     * @param y other y
     * @return distance
     */
    public double distance(double x, double y) {
        return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

    /**
     * if two lines are intersected
     * @param min1 start of line1
     * @param max1 end of line1
     * @param min2 start of line2
     * @param max2 end of line2
     * @return is in range
     */
    public boolean inRange(double min1, double max1, double min2, double max2) {
        return Math.max(min1, max1) >= Math.min(min2, max2) &&
                Math.min(min1, max1) <= Math.max(min2, max2);
    }

    /**
     * if circle is intersected with a rectangle
     * @param rect other rectangle
     * @return is intersected
     */
    public boolean rectIntersect(Rectangle rect) {
        return inRange(x - r / SQRT_2, x + r / SQRT_2, rect.getX(), rect.getX() + rect.getWidth()) &&
                inRange(y - r / SQRT_2, y + r / SQRT_2, rect.getY(), rect.getY() + rect.getHeight());
    }

    /**
     * if circle is intersected with other circle
     * @param circle other circle
     * @return is intersected
     */
    public boolean circleIntersect(Circle circle) {
       return distance(circle.x, circle.y) <= (r + circle.r);
    }

    /**
     * is the other circle intersected with the circle horizontally
     * @param circle - the other circle
     * @return - is intersected horizontally
     */
    public boolean isIntersectToCircleH(Circle circle) {
        double angle = angleToCircle(circle);
        return angle <= 45 && angle >= -45 ||
                angle >= 135 && angle <= 180 ||
                angle >= -180 && angle <= -135;
    }

    /**
     * angle of the circle to the other one
     * @param circle - other circle
     * @return angle
     */
    public double angleToCircle(Circle circle) {
        return Math.toDegrees(Math.atan2(circle.y - y, circle.x - x));
    }
}
