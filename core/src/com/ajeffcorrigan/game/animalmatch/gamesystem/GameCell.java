package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.ajeffcorrigan.game.animalmatch.tools.SpriteLayer;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
    // Player Actor object
    private PlayerActor actor;

    public GameCell(Vector2 lc) {
        this.logicCoordinates = new Vector2(lc);
    }

    public GameCell(Vector2 lc, Vector2 sl) {
        this.logicCoordinates = lc;
        this.screenLocation = sl;
        this.canBeOccupied = true;
        this.bgLayer = new Array<SpriteLayer>();
        this.fgLayer = new Array<SpriteLayer>();
    }

    // Draw the background layers with spritebatch
    public void drawBackground(SpriteBatch sb) {
        for(SpriteLayer sl : bgLayer) { sl.draw(sb); }
    }
    // Draw the foreground layers with spritebatch
    public void drawForeground(SpriteBatch sb) {
        for(SpriteLayer sl : fgLayer) { sl.draw(sb); }
    }
    // Draw actor

    // Update the sprite scale on background and foreground layers.
    public void updateSpriteScale(Vector2 sImg) {
        for(SpriteLayer sl : bgLayer) { sl.setSize(sl.getWidth() * sImg.x,sl.getHeight() * sImg.y); }
        for(SpriteLayer sl : fgLayer) { sl.setSize(sl.getWidth() * sImg.x,sl.getHeight() * sImg.y); }
    }

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

    // Adds a new sprite layer, levelCode decides if background or foreground
    public void addSpriteLayer(int levelCode, TextureRegion tx) {
        // Background items and ground level items.
        if(levelCode <= 2) {
            bgLayer.add(new SpriteLayer(levelCode, new Sprite(tx), this.screenLocation));
        }
        // Foreground items, generally "above" player sprite.
        if(levelCode > 2) {
            fgLayer.add(new SpriteLayer(levelCode, new Sprite(tx), this.screenLocation));
        }
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
