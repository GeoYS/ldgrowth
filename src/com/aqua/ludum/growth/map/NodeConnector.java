package com.aqua.ludum.growth.map;

import com.aqua.ludum.growth.map.Plant.Node;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class NodeConnector {
	
	private Node node1, node2;
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
	
	private Point[] updateVertices(){		
		vertices[0] = nx * node1.size;
		vertices[1] = ny * node1.size;
		vertices[2] = nx * -node1.size;
		vertices[3] = ny * -node1.size;
		vertices[4] = nx * node2.size;
		vertices[5] = ny * node2.size;
		vertices[6] = nx * -node2.size;
		vertices[7] = ny * -node2.size;
	}
	
	public void render(SpriteBatch batch){
		batch.end();
		
		renderer.begin(ShapeType.Filled);
		
		renderer.polygon(vertices);
		
		renderer.end();
		
		batch.begin();
	}
}