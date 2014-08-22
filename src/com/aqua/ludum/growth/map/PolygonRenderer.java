package com.aqua.ludum.growth.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;


/**
 * Renders filled polygons.
 * @author GeoYS_2
 *
 */
public class PolygonRenderer {
	
	public PolygonRenderer() {
		this.polygonBatch = new PolygonSpriteBatch();
		colorPix = new Pixmap(1, 1, Pixmap.Format.RGB888);
		triangulator = new EarClippingTriangulator();
	}
	
	public void begin(){
		polygonBatch.begin();
	}
	
	/**
	 * Vertices -> {x1, y1, ..., xn, yn}
	 * Triangles -> {triangle1P1, triangle1P2, triangle1P3, ..., trianglenP1, trianglenP2, trianglenP3} using indices
	 * @param vertices
	 * @param triangles
	 */
	public void draw(float[] vertices){
		Texture unitTexture = new Texture(colorPix);
		PolygonRegion polyRegion = new PolygonRegion(new TextureRegion(unitTexture), vertices, triangulate(vertices));
		shape = new PolygonSprite(polyRegion);
		shape.draw(polygonBatch);
	}
	
	public void end(){
		polygonBatch.end();
	}

	public void setColor(Color color){
		colorPix = new Pixmap(1, 1, Pixmap.Format.RGB888);
		colorPix.setColor(color);
		colorPix.drawPixel(0, 0);
	}
	
	public Batch getBatch(){
		return  polygonBatch;
	}
	
	private short[] triangulate(float[] vertices){
		return triangulator.computeTriangles(vertices).items;
	}
	
	private PolygonSprite shape;
	private PolygonSpriteBatch polygonBatch;
	private Pixmap colorPix;
	private EarClippingTriangulator triangulator;
	
	public static class SingleShapeRenderer{
		public SingleShapeRenderer(float[] vertices) {
			this.polygonBatch = new PolygonSpriteBatch();
			colorPix = new Pixmap(1, 1, Pixmap.Format.RGB888);
			triangulator = new EarClippingTriangulator();
			TextureRegion unitTexture = new TextureRegion(new Texture(colorPix));
			polyRegion = new PolygonRegion(unitTexture, vertices, triangulate(vertices));
			shape = new PolygonSprite(polyRegion);
		}
		
		public void begin(){
			polygonBatch.begin();
		}
		
		/**
		 * Vertices -> {x1, y1, ..., xn, yn}
		 * Triangles -> {triangle1P1, triangle1P2, triangle1P3, ..., trianglenP1, trianglenP2, trianglenP3} using indices
		 * @param vertices
		 * @param triangles
		 */
		public void draw(){
			shape.draw(polygonBatch);
		}
		
		public void end(){
			polygonBatch.end();
		}

		public void setColor(Color color){
			colorPix = new Pixmap(1, 1, Pixmap.Format.RGB888);
			colorPix.setColor(color);
			colorPix.drawPixel(0, 0);
			Texture unitTexture = new Texture(colorPix);
			polyRegion.getRegion().setRegion(unitTexture);
		}
		
		public Batch getBatch(){
			return  polygonBatch;
		}
		
		protected short[] triangulate(float[] vertices){
			return triangulator.computeTriangles(vertices).items;
		}
		
		private PolygonSprite shape;
		private PolygonSpriteBatch polygonBatch;
		private Pixmap colorPix;
		private EarClippingTriangulator triangulator;		
		private PolygonRegion polyRegion;
	}
}
