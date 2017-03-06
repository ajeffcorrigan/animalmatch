package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.ajeffcorrigan.game.animalmatch.tools.jAssets;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerActor extends Sprite{
    // Speed of movement.
    private final float moveSpeed = 113.856f;
    // Location of sprite on game board.
    private Vector2 boardLocation;
    // Player bounds
    private Rectangle playerBounds;
    // Player state.
    private boolean playerSelected;
    // Player type
    private int playerType;
    // Should player be visible.
    private boolean drawPlayer;
    // Player tile gid
    private int tileId;
    // Is Player moving?
    private boolean playerMoving;
    // Current move direction
    private Vector2 moveDirection;

    public PlayerActor(int tg, Vector2 loc, Vector2 scaler, int type) {
        super(new Sprite(jAssets.getTextureRegion(String.valueOf(tg))));
        this.tileId = tg;
        this.boardLocation = loc;
        this.setPosition(boardLocation.x,boardLocation.y);
        this.setSize(this.getWidth() * scaler.x, this.getHeight() * scaler.y);
        this.setFlip(false,true);
        this.playerBounds = new Rectangle(boardLocation.x,boardLocation.y,getWidth(),getHeight());
        this.playerType = type;
        this.playerSelected = false;
        this.drawPlayer = true;
        this.playerMoving = false;
        this.moveDirection = new Vector2(0,0);
    }

    // Get the current player bounds
    public Rectangle getPlayerBounds() {
        return playerBounds;
    }

    // Checks is player actor is currently selected.
    public boolean isPlayerSelected() { return playerSelected; }
    // Sets the state of the player
    public void setPlayerSelected(boolean playerSelected) { this.playerSelected = playerSelected; }

    public boolean isDrawPlayer() { return drawPlayer; }
    public int getPlayerType() { return playerType; }

    public void setMoveDirection(Vector2 mD) {
        this.playerMoving = true;
        this.moveDirection = mD;
    }

    public void stopMoving() {
        this.moveDirection.set(0,0);
        this.playerMoving = false;
    }

    public boolean isPlayerMoving() { return playerMoving; }

    public void update(float delta) {
        this.setPosition(this.getX() + (this.moveDirection.x * delta * this.moveSpeed),this.getY() + (this.moveDirection.y * delta * this.moveSpeed));
        this.updateBounds();
    }

    public void updateBounds() {
        this.playerBounds.setPosition(this.getX(),this.getY());
    }
}
