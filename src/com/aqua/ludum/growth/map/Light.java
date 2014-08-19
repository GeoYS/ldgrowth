package com.aqua.ludum.growth.map;

import java.util.List;

import com.badlogic.gdx.math.Polygon;

/**
 *
 * @author Duane Byer
 */
public class Light extends Area {
    
    public Light(Polygon shape, double amount) {
        super(shape);
        this.amount = amount;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    private final double amount;
}
