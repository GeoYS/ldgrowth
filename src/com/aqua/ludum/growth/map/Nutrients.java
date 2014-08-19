package com.aqua.ludum.growth.map;

import java.util.List;

import com.badlogic.gdx.math.Polygon;

/**
 *
 * @author duane_000
 */
public class Nutrients extends Area {
    
    public Nutrients(Polygon shape, double amount) {
        super(shape);
        this.amount = amount;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    private final double amount;
}
