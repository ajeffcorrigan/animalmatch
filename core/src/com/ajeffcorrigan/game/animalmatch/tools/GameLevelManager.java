package com.ajeffcorrigan.game.animalmatch.tools;

import com.ajeffcorrigan.game.animalmatch.gamesystem.GameCell;
import com.ajeffcorrigan.game.animalmatch.gamesystem.PlayerActor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import java.io.IOException;

public class GameLevelManager {

    // Holds the current internal level.
    private int currentInternalLevel;
    // Tile Size in pixels
    private Vector2 tileSize;
    // Game Asset Manager object
    private GameAssetManager gam;
    // Level Size in rows and cols
    private Vector2 levelSize;
    // Current level XML Reader object
    private XmlReader reader;
    // Root Element object
    private XmlReader.Element rootElement = null;

    public GameLevelManager(GameAssetManager gam) {
        // GameAssetManager object
        this.gam = gam;
        // Set initial level to zero.
        this.currentInternalLevel = 0;
        // Load the level.
        this.LoadLevel(this.currentInternalLevel);
    }

    // Load the level file.
    private void LoadLevel(int lvl) {
        // Construct the level file.
        String levelFile = "level_"+ this.currentInternalLevel +".xml";
        // Initialize the XML reader.
        // XML reader
        reader = new XmlReader();
        // Initialize the file handler.
        // Initialize the file handler.
        FileHandle xmlFile = Gdx.files.internal(levelFile);
        // Parse and load root details.
        try { this.rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }
        // Set the level size in rows and columns
        this.levelSize = new Vector2(rootElement.getInt("width"),rootElement.getInt("height"));
        // Set the tile size
        this.tileSize = gam.getTileSize();
        // Check for texture regions, add if not available.
        for(XmlReader.Element layer : rootElement.getChildrenByName("layer")) {
            for(XmlReader.Element tileData : layer.getChildByName("data").getChildrenByName("tile")) {
                if(!gam.textureRegionExists(tileData.get("gid"))) {
                    gam.addTextureRegionById(tileData.getInt("gid"));
                }
            }
        }
    }
    public void loadNextLevel() {
        this.currentInternalLevel = this.currentInternalLevel + 1;
        this.reader = null;
        this.rootElement = null;
        this.levelSize = null;
        LoadLevel(this.currentInternalLevel);
    }
    // Get current level for display (not zero based).
    public int getCurrentLevel() { return currentInternalLevel + 1; }
    // Set current level, accepts displayed level (not zero based).
    public void setCurrentLevel(int currentLevel) {
        if(currentLevel > 0) {
            this.currentInternalLevel = currentLevel - 1;
        }
    }
    // Get the level's size, by row and column.
    public Vector2 getLevelSize() { return this.levelSize; }
    // Set the level's size
    private void setLevelSize(Vector2 ls) { this.levelSize = ls; }
    // Get the tile size for the map
    public Vector2 getTileSize() { return this.tileSize; }
    // Set the tile size for the map
    private void setTileSize(Vector2 ts) { this.tileSize = ts; }
    // Sets the background tiles
    public void setTileGraphics(GameCell gc) {
        int tileNum = (int)gc.getLogicCoordinates().x + ((int)gc.getLogicCoordinates().y * (int)this.levelSize.y);
        for(XmlReader.Element layerElement : rootElement.getChildrenByName("layer")) {
            if(!layerElement.getAttribute("name").equalsIgnoreCase("actors")) {
                String layerGid = layerElement.getChildByName("data").getChild(tileNum).get("gid");
                if(Integer.valueOf(layerGid) > 0) {
                    gc.addSpriteLayer(layerElement.getIntAttribute("name"), jAssets.getTextureRegion(layerGid));
                }
            }
        }
    }
    // Checks if a player exists on this tile returns a tile larger than 0.
    public int ifActorExists(int tileNum) {
        for(XmlReader.Element layerElement : rootElement.getChildrenByNameRecursively("layer")) {
            if(layerElement.getAttribute("name").equalsIgnoreCase("actors")) {
                return layerElement.getChildByName("data").getChild(tileNum).getInt("gid");
            }
        }
        return 0;
    }
    // Return the property element for the actor
    public XmlReader.Element getActorDetails(int layerGid) {
        SheetManager sm = gam.getSheetByTileId(layerGid);
        int actorGid = (layerGid - sm.getFirstId());
        for(XmlReader.Element tileSet : rootElement.getChildrenByNameRecursively("tileset")) {
            if(tileSet.get("name").equalsIgnoreCase(sm.getAssetName())) {
                for(XmlReader.Element tileElement : tileSet.getChildrenByNameRecursively("tile")) {
                    if(tileElement.getInt("id") == actorGid) {
                        return tileElement.getChild(0);
                    }
                }
            }
        }
        return null;
    }
    // Get the rectangular bounds for non passable areas.
    public Array<Rectangle> getNonPassBounds(Vector2 sl, Vector2 sc) {
        Array<Rectangle> npb = new Array<Rectangle>();
        for(XmlReader.Element objects : rootElement.getChildByName("objectgroup").getChildrenByName("object")) {
            npb.add(new Rectangle((objects.getFloatAttribute("x") * sc.x) + sl.x, (objects.getFloatAttribute("y") * sc.y) + sl.y, objects.getFloatAttribute("width") * sc.x, objects.getFloatAttribute("height") * sc.y ));
        }
        return npb;
    }
    // Verify the texture region exists, if not add it.
    public void verifyTextureRegion(int c) {
        if(!gam.textureRegionExists(c)) { gam.addTextureRegionById(c); }
    }

}
