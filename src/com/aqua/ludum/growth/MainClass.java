package com.aqua.ludum.growth;

import com.aqua.ludum.growth.game.GrowthGame;
import com.aqua.ludum.growth.screen.Game;
import com.aqua.ludum.growth.screen.GameApp;

public class MainClass extends GameApp {

	@Override
	public Game game() {
		return new GrowthGame();
	}
	
}
