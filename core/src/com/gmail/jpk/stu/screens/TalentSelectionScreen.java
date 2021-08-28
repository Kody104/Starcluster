package com.gmail.jpk.stu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.jpk.stu.sprites.BasicSprite;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.starcluster.Starcluster;
import com.gmail.jpk.stu.utils.AtlasData;

public class TalentSelectionScreen extends BaseScreen {

	private final GameScreen backScreen;
	private Array<BasicSprite> talentSprites;
	
	public TalentSelectionScreen(Starcluster game, final GameScreen backScreen) {
		super(game);
		
		this.backScreen = backScreen;
		this.talentSprites = new Array<BasicSprite>();
		
		createTalentScreen();
	}
	
	private void createTalentScreen() {
		BasicSprite fissionTalent = new BasicSprite(AtlasData.createSprite(AtlasData.TALENT_ATLAS, "fission"), SpriteType.TALENT_ICON);
		fissionTalent.setX(400.0f);
		fissionTalent.setY(200.0f);
		fissionTalent.setSize(0.5f);
		talentSprites.add(fissionTalent);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.0f,  0.0f,  0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.enableBlending();
		game.batch.begin();
		for(BasicSprite s: talentSprites) {
			s.render(game.batch);
		}
		// TODO:
		game.batch.end();
		game.batch.disableBlending();
	}
	
	@Override
	public void show() {
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
