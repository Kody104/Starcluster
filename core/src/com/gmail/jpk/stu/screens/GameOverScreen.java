package com.gmail.jpk.stu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.jpk.stu.sprites.DisplayableText;
import com.gmail.jpk.stu.sprites.DisplayableText.TextType;
import com.gmail.jpk.stu.starcluster.Starcluster;

public class GameOverScreen extends BaseScreen {

	private DisplayableText overText;
	
	public GameOverScreen(Starcluster game) {
		super(game);
		overText = new DisplayableText(game.font, "Game Over", TextType.H1);
		overText.setX(400 - (overText.getTextWidth() / 2.0f));
		overText.setY(240 + overText.getTextHeight());
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.enableBlending();
		
		game.batch.begin();
		overText.render(game.batch);
		game.batch.end();
		
		game.batch.disableBlending();
		
		renderTime += Gdx.graphics.getDeltaTime();
		
		if(Gdx.input.justTouched()) {
			if(renderTime > 1.0f) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
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
