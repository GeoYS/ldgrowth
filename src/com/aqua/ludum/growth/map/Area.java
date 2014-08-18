package com.aqua.ludum.growth.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duane Byer
 */
public abstract class Area {
    
    public Area(List<Point> points) {
        this.points = new Point[points.size()];
        int i = 0;
        for (Point p : points) {
            this.points[i] = p;
            i++;
        }
    }
    
    public Area(Point[] points) {
        this.points = new Point[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
    }
    
    public final boolean contains(Point p) {
        return true;
    }
    
    public void render(SpriteBatch batch) {
    }
    
    public void update(float delta) {
    }
    
    private final Point[] points;
    
}
