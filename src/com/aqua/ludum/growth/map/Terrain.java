/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aqua.ludum.growth.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duane_000
 */
public class Terrain {
    
    public Terrain(Map map) {
    	this.mapRenderer = new OrthogonalTiledMapRenderer((TiledMap) map, 1);
    	camera = new OrthographicCamera(960, 640);
    	camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    	camera.update();
    	this.mapRenderer.setView(camera);
    	
    	rocks = new ArrayList<Rock>();
    	lights = new ArrayList<Light>();
    	nutrients = new ArrayList<Nutrients>();
    	waters = new ArrayList<Water>();
    	plants = new ArrayList<Plant>();
    	
    	for(int mapLayer = 0; mapLayer < map.getLayers().getCount(); mapLayer ++){
    		MapLayer mLayer = map.getLayers().get(mapLayer);
    		for(int mapObject = 0; mapObject < mLayer.getObjects().getCount(); mapObject ++){
    			MapObject mObject = mLayer.getObjects().get(mapObject);
    			if(mObject.getProperties().containsKey("type")){
    				String type = (String) mObject.getProperties().get("type");
    				if(type.equals("light")){
    					double amount = Double.parseDouble((String) mObject.getProperties().get("amount"));
    					Polygon shape = ((PolygonMapObject) mObject).getPolygon();
    					lights.add(new Light(shape, amount));
    				}
    				else if(type.equals("water")){
    					double amount = Double.parseDouble((String) mObject.getProperties().get("amount"));
    					Polygon shape = ((PolygonMapObject) mObject).getPolygon();
    					waters.add(new Water(shape, amount));
    				}
    				else if(type.equals("rock")){
    					Polygon shape = ((PolygonMapObject) mObject).getPolygon();
    					rocks.add(new Rock(shape));
    				}
    				else if(type.equals("nutrients")){
    					double amount = Double.parseDouble((String) mObject.getProperties().get("amount"));
    					Polygon shape = ((PolygonMapObject) mObject).getPolygon();
    					nutrients.add(new Nutrients(shape, amount));
    				}
    			}
    		}
    	}
    	
    	this.plants.add(new PlayerPlant(this, new Point(50, 50)));
    }
    
    public void update(float delta) {
        for (Plant plant : this.plants) {
            plant.update(delta);
        }
        for (Rock rock : this.rocks) {
            rock.update(delta);
        }
        for (Light light : this.lights) {
            light.update(delta);
        }
        for (Nutrients nutrient : this.nutrients) {
            nutrient.update(delta);
        }
        for (Water water : this.waters) {
            water.update(delta);
        }
    }
    
    public void render(SpriteBatch batch) {
        // render itself
    	batch.end();
    	
    	mapRenderer.render();
    	
    	batch.begin();
        for (Rock rock : this.rocks) {
            rock.render(batch);
        }
        for (Light light : this.lights) {
            light.render(batch);
        }
        for (Nutrients nutrient : this.nutrients) {
            nutrient.render(batch);
        }
        for (Water water : this.waters) {
            water.render(batch);
        }
    	for (Plant plant : this.plants) {
            plant.render(batch);
        }
    }
    
    public List<Rock> getRocks() {
        return this.rocks;
    }
    
    public List<Light> getLights() {
        return this.lights;
    }
    
    public List<Nutrients> getNutrients() {
        return this.nutrients;
    }
    
    public List<Water> getWaters() {
        return this.waters;
    }
    
    private List<Rock> rocks;
    private List<Light> lights;
    private List<Nutrients> nutrients;
    private List<Water> waters;
    
    private List<Plant> plants;
    
    private TiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    
}
