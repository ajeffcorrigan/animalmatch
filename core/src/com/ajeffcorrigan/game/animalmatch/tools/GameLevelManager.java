package com.ajeffcorrigan.game.animalmatch.tools;

import com.ajeffcorrigan.game.animalmatch.gamesystem.GameCell;
import com.badlogic.gdx.files.FileHandle;
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
        FileHandle xmlFile = new FileHandle(levelFile);
        // Parse and load root details.
        try { this.rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }
        // Set the level size
        this.setLevelSize(new Vector2(rootElement.getIntAttribute("width"),rootElement.getIntAttribute("height")));
        // Set the tile size
        this.setTileSize(new Vector2(rootElement.getIntAttribute("tilewidth"),rootElement.getIntAttribute("tileheight")));
        // Get sheet name which layers are associated with.
        String sheetName = rootElement.getChildByName("tileset").getAttribute("name");
        // Check for texture regions, add if not available.
        for(XmlReader.Element layerElement : rootElement.getChildrenByName("layer")) {
            for(XmlReader.Element tileElement : layerElement.getChildByName("data").getChildrenByName("tile")) {
                this.gam.checkTextureRegion(tileElement.getAttribute("gid"),sheetName);
            }
        }
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

    // Sends the tile graphics code to the game cell for processing.
    public void setTileGraphics(GameCell gc) {
        int tileNum = (int)gc.getLogicCoordinates().x + ((int)gc.getLogicCoordinates().y * (int)this.levelSize.y);
        for(XmlReader.Element layerElement : rootElement.getChildrenByName("layer")) {
            String layerGid = layerElement.getChildByName("data").getChild(tileNum).getAttribute("gid");
            if(Integer.valueOf(layerGid) >= gam.getFirstGid()) {
                gc.addSpriteLayer(layerElement.getIntAttribute("name"), jAssets.getTextureRegion(layerGid));
            }
        }
    }

    // Get the rectangular bounds for non passable areas.
    public Array<Rectangle> getNonPassBounds(Vector2 sl, Vector2 sc) {
        Array<Rectangle> npb = new Array<Rectangle>();
        for(XmlReader.Element objects : rootElement.getChildByName("objectgroup").getChildrenByName("object")) {
            npb.add(new Rectangle((objects.getFloatAttribute("x") * sc.x) + sl.x, (objects.getFloatAttribute("y") * sc.y) + sl.y, objects.getFloatAttribute("width") * sc.x, objects.getFloatAttribute("height") * sc.y ));
        }
        return npb;
    }

}
