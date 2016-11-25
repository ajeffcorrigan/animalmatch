package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by admin on 10/6/2016.
 */

public class GameCell extends Sprite {

    // Cell coordinates in array.
    private Vector2 logicCoordinates;
    // Location on the screen
    private Vector2 screenLocation;

    public GameCell(Vector2 lc) {
        this.logicCoordinates = new Vector2(lc);
    }
}
