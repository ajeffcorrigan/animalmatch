package com.ajeffcorrigan.game.animalmatch.screens;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.ajeffcorrigan.game.animalmatch.gamesystem.GameBoard;
import com.ajeffcorrigan.game.animalmatch.gamesystem.PlayerActor;
import com.ajeffcorrigan.game.animalmatch.tools.GameLevelManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;

public class GamePlayScreen extends ScreenAdapter implements InputProcessor {

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

    private ShapeRenderer shapeRenderer;

    public GamePlayScreen(AnimalMatch game) {

        // Initialize the main game object.
        this.game = game;

        Gdx.input.setInputProcessor(this);

        //Game camera and viewport setup.
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(AnimalMatch.gw, AnimalMatch.gh, gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        gamecam.setToOrtho(true);

        // Shaperenderer
        shapeRenderer = new ShapeRenderer();

        //Set start location of the game board entity
        gbLocation = new Vector2(AnimalMatch.gw * .02f, AnimalMatch.gh * .005f);

        // Initialize the game level manager object.
        glm = new GameLevelManager(this.game.gam);

        // Create the initial game board.
        gameBoard = new GameBoard(gbLocation,glm,this.game.gam.getTileSize());
    }

    @Override
    public void render(float delta) {
        update(delta);

        super.render(delta);

        //Clear the game screen
        Gdx.gl.glClearColor( 1f, 1f, 1f, 0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        gameBoard.drawBoard(game.batch);
        game.batch.end();

        shapeRenderer.setProjectionMatrix(gamecam.combined);
        gameBoard.drawBounds(shapeRenderer);
    }

    private void update(float delta) {
        gameBoard.update(delta);
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

    @Override
    public boolean keyDown(int keycode) {
        for(PlayerActor pa : gameBoard.getPlayerActors()) {
            if(pa.isPlayerSelected() && !pa.isPlayerMoving()) {
                switch (keycode) {
                    case Input.Keys.UP:
                        pa.setMoveDirection(new Vector2(0,-1));
                        break;
                    case Input.Keys.DOWN:
                        pa.setMoveDirection(new Vector2(0,1));
                        break;
                    case Input.Keys.LEFT:
                        pa.setMoveDirection(new Vector2(-1,0));
                        break;
                    case Input.Keys.RIGHT:
                        pa.setMoveDirection(new Vector2(1,0));
                        break;
                }

            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) { return false; }
    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // If game board is touched.
        if(this.gameBoard.getGbBound().contains(screenX,screenY)) {
            Gdx.app.debug(this.getClass().getSimpleName(), "GameBoard was touched.");
            for(PlayerActor pa : gameBoard.getPlayerActors()) {
                if(pa.getPlayerBounds().contains(screenX,screenY)) {
                    if(pa.isPlayerSelected()) {
                        pa.setPlayerSelected(false);
                        pa.stopMoving();
                        Gdx.app.debug(this.getClass().getSimpleName(), "PlayerActor type "+ pa.getPlayerType() +" is unselected.");
                    } else {
                        pa.setPlayerSelected(true);
                        Gdx.app.debug(this.getClass().getSimpleName(), "PlayerActor type "+ pa.getPlayerType() +" is selected.");
                    }

                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Gdx.app.debug(this.getClass().getSimpleName(), "touchDragged() function.");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
