package com.ajeffcorrigan.game.animalmatch.screens;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.ajeffcorrigan.game.animalmatch.gamesystem.GameBoard;
import com.ajeffcorrigan.game.animalmatch.gamesystem.GameCell;
import com.ajeffcorrigan.game.animalmatch.tools.GameLevelManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;

/**
 * Created by jacorrigan on 9/29/2016.
 */

public class GamePlayScreen extends ScreenAdapter {

    // AnimalMatch game object.
    private AnimalMatch game;
    // Game Level Manager
    private GameLevelManager glm;
    // Game Board object.
    private GameBoard gameBoard;
    // Starting location of gameboard entity
    private Vector2 gbLocation;

    //basic play screen variables
    public OrthographicCamera gamecam;
    private Viewport gamePort;

    public GamePlayScreen(AnimalMatch game) {

        // Initialize the main game object.
        this.game = game;

        //Game camera and viewport setup.
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(AnimalMatch.gw, AnimalMatch.gh, gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        gamecam.setToOrtho(true);

        //Set start location of the game board entity
        gbLocation = new Vector2(AnimalMatch.gw * .025f,AnimalMatch.gh * .005f);

        // Initialize the game level manager object.
        glm = new GameLevelManager();

        gameBoard = new GameBoard(gbLocation,glm.getLevelSize(),glm,new Vector2(this.game.gam.xSize,this.game.gam.xSize));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //Clear the game screen
        Gdx.gl.glClearColor( 0f, 0f, 0f, 0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        gameBoard.drawBoard(game.batch);
        game.batch.end();
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
