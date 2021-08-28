package com.gmail.jpk.stu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.jpk.stu.starcluster.Starcluster;

public class MainMenuScreen extends BaseScreen {
	
	public MainMenuScreen(final Starcluster game) {
		super(game);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.3f, 1);
		
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Under Construction!!!", 320, 300);
		game.batch.end();
		
		if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}
