package com.ajeffcorrigan.game.animalmatch.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SpriteLayer extends Sprite {

    // Layer level of sprite.
    private int layerLevel;
    // Cell bounds
    public Rectangle cellBounds;

    // Cell location for sprite.
    private Vector2 cellLocation;

    public SpriteLayer() {
        Gdx.app.debug(this.getClass().getSimpleName(), "Blank SpriteLayer constructor.");
    }

    public SpriteLayer(int sl, Sprite sprite, Vector2 loc) {
        super(sprite);
        this.layerLevel = sl;
        this.cellLocation = loc;
        this.setPosition(cellLocation.x,cellLocation.y);
        this.setFlip(false,true);
    }

    public int getLayerLevel() { return layerLevel; }

    public Vector2 getCellLocation() { return cellLocation; }
    public void setCellLocation(Vector2 cellLocation) { this.cellLocation = cellLocation; }

}
