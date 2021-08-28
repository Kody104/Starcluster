package com.gmail.jpk.stu.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gmail.jpk.stu.starcluster.Starcluster;

public abstract class BaseScreen implements Screen {
	
	protected final Starcluster game;
	protected final OrthographicCamera camera;
	protected final Viewport viewPort;
	protected float renderTime;
	
	public BaseScreen(final Starcluster game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		viewPort = new FitViewport(800, 480, camera);
		renderTime = 0.0f;
	}
}
