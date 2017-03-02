package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerActor extends Sprite{

    // Location of sprite on game board.
    private Vector2 boardLocation;
    // Player bounds
    private Rectangle playerBounds;
    // Player state.
    private boolean playerSelected;

    public PlayerActor(Sprite sprite, Vector2 loc, Vector2 scaler) {
        super(sprite);
        this.boardLocation = loc;
        this.setPosition(boardLocation.x,boardLocation.y);
        this.setSize(this.getWidth() * scaler.x, this.getHeight() * scaler.y);
        this.setFlip(false,true);
        this.playerBounds = new Rectangle(boardLocation.x,boardLocation.y,getWidth(),getHeight());
        this.playerSelected = false;
    }

    public Rectangle getPlayerBounds() {
        return playerBounds;
    }

    public void setPlayerBounds(Rectangle playerBounds) {
        this.playerBounds = playerBounds;
    }

    // Checks is player actor is currently selected.
    public boolean isPlayerSelected() { return playerSelected; }
    // Sets the state of the player
    public void setPlayerSelected(boolean playerSelected) { this.playerSelected = playerSelected; }
}
