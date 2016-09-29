package com.ajeffcorrigan.game.animalmatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by jacorrigan on 9/29/2016.
 */

public class MainMenuScreen extends ScreenAdapter {


    public MainMenuScreen() {
        Gdx.app.debug(this.getClass().getSimpleName(), "MainMenuScreen constructor.");
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.debug(this.getClass().getSimpleName(), "show() function.");
    }
}
