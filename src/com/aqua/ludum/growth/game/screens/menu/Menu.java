package com.aqua.ludum.growth.game.screens.menu;

import com.aqua.ludum.growth.game.screens.play.PlayScreen;
import com.aqua.ludum.growth.screen.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Menu extends Stage{
	
	private ImageButton playButton;
	
	public Menu(final Game game){
		playButton = new ImageButton(PlayButtonStyle.instance);
		playButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.enterScreen(PlayScreen.ID);
			}
			
		});
		this.addActor(playButton);
	}
}
