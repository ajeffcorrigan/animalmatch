package com.ajeffcorrigan.game.animalmatch;

import com.ajeffcorrigan.game.animalmatch.screens.GamePlayScreen;
import com.ajeffcorrigan.game.animalmatch.screens.MainMenuScreen;
import com.ajeffcorrigan.game.animalmatch.screens.SplashScreen;
import com.ajeffcorrigan.game.animalmatch.tools.ScreenHandler;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AnimalMatch extends Game {

	public ScreenHandler sHandler;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(this.getClass().getSimpleName(), "create() method.");

		sHandler = new ScreenHandler();
		sHandler.AddScreen("splash",new SplashScreen(this),true);
		sHandler.AddScreen("mainmenu",new MainMenuScreen(this),false);
		sHandler.AddScreen("gameplay",new GamePlayScreen(this),false);
		setScreen(sHandler.getScreen());
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
