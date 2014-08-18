package com.aqua.ludum.growth.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class PlayButtonStyle extends ImageButtonStyle{

	public static ImageButtonStyle instance = new PlayButtonStyle();
	
	public PlayButtonStyle(){
		Texture playUp = new Texture(new Pixmap(Gdx.files.internal("../LDGrowth/data/playup.png")), true),
				playDown = new Texture(new Pixmap(Gdx.files.internal("../LDGrowth/data/playdown.png")), true),
				playOver = new Texture(new Pixmap(Gdx.files.internal("../LDGrowth/data/playover.png")), true);
		this.imageUp = new NinePatchDrawable(new NinePatch(playUp));
		this.imageDown = new NinePatchDrawable(new NinePatch(playDown));
		this.imageOver = new NinePatchDrawable(new NinePatch(playOver));		
	}
}
