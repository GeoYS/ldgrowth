package com.aqua.ludum.growth.game;

import com.aqua.ludum.growth.game.screens.menu.MenuScreen;
import com.aqua.ludum.growth.game.screens.play.PlayScreen;
import com.aqua.ludum.growth.screen.*;

public class GrowthGame extends Game{

	@Override
	public void create() {	
		this.addScreen(new MenuScreen());
		this.addScreen(new PlayScreen());
		this.initScreens();
		this.enterScreen(MenuScreen.ID);
	}

}
