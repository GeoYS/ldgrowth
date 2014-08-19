package com.aqua.ludum.growth.game.screens.menu;

import com.aqua.ludum.growth.screen.Game;
import com.aqua.ludum.growth.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen extends GameScreen{

	public static int ID = 0;
	
	private SpriteBatch batch;
	private Texture background;
	private Menu menu;
	
	@Override
	public void render() {
		batch.begin();
		
		Gdx.gl20.glClearColor(0,0,0,0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(background, 0, 0);
		
		batch.end();
		
		menu.draw();
	}

	@Override
	public void update(float delta) {
		menu.act(delta);
	}

	@Override
	public void init(Game game) {
		batch = new SpriteBatch();
		background = new Texture(new Pixmap(Gdx.files.internal("../LDGrowth/data/titlebackground.png")), true);
		menu = new Menu(game);
		menu.getRoot().setPosition(Gdx.graphics.getWidth() / 2, 50);
		this.addProcessor(menu);
	}

	@Override
	public int getID() {
		return ID;
	}

}
