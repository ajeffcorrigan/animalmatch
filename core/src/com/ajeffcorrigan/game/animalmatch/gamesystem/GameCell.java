package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.ajeffcorrigan.game.animalmatch.tools.SpriteLayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by admin on 10/6/2016.
 */

public class GameCell {

    // Cell coordinates in array.
    private Vector2 logicCoordinates;
    // Location on the screen
    private Vector2 screenLocation;
    // Array of sprite layers for background
    private Array<SpriteLayer> bgLayer;
    // Array of sprite layers for foreground
    private Array<SpriteLayer> fgLayer;
    // Can be occupied by player
    private boolean canBeOccupied;

    public GameCell(Vector2 lc) {
        this.logicCoordinates = new Vector2(lc);
    }

    public GameCell(Vector2 lc, Vector2 sl) {
        this.logicCoordinates = lc;
        this.screenLocation = sl;
        this.canBeOccupied = true;
        this.bgLayer = new Array<SpriteLayer>();
    }

    public void drawBackground(SpriteBatch sb) {
        for(SpriteLayer sl : bgLayer) {
            sl.draw(sb);
        }
    }

    public void updateSpriteScale(Vector2 sImg) {
        for(SpriteLayer sl : bgLayer) {
            //sl.setScale(sImg.x,sImg.y);
            sl.setSize(sl.getWidth() * sImg.x,sl.getHeight() * sImg.y);
        }
    }

    public void drawForeground() {}

    public void drawAll(SpriteBatch sb) {
        drawBackground(sb);
    }

    public Vector2 getLogicCoordinates() { return logicCoordinates; }
    public void setLogicCoordinates(Vector2 logicCoordinates) { this.logicCoordinates = logicCoordinates; }

    public Vector2 getScreenLocation() {
        return screenLocation;
    }

    public void setScreenLocation(Vector2 screenLocation) {
        this.screenLocation = screenLocation;
    }

    public boolean isCanBeOccupied() { return canBeOccupied; }
    public void setCanBeOccupied(boolean canBeOccupied) { this.canBeOccupied = canBeOccupied; }

    public void addSpriteLayer(int lev, TextureRegion tx) {
        bgLayer.add(new SpriteLayer(lev, new Sprite(tx), this.screenLocation));
        Gdx.app.debug(this.getClass().getSimpleName(), "Exiting addSpriteLayer function.");
    }

    public SpriteLayer spriteLayerExists(int id) {
        for(SpriteLayer sp : bgLayer) {
            if(sp.getLayerLevel() == id) {
                return sp;
            }
        }
        return null;
    }

    public void updateSpriteLayer(int lev, TextureRegion tx) {
        for(SpriteLayer sp : bgLayer) {
            if(sp.getLayerLevel() == lev) {
                sp.set(new Sprite(tx));
            }
        }
    }
}
