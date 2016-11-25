package com.ajeffcorrigan.game.animalmatch;

import com.ajeffcorrigan.game.animalmatch.screens.SplashScreen;
import com.ajeffcorrigan.game.animalmatch.tools.GameAssetManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimalMatch extends Game {

	// Sprite batch handler.
	public SpriteBatch batch;

	// Game Asset Manager class
	public GameAssetManager gam;

	// Game screen size.
	public static int gw;
	public static int gh;

	@Override
	public void create() {
		// Set the logging level.
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Gdx.app.debug(this.getClass().getSimpleName(), "create() method.");

		// Set the game screen size.
		gw = Gdx.graphics.getWidth();
		gh = Gdx.graphics.getHeight();

		// Initialize the sprite batch.
		batch = new SpriteBatch();

		// Set the splash screen as starting screen.
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
