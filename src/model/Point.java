package model;

/**
 * Created by Dominik Mandinec on 22/10/2019
 */

public class Point {
    private final int x;
    private final int y;
    private final int color;

    public Point(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
        this.color = point.color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }
}
