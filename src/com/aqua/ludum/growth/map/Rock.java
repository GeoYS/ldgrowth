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
public class Rock extends Area {
    
    public Rock(Polygon shape) {
        super(shape);
        color = new Color(Color.GRAY);
        color.a = MIN_ALPHA;
        renderer = new SingleShapeRenderer(shape.getTransformedVertices());
        renderer.setColor(color);
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
    
    private final float ANIMATION_TIME = 3, MAX_ALPHA = 0.8f, MIN_ALPHA = 0.2f;
    private boolean fading = false;
    private SingleShapeRenderer renderer;
    private Color color;
}
