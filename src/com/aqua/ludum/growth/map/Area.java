package com.aqua.ludum.growth.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duane Byer
 */
public abstract class Area {
    
    public Area(Polygon shape) {
    	this.shape = shape;
    }
    
    public void render(SpriteBatch batch) {
    }
    
    public void update(float delta) {
    }
    
    public Polygon getShape(){
    	return shape;
    }
    
    public boolean contains(Point point) {
        return shape.contains((float) point.x, (float) point.y);
    }
    
    private final Polygon shape;
    
}
