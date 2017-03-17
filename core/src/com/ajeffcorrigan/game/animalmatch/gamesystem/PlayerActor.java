package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.ajeffcorrigan.game.animalmatch.tools.jAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerActor extends Sprite{
    // Speed of movement.
    private final float moveSpeed = 366.856f;
    // Location of sprite on game board in pixels.
    private Vector2 boardLocation;
    // Current cell location in rows / columns
    private Vector2 cellLocation;
    // Player bounds
    private Rectangle playerBounds;
    // Player state.
    private boolean playerSelected;
    // Should player be visible.
    private boolean drawPlayer;
    // Is Player moving?
    private boolean playerMoving;
    // Current move direction
    private Vector2 moveDirection;
    // Center of bounds
    private Vector2 centerBounds = new Vector2();
    // Player can be played?
    private boolean playableActor;
    // Unselected textureRegion id
    private int unSelectTexture;
    // Selected textureRegion id
    private int selectedTexture;
    // Name of actor
    private String actorName;
    // Has the actor been matched
    private boolean playerMatched;


    // Constructor for nonplayable actor
    public PlayerActor(int id, Vector2 loc, Vector2 scaler, String name, boolean playable, Vector2 cl) {
        super(new Sprite(jAssets.getTextureRegion(String.valueOf(id))));
        setSharedSettings(loc, scaler, name, playable, cl);
    }

    public PlayerActor(int unSelected, Vector2 loc, Vector2 scaler, String name, boolean playable, int selected, Vector2 cl) {
        // Set starting sprite as unselected value
        super(new Sprite(jAssets.getTextureRegion(String.valueOf(unSelected))));
        this.unSelectTexture = unSelected;
        this.selectedTexture = selected;
        setSharedSettings(loc, scaler, name, playable, cl);
    }

    // Shared settings for both playable and nonplayable actors
    private void setSharedSettings(Vector2 loc, Vector2 scaler, String name, boolean playable, Vector2 cl) {
        this.boardLocation = loc;
        this.setSize(this.getWidth() * scaler.x, this.getHeight() * scaler.y);
        this.actorName = name;
        this.playableActor = playable;
        this.cellLocation = cl;
        this.setPosition(boardLocation.x,boardLocation.y);
        this.setFlip(false,true);
        this.playerBounds = new Rectangle(boardLocation.x,boardLocation.y,getWidth(),getHeight());
        setCenterBounds();
        this.drawPlayer = true;
        this.playerMoving = false;
        this.moveDirection = new Vector2(0,0);
        this.playerSelected = false;
        this.playerMatched = false;
    }

    // Get the current player bounds
    public Rectangle getPlayerBounds() {
        return playerBounds;
    }
    // Checks is player actor is currently selected.
    public boolean isPlayerSelected() { return playerSelected; }
    // Gets the center coordinates of the bounding box.
    public Vector2 getCenterBounds() { return centerBounds; }
    // Checks to see if actor should be drawn
    public boolean isDrawPlayer() { return drawPlayer; }

    public void setMoveDirection(Vector2 mD) {
        if(playableActor) {
            this.playerMoving = true;
            this.moveDirection = mD;
        }
    }

    public void stopMoving() {
        if(playableActor) {
            this.moveDirection.set(0, 0);
            this.playerMoving = false;
        }
    }

    // Is the actor moving
    public boolean isPlayerMoving() { return playerMoving; }

    // Update actor position details.
    public void update(float delta) {
        this.setPosition(this.getX() + (this.moveDirection.x * delta * this.moveSpeed),this.getY() + (this.moveDirection.y * delta * this.moveSpeed));
        this.updateBounds();
    }
    // Updates the player bounds.
    public void updateBounds() {
        this.playerBounds.setPosition(this.getX(),this.getY());
        setCenterBounds();
    }

    public void updateCell(Vector2 newCell) { this.cellLocation = newCell; }

    // Updates the center point of the player
    private void setCenterBounds() {
        this.centerBounds.set(playerBounds.getX() + (playerBounds.getWidth() / 2), playerBounds.getY() + (playerBounds.getHeight() / 2));
    }

    // Sets the state of the player
    public void setPlayerSelected(boolean playerSelected) {
        if(playableActor) {
            this.playerSelected = playerSelected;
            Gdx.app.debug(this.getClass().getSimpleName(), "Actor Bounds: "+ this.playerBounds);
            Gdx.app.debug(this.getClass().getSimpleName(), "Actor Location: "+ this.boardLocation);
            if(this.playerSelected) {
                super.setRegion(jAssets.getTextureRegion(String.valueOf(selectedTexture)));
                this.setFlip(false,true);
            } else {
                super.setRegion(jAssets.getTextureRegion(String.valueOf(unSelectTexture)));
                this.setFlip(false,true);
            }
        }
    }
    // Is the actor playable?
    public boolean isPlayableActor() { return playableActor; }
    // Get actor name
    public String getActorName() { return actorName; }
    // Check if provided actor is beside actor
    public boolean isBesideActor(Vector2 paLocation) {
        if(paLocation.x == cellLocation.x) {
            if(paLocation.y == (cellLocation.y - 1) || paLocation.y == (cellLocation.y + 1)) {
                return true;
            }
        }
        if(paLocation.y == cellLocation.y) {
            if(paLocation.x == (cellLocation.x - 1) || paLocation.x == (cellLocation.x + 1)) {
                return true;
            }
        }
        return false;
    }
    // Get current cell coordinates
    public Vector2 getCellLocation() { return cellLocation; }

    public boolean isPlayerMatched() {
        return playerMatched;
    }

    public void setPlayerMatched(boolean playerMatched) {
        this.playerMatched = playerMatched;
        this.drawPlayer = false;
        this.playerSelected = false;
    }
}
