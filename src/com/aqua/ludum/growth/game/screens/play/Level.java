package com.aqua.ludum.growth.game.screens.play;

import com.aqua.ludum.growth.map.Terrain;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Level {
	
	private Terrain terrain;
	
	public Level(String pathToTmx){
		TmxMapLoader mapLoader = new TmxMapLoader();
		TiledMap map = mapLoader.load(pathToTmx);
		terrain = new Terrain(map);
	}
	
	public void update(float delta){
		terrain.update(delta);
	}
	public void render(SpriteBatch batch){
		terrain.render(batch);
	}
}
