package com.aqua.ludum.growth.map;

import java.util.List;

import com.aqua.ludum.growth.map.PolygonRenderer.SingleShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

/**
 *
 * @author Duane Byer
 */
public class Light extends Area {
    
    public Light(Polygon shape, double amount) {
        super(shape);
        this.amount = amount;
        color = new Color(Color.YELLOW);
        color.a = MIN_ALPHA;
        renderer = new SingleShapeRenderer(shape.getTransformedVertices());
        renderer.setColor(color);
    }
    
    public double getAmount() {
        return this.amount;
    }

    @Override
    public void render(SpriteBatch batch) {
    	batch.end();
    	
    	renderer.setColor(color);
    	renderer.getBatch().setTransformMatrix(batch.getTransformMatrix());
    	renderer.getBatch().setProjectionMatrix(batch.getProjectionMatrix());
		Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
    	renderer.begin();
    	renderer.draw();
    	renderer.end();
    	
    	batch.begin();
    }
    
    @Override
    public void update(float delta) {
    	super.update(delta);
    	if(fading){
    		color.a -= (delta / ANIMATION_TIME) * (MAX_ALPHA - MIN_ALPHA);
    		if(color.a < 0){
    			color.a = 0;
    		}
    		if(color.a < MIN_ALPHA){
    			fading = false;
    		}
    	}
    	else{
    		color.a += (delta / ANIMATION_TIME) * (MAX_ALPHA - MIN_ALPHA);
    		if(color.a > MAX_ALPHA){
    			fading = true;
    		}
    	}
    }
    
    private final float ANIMATION_TIME = 3, MAX_ALPHA = 0.99f, MIN_ALPHA = 0.8f;
    private boolean fading = false;
    private final double amount;
    private SingleShapeRenderer renderer;
    private Color color;
    
}
