package com.ajeffcorrigan.game.animalmatch.screens;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by jacorrigan on 9/29/2016.
 */

public class MainMenuScreen extends ScreenAdapter {

    private AnimalMatch game;

    public MainMenuScreen(AnimalMatch game) {
        Gdx.app.debug(this.getClass().getSimpleName(), "MainMenuScreen constructor.");
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.debug(this.getClass().getSimpleName(), "show() function.");
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen
        Gdx.gl.glClearColor( .80f, .80f, .85f, 0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

    private void update(float delta) {
        if (Gdx.input.justTouched()) {
            Gdx.app.debug(this.getClass().getSimpleName(), "Screen has been touched.");
            Gdx.app.debug(this.getClass().getSimpleName(), "delta value: " + delta);
            this.game.setScreen(this.game.sHandler.getScreen("gameplay"));
            this.dispose();
        }
    }


}
