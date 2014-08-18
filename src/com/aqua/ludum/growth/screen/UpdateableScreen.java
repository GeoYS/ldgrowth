package com.aqua.ludum.growth.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * A screen with an implementable update method and
 * overridable input listener methods.
 * Render is called before update is called.
 * Upon leaving a screen, hide() is called.
 * Conversely, show() is called when entering a screen.
 * @author GeoYS_2
 *
 */
public abstract class UpdateableScreen implements Screen, InputProcessor{
	
	@Override
	public final void render(float delta) {
		update(delta);
		render();
	}
	
	public abstract void render();

	public abstract void update(float delta);
	
	public abstract void init(Game game);
	
	public abstract int getID();
	
	@Override
	public void dispose() {
	}
	
	@Override
	public void hide() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void show() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
}
