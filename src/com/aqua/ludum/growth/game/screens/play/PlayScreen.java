package com.aqua.ludum.growth.game.screens.play;

import com.aqua.ludum.growth.screen.Game;
import com.aqua.ludum.growth.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayScreen extends GameScreen{

	public static int ID = 1;
	
	private Level level;
	private SpriteBatch batch;
	
	@Override
	public void render() {		
		batch.begin();
		
		Gdx.gl20.glClearColor(0,0,0,0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		level.render(batch);
		
		batch.end();
	}

	@Override
	public void update(float delta) {
		level.update(delta);
	}

	@Override
	public void init(Game game) {
		batch = new SpriteBatch();
	}

	@Override
	public void show() {
		level = new TestLevel();
	}
	
	@Override
	public int getID() {
		return ID;
	}

}
