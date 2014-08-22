package com.aqua.ludum.growth.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Duane Byer
 */
public class ComputerPlant extends Plant {
    
    public ComputerPlant(Terrain terrain, Point position) {
        super(terrain, position);
    }
    
    @Override
    public void control(float delta) {
        
    }

	@Override
	public void render(SpriteBatch batch) {
		renderPlant(batch, Color.RED);
	}
    
}
