package com.aqua.ludum.growth.map;

import com.aqua.ludum.growth.map.Plant.Node;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class NodeConnector {
	
	public Node node1, node2;
	private ShapeRenderer renderer;
	private float[] vertices;
	
	/*
	 * Normalised vector perp. to the vector between nodes. 
	 */
	private double nx, ny;
	
	public NodeConnector(Node node1, Node node2){
		this.node1 = node1;
		this.node2 = node2;		

		float x = (float) (node1.position.x - node2.position.x),
				y = (float) (node1.position.y - node2.position.y),
				distance;
		distance = (float) Math.sqrt(y * y + x * x);
		ny = - x / distance;
		nx = y / distance;
				
		renderer = new ShapeRenderer();
	}
	
	private void updateVertices(){		
		vertices[0] = (float) (nx * node1.size);
		vertices[1] = (float) (ny * node1.size);
		vertices[2] = (float) (nx * -node1.size);
		vertices[3] = (float) (ny * -node1.size);
		vertices[4] = (float) (nx * node2.size);
		vertices[5] = (float) (ny * node2.size);
		vertices[6] = (float) (nx * -node2.size);
		vertices[7] = (float) (ny * -node2.size);
	}
	
	public void render(SpriteBatch batch){
		batch.end();
		
		renderer.setColor(Color.CYAN);
		renderer.begin(ShapeType.Filled);
		
		updateVertices();
		renderer.polygon(vertices);
		
		renderer.end();
		
		batch.begin();
	}
	
	public boolean equals(NodeConnector nodeConnector){
		if(node1 == nodeConnector.node1 || node1 == nodeConnector.node2){
			if(node2 == nodeConnector.node1 || node2 == nodeConnector.node2){
				return true;
			}
		}
		return false;
	}
}
