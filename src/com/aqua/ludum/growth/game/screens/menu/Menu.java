package com.aqua.ludum.growth.game.screens.menu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Menu extends Stage{
	
	private ImageButton playButton;
	
	public Menu(){
		playButton = new ImageButton(PlayButtonStyle.instance);
		this.addActor(playButton);
	}
}
