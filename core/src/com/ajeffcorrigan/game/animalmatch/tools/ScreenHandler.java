package com.ajeffcorrigan.game.animalmatch.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by jacorrigan on 9/29/2016.
 */

public class ScreenHandler {

    private ArrayMap<String,Screen> screens;
    private boolean isCurrenScreenEnded = false;
    private int currentIndex = 0;
    private String startScreen;
    private String currentScreen;
    private String activeScreen;

    public ScreenHandler() {
        Gdx.app.debug(this.getClass().getSimpleName(), "ScreenHandler constructor.");
        screens = new ArrayMap<String,Screen>();
    }

    public void AddScreen(String sCode, Screen screen, boolean isStartScreen){
        screens.put(sCode,screen);
        if(isStartScreen) { this.startScreen = sCode; }
    }

    public void endCurrentScreen() {
        isCurrenScreenEnded = true;
    }

    public boolean isCurrenScreenEnded() { return isCurrenScreenEnded; }

    public Screen getScreen() {
        this.activeScreen = this.startScreen;
        return screens.get(this.startScreen);
    }

    public Screen getScreen(String sc) {
        this.activeScreen = sc;
        return screens.get(sc);
    }

}
