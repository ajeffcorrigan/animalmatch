package com.ajeffcorrigan.game.animalmatch.screens;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.ajeffcorrigan.game.animalmatch.tools.ScreenHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by jacorrigan on 9/29/2016.
 */

public class SplashScreen extends ScreenAdapter {

    private boolean assetsLoaded = false;
    private AnimalMatch game;

    public SplashScreen(AnimalMatch game) {
        Gdx.app.debug(this.getClass().getSimpleName(), "SplashScreen constructor.");
        this.game = game;
    }

    private void update(float delta) {
        if(assetsLoaded) {
            Gdx.app.debug(this.getClass().getSimpleName(), "assets have been loaded.");
            this.game.setScreen(this.game.sHandler.getScreen("mainmenu"));
            this.dispose();
        } else {
            Gdx.app.debug(this.getClass().getSimpleName(), "assets have not been loaded.");
        }
        assetsLoaded = true;
    }

    @Override
    public void show() {
        Gdx.app.debug(this.getClass().getSimpleName(), "show() function.");
    }

    @Override
    public void render(float delta) {
        this.update(delta);
        Gdx.app.debug(this.getClass().getSimpleName(), "render() function.");
    }

    @Override
    public void dispose() {

        super.dispose();
    }
}
