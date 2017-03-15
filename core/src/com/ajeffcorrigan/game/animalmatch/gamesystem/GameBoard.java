package com.ajeffcorrigan.game.animalmatch.gamesystem;

import com.ajeffcorrigan.game.animalmatch.AnimalMatch;
import com.ajeffcorrigan.game.animalmatch.tools.GameLevelManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GameBoard {

    // Array of GameCell objects.
    private Array<GameCell> gameCells;
    // Array of GameCell objects.
    private Array<PlayerActor> playerActors;
    // Array of GameCell objects.
    private Array<PlayerActor> nonPlayActors;
    // Array of non moving actor bodies
    private Array<Rectangle> nonMovingActors;
    // Array of non moving actor bodies
    private Array<Rectangle> nonPlayableBounds;
    // Size of the game board in vector2
    private Vector2 gameBoardSize;
    // Starting location of the game board on the screen.
    private Vector2 startLoc;
    // Game Level Manager Object
    private GameLevelManager glm;
    // Tile Size
    private Vector2 tileSize;
    // Scale to fit screen
    private Vector2 scaleImage = new Vector2(1f,1f);
    // Game Board size
    private Vector2 gbSize;
    // Array of rectangle bounds (non passable)
    private Array<Rectangle> nonPassBounds;
    // Game board bound
    private Rectangle gbBound;

    public GameBoard(Vector2 sl, GameLevelManager glm, Vector2 tSz) {
        // Start location of the game board.
        this.startLoc = sl;
        // Size of game board, row by column
        this.gameBoardSize = glm.getLevelSize();
        // Game Level Manager Object
        this.glm = glm;
        // Sets the tile size based on the Game Asset Manager value
        this.tileSize = tSz;
        // Board size.
        this.gbSize = new Vector2(AnimalMatch.gw - ((AnimalMatch.gw * .02f) * 2), AnimalMatch.gh - (AnimalMatch.gh * .30f));
        // Initialize the actors
        this.playerActors = new Array<PlayerActor>();
        // Initialize the non playable actors
        this.nonPlayActors = new Array<PlayerActor>();
        //Initialize the Game Board bounds.
        this.gbBound = new Rectangle(sl.x,sl.y,gbSize.x,gbSize.y);
        // Set the scale of the board based on board size
        this.setScale();
        // Create a blank board with coordinates and cell locations.
        this.CreateBoard();
    }

    // Create a new board based on the size of the map.
    private void CreateBoard() {
        gameCells = new Array<GameCell>();
        // Creates the initial board array and sets graphics and scale.
        for(int y = 0; y < gameBoardSize.y; y++) {
            for(int x = 0; x < gameBoardSize.x; x++) {
                gameCells.add(new GameCell(new Vector2(x, y),new Vector2(((this.tileSize.x * x) * this.scaleImage.x) + startLoc.x, ((this.tileSize.y * y) * this.scaleImage.y) + startLoc.y)));
                this.glm.setTileGraphics(gameCells.peek());
                gameCells.peek().updateSpriteScale(scaleImage);
                // Check if actor exists at this location.
                int tCode = glm.ifActorExists(gameCells.peek().getTileCode((int)gameBoardSize.y));
                if(tCode > 0) {
                    XmlReader.Element actorPropterties = glm.getActorDetails(tCode);
                    String actorName = "";
                    boolean isPlayable = false;
                    if(actorPropterties != null) {
                        for(XmlReader.Element actorProperty : actorPropterties.getChildrenByNameRecursively("property")) {
                            if(actorProperty.get("name").equalsIgnoreCase("name")) { actorName = actorProperty.get("value"); }
                            if(actorProperty.get("name").equalsIgnoreCase("playable")) { isPlayable = actorProperty.getBoolean("value"); }
                        }
                        if(isPlayable) {
                            playerActors.add(new PlayerActor(tCode,gameCells.peek().getScreenLocation(),scaleImage,actorName,isPlayable,tCode, gameCells.peek().getLogicCoordinates()));
                        } else {
                            nonPlayActors.add(new PlayerActor(tCode,gameCells.peek().getScreenLocation(),scaleImage,actorName,isPlayable, gameCells.peek().getLogicCoordinates()));
                        }
                    }
                }
            }
        }
        // Get the non-passable areas.
        nonPassBounds = glm.getNonPassBounds(startLoc, scaleImage);
    }

    // Updates the scale of the board's graphics, should be called if scaleImage variable changes.
    private void updateScale() { for(GameCell gc : gameCells) { gc.updateSpriteScale(scaleImage); } }

    // Draws the board to the sprite batch.
    public void drawBoard(SpriteBatch sb) {
        // Draw background
        for(GameCell gc : gameCells) { gc.drawBackground(sb); }
        // Draw player
        for(PlayerActor pa : playerActors) {
            if (pa.isDrawPlayer()) { pa.draw(sb); }
        }
        // Draw player
        for(PlayerActor pa : nonPlayActors) {
            if (pa.isDrawPlayer()) { pa.draw(sb); }
        }
        // Draw foreground
        for(GameCell gc : gameCells) { gc.drawForeground(sb); }
    }

    // Run through updates.
    public void update(float delta) {
        // Process each player.
        for(PlayerActor pa : playerActors) {
            if (pa.isPlayerMoving()) {
                // Check bounds of board for collision
                if(!gbBound.contains(pa.getPlayerBounds().getX(),pa.getPlayerBounds().getY()) || !gbBound.contains(pa.getPlayerBounds().getX()+pa.getPlayerBounds().getWidth(),pa.getPlayerBounds().getY()+pa.getPlayerBounds().getHeight())) {
                    pa.stopMoving();
                }
                // Check bounds of non pass zones, trees, boxes, etc.
                for(Rectangle npB : nonPassBounds) { if(npB.overlaps(pa.getPlayerBounds())) { pa.stopMoving(); } }
                // Check for bounds of non player bounds
                for(PlayerActor nPA : nonPlayActors) {
                    if(nPA.getBoundingRectangle().overlaps(pa.getPlayerBounds()) && !nPA.isPlayerMatched()) {
                        pa.stopMoving();
                    }
                }


                // If the bounds have been touched, player is no longer moving, update coordinates.
                if(!pa.isPlayerMoving()) {
                    for(GameCell gc : gameCells) {
                        if(new Rectangle(gc.getScreenLocation().x,gc.getScreenLocation().y,this.tileSize.x,this.tileSize.y).contains(pa.getCenterBounds())) {
                            pa.setPosition(gc.getScreenLocation().x,gc.getScreenLocation().y);
                            pa.updateBounds();
                            pa.updateCell(gc.getLogicCoordinates());
                        }
                    }
                    for(PlayerActor nPA : nonPlayActors) {
                        // Only check same name actors
                        if(nPA.getActorName().equalsIgnoreCase(pa.getActorName()) && (!nPA.isPlayerMatched())) {
                            if(nPA.isBesideActor(pa.getCellLocation())) {
                                Gdx.app.debug(this.getClass().getSimpleName(), "PlayerActor "+ pa.getActorName() +" is beside same.");
                                pa.setPlayerMatched(true);
                                nPA.setPlayerMatched(true);
                            }
                        }
                    }

                } else {
                    // Update player if still moving.
                    pa.update(delta);
                }
                break;
            }
        }
    }

    // Draw the bounds
    public void drawBounds(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLACK);
        for(Rectangle r : nonPassBounds) { sr.rect(r.getX(),r.getY(),r.getWidth(),r.getHeight()); }
        for(PlayerActor pa : playerActors) {
            sr.rect(pa.getPlayerBounds().getX(),pa.getPlayerBounds().getY(),pa.getPlayerBounds().getWidth(),pa.getPlayerBounds().getHeight());
        }
        sr.line(startLoc,new Vector2(startLoc.x,startLoc.y + gbSize.y));
        sr.line(startLoc,new Vector2(startLoc.x + gbSize.x,startLoc.y));
        sr.line(new Vector2(startLoc.x + gbSize.x, startLoc.y),new Vector2(startLoc.x + gbSize.x, startLoc.y + gbSize.y));
        sr.line(new Vector2(startLoc.x, startLoc.y + gbSize.y),new Vector2(startLoc.x + gbSize.x, startLoc.y + gbSize.y));
        sr.end();
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

    private void setScale() {
        BigDecimal bd;
        int decPlaces = 3;

        // Set Image scale
        this.scaleImage.x = (this.gbSize.x / this.gameBoardSize.x) / this.tileSize.x;
        bd = new BigDecimal(this.scaleImage.x).setScale(decPlaces, RoundingMode.HALF_DOWN);
        this.scaleImage.x = bd.floatValue();

        this.scaleImage.y = (this.gbSize.y / this.gameBoardSize.y) / this.tileSize.y;
        bd = new BigDecimal(this.scaleImage.y).setScale(decPlaces, RoundingMode.HALF_DOWN);
        this.scaleImage.y = bd.floatValue();

    }

    // Return the game board bounds
    public Rectangle getGbBound() { return gbBound; }
    // Return the player actor array
    public Array<PlayerActor> getPlayerActors() { return this.playerActors; }
    // If player is selected, unselect other players

}
