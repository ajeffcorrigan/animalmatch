package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameBoard {

    //Array of GameCell objects.
    private Array<GameCell> gameCells;

    private Vector2 gameBoardSize;

    //Starting location of the game board.
    private Vector2 startLoc;

    public GameBoard(Vector2 sl, Vector2 gl) {
        this.gameBoardSize = gl;
        this.startLoc = sl;
    }

    public void CreateBoard() {
        Gdx.app.debug(this.getClass().getSimpleName(), "Creating new game board.");
        gameCells = new Array<GameCell>();
        for(int x = 0; x < gameBoardSize.x; x++) {
            for(int y = 0; y < gameBoardSize.y; y++) {
                gameCells.add(new GameCell(new Vector2(x, y)));
            }
        }
    }

    public void draw(SpriteBatch sb) {
        for(GameCell gc : gameCells) {  }
    }
}
