package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.ajeffcorrigan.game.animalmatch.tools.GameLevelManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameBoard {

    // Array of GameCell objects.
    private Array<GameCell> gameCells;
    // Size of the game board in vector2
    private Vector2 gameBoardSize;
    // Starting location of the game board on the screen.
    private Vector2 startLoc;
    // Game Level Manager Object
    private GameLevelManager glm;
    // Tile Size
    private Vector2 tileSize;
    // Scale to fit screen
    private float scaleImage;

    public GameBoard(Vector2 sl, Vector2 gl, GameLevelManager glm, Vector2 tSz) {
        // Start location of the game board.
        this.startLoc = sl;
        // Size of game board, row by column
        this.gameBoardSize = gl;
        this.glm = glm;
        this.tileSize = tSz;
        this.scaleImage = 1f;
        this.CreateBlankBoard();
        this.populateBoard();
        this.updateScale();
    }

    private void CreateBlankBoard() {
        Gdx.app.debug(this.getClass().getSimpleName(), "Creating a new empty game board.");
        gameCells = new Array<GameCell>();
        for(int x = 0; x < gameBoardSize.x; x++) {
            for(int y = 0; y < gameBoardSize.y; y++) {
                gameCells.add(new GameCell(new Vector2(x, y),new Vector2((this.tileSize.x * x) + startLoc.x, (this.tileSize.y * y) + startLoc.y)));
            }
        }
    }

    private void populateBoard() {
        for(GameCell gc : gameCells) {
            glm.updateGameCell(gc);
        }
        Gdx.app.debug(this.getClass().getSimpleName(), "Exiting populateBoard function.");
    }


    private void updateScale() {
        for(GameCell gc : gameCells) {
            gc.updateSpriteScale(scaleImage);
        }
    }

    // Draws the board to the sprite batch.
    public void drawBoard(SpriteBatch sb) {
        for(GameCell gc : gameCells) {
            gc.drawAll(sb);
        }
    }

    public Vector2 getCellLocation(Vector2 xyLoc) {
        for(GameCell gc : gameCells) {
            Vector2 curLoc = gc.getLogicCoordinates();
            if(curLoc.sub(xyLoc).isZero()) {
                return gc.getScreenLocation();
            }
        }
        return null;
    }

    public Array<GameCell> getGameCells() { return gameCells; }

}
