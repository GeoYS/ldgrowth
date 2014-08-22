package com.aqua.ludum.growth;

import com.aqua.ludum.growth.game.GrowthGame;
import com.aqua.ludum.growth.screen.Game;
import com.aqua.ludum.growth.screen.GameApp;
import com.badlogic.gdx.graphics.FPSLogger;

public class MainClass extends GameApp {

	FPSLogger fps = new FPSLogger();
	
	@Override
	public Game game() {
		return new GrowthGame();
	}
	
	@Override
	public void render() {
		super.render();
		fps.log();
	}
	
}
