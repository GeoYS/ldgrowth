package com.aqua.ludum.growth.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 *
 * @author Duane Byer, George Shen
 */
public class PlayerPlant extends Plant {
    
    public PlayerPlant(Terrain terrain, Point position) {
        super(terrain, position);
        selectedIndicator = new SelectedIndicator();
        selectedNode = null;
    }
    
    @Override
    public void control(float delta) {
    	updateMousePos();
    	if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
    		rightDown = true;
    		rightDown(delta);
    	}
    	else if(rightDown){
    		rightDown = false;
    		onRightReleased();
    	}
    	if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			// left click
			// select starting node 
			leftDown = true;
			leftDown(delta);
		}
		else if(leftDown){
			// left released
			// set target
			leftDown = false;
			onLeftReleased();
		} 
    }

	@Override
	public void render(SpriteBatch batch) {
		this.renderPlant(batch, Color.GREEN);
		if(selectedIndicator.isActive()){
			selectedIndicator.render(batch);
		}
	}
	
	private void leftDown(float delta){
		if(selectedNode == null){
			for(Node node : this.getNodes()){
				double radiusSquared = node.size * node.size,
						dx = mouseX - node.position.x, dy = mouseY - node.position.y,
						distSquared;
				distSquared = dx * dx + dy * dy;
				if(distSquared <= radiusSquared){
					selectedIndicator.select(node);
					return;
				}
			}
		}
	}
	
	private void onLeftReleased(){
		if(selectedNode == null){
			for(Node node : this.getNodes()){
				double radiusSquared = node.size * node.size,
						dx = mouseX - node.position.x, dy = mouseY - node.position.y,
						distSquared;
				distSquared = dx * dx + dy * dy;
				if(distSquared <= radiusSquared){
					selectedNode = node;
					selectedIndicator.select(node);
					return;
				}
			}
		}
		else{
			// set target for selectedNode
			for(Node node : this.getNodes()){
				if(node != selectedNode){
					double radiusSquared = node.size * node.size,
							dx = mouseX - node.position.x, dy = mouseY - node.position.y,
							distSquared;
					distSquared = dx * dx + dy * dy;
					if(distSquared <= radiusSquared){
						this.growTowards(selectedNode, node);
						return;
					}
				}
			}
		}
	}
    
	private void rightDown(float delta){
		for(Node node : this.getNodes()){
			double radiusSquared = node.size * node.size,
					dx = mouseX - node.position.x, dy = mouseY - node.position.y,
					distSquared;
			distSquared = dx * dx + dy * dy;
			if(distSquared <= radiusSquared){
				// decay the selected plant
			}
		}
	}
	
	private void onRightReleased(){
		if(selectedNode != null){
			selectedNode = null;
			selectedIndicator.deselect();
		}
	}
	
	private void updateMousePos(){
		// convert screen coords. to game world coords.
		float mouseX = Gdx.input.getX(), mouseY = Gdx.input.getY();
		mouseY *= -1;
		mouseY += Gdx.graphics.getHeight();
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	private float mouseX, mouseY;
    private boolean leftDown = false, rightDown = false;
    private Node selectedNode;
    private final SelectedIndicator selectedIndicator;
    
    private class SelectedIndicator{
    	
    	private final ShapeRenderer renderer;
    	private Node selected;
    	
    	public SelectedIndicator() {
    		renderer = new ShapeRenderer();
    		selected = null;
		}
    	
    	public void render(SpriteBatch batch){
    		batch.end();
    		
    		renderer.setColor(Color.RED);
    		renderer.begin(ShapeType.Line);
    		renderer.circle((float)selected.position.x, (float)selected.position.y, 10);
    		renderer.end();
    		
    		batch.begin();
    	}
    	
    	public void select(Node node){
    		selected = node;
    	}
    	
    	public void deselect(){
    		selected = null;
    	}
    	
    	public boolean isActive(){
    		return selected != null;
    	}
    }
}
