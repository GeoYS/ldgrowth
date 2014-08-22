package com.aqua.ludum.growth.map;

import com.aqua.ludum.growth.map.Plant.Node;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class NodeConnector {
	
	public Node node1, node2;
	private float[] vertices;
	private PolygonSprite shape;
	private PolygonSpriteBatch polygonBatch;
	private Texture shapeTexture;
	
	/*
	 * Normalised vector perp. to the vector between nodes. 
	 */
	private double nx, ny;
	
	public NodeConnector(Node node1, Node node2){
		this.node1 = node1;
		this.node2 = node2;		

		vertices = new float[8];
		
		float dx = (float) (node1.position.x - node2.position.x),
				dy = (float) (node1.position.y - node2.position.y),
				distance;
		
		distance = (float) Math.sqrt(dy * dy + dx * dx);
		ny = - dx / distance;
		nx = dy / distance;
				
		polygonBatch = new PolygonSpriteBatch();
		shapeTexture = unitTexture(Color.GREEN);
		PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(shapeTexture), vertices, new short[]{0,1,2,0,2,3});
		shape = new PolygonSprite(polygonRegion);
	}
	
	private Texture unitTexture(Color color){
		Pixmap colorPix = new Pixmap(1, 1, Pixmap.Format.RGB888);
		colorPix.setColor(color);
		colorPix.drawPixel(0, 0);

		Texture texture = new Texture(colorPix);
		return texture;
	}
	
	private void updateVertices(){		
		vertices[0] = (float) (nx * node1.size + node1.position.x);
		vertices[1] = (float) (ny * node1.size + node1.position.y);
		vertices[2] = (float) (nx * -node1.size + node1.position.x);
		vertices[3] = (float) (ny * -node1.size + node1.position.y);
		vertices[4] = (float) (nx * -node2.size + node2.position.x);
		vertices[5] = (float) (ny * -node2.size + node2.position.y);
		vertices[6] = (float) (nx * node2.size + node2.position.x);
		vertices[7] = (float) (ny * node2.size + node2.position.y);
	}
	
	public void render(SpriteBatch batch){
		// batch ended before entering this method
		updateVertices();
		
		polygonBatch.setProjectionMatrix(batch.getProjectionMatrix());
		polygonBatch.setTransformMatrix(batch.getTransformMatrix());
		
		polygonBatch.begin();
		shape.draw(polygonBatch);
		polygonBatch.end();
	}
}
