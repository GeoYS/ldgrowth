package com.aqua.ludum.growth.map;

/**
 *
 * @author Duane Byer
 */
public class Point {
    
    public Point() {
        this(0.0, 0.0);
    }
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public final double x;
    public final double y;
    
}
