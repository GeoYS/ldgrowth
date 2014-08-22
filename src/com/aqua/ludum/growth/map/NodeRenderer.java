package com.aqua.ludum.growth.map;

import com.aqua.ludum.growth.map.Plant.Node;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class NodeRenderer {
	
	private final ShapeRenderer renderer;
	
	public NodeRenderer() {
		renderer = new ShapeRenderer();
	}
	
	public void draw(Node node){
		renderer.setColor(Color.TEAL);
		renderer.circle((float)node.position.x, (float)node.position.y, (float)node.size);
	}
	
	public void begin(){
		renderer.begin(ShapeType.Filled);
	}
	
	public void end(){
		renderer.end();
	}
}
