package com.ajeffcorrigan.game.animalmatch.screens;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.ajeffcorrigan.game.animalmatch.gamesystem.GameBoard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by jacorrigan on 9/29/2016.
 */

public class GamePlayScreen extends ScreenAdapter {

    private AnimalMatch game;

    private boolean gameScreenInit = false;

    private GameBoard gameBoard;

    //basic play screen variables
    public OrthographicCamera gamecam;
    private Viewport gamePort;

    public GamePlayScreen(AnimalMatch game) {

        this.game = game;

        //Game camera and viewport setup.
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(AnimalMatch.gw, AnimalMatch.gh, gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        gamecam.setToOrtho(false);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Clear the game screen
        Gdx.gl.glClearColor( 0f, 0f, 0f, 0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.debug(this.getClass().getSimpleName(), "show() function.");
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
