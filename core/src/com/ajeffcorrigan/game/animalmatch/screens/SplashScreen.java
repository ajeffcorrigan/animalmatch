package com.ajeffcorrigan.game.animalmatch.screens;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

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
            Gdx.app.debug(this.getClass().getSimpleName(), "delta value: " + delta);
            this.game.setScreen(new MainMenuScreen(this.game));
            this.dispose();
        } else {
            Gdx.app.debug(this.getClass().getSimpleName(), "delta value: " + delta);
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

        //Clear the game screen
        Gdx.gl.glClearColor( .20f, .20f, .20f, 0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        Gdx.app.debug(this.getClass().getSimpleName(), "render() function.");
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
