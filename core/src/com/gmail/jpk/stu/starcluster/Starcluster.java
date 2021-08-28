package com.gmail.jpk.stu.starcluster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.screens.MainMenuScreen;
import com.gmail.jpk.stu.utils.GlobalUtility;

public class Starcluster extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public Starcluster(int height, int width) {
		GlobalUtility.setScreenDimension(height, width);
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
