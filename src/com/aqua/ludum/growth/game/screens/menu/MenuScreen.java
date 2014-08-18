package com.aqua.ludum.growth.game.screens.menu;

import com.aqua.ludum.growth.screen.Game;
import com.aqua.ludum.growth.screen.GameScreen;
import com.badlogic.gdx.Gdx;
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
		
		batch.draw(background, 0, 0);
		
		batch.end();
	}

	@Override
	public void update(float delta) {
		menu.act(delta);
	}

	@Override
	public void init(Game game) {
		batch = new SpriteBatch();
		background = new Texture(new Pixmap(Gdx.files.internal("../LDGrowth/data/titlebackground.png")), true);
		menu = new Menu();
		this.addProcessor(menu);
	}

	@Override
	public int getID() {
		return ID;
	}

}
