package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameBoard {

    //Array of GameCell objects.
    private Array<GameCell> gameCells;

    //Current game level layout.
    private int[][] gameLevel;

    //Starting location of the game board.
    private Vector2 startLoc;

    public GameBoard(Vector2 sl, int[][] gl) {
        this.gameLevel = gl;
        this.startLoc = sl;
    }

    public void CreateBoard() {
        gameCells = new Array<GameCell>();
        for(int x = 0; x < gameLevel.length; x++) {
            for(int y = 0; y < gameLevel[x].length; y++) {
                if(gameLevel[x][y] > 0) {
                    gameCells.add(new GameCell(new Vector2(x, y)));
                }
            }
        }
    }
}
