package com.ajeffcorrigan.game.animalmatch.tools;

import com.ajeffcorrigan.game.animalmatch.gamesystem.GameCell;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

/**
 * Created by jacorrigan on 11/25/2016.
 */

public class GameLevelManager {

    // Holds the current internal level.
    private int currentInternalLevel;
    // XML reader
    private XmlReader reader;
    // Root element
    private XmlReader.Element rootElement = null;
    // Current level branch
    private XmlReader.Element currentLvlElement = null;
    // File Handler
    private FileHandle xmlFile;
    // Tile Size
    public int tileSize = 64;


    public GameLevelManager() {
        // Initialize the XML reader.
        reader = new XmlReader();
        // Initialize the file handler.
        xmlFile = new FileHandle("levels.xml");
        // Parse and load root details.
        try { rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }
        // Set initial level to zero.
        this.currentInternalLevel = 0;
        // Populate the initial level's details
        this.currentLvlElement = rootElement.getChild(currentInternalLevel);
    }

    // Get current level for display (not zero based).
    public int getCurrentLevel() { return currentInternalLevel + 1; }

    // Set current level, accepts displayed level (not zero based).
    public void setCurrentLevel(int currentLevel) {
        if(currentLevel > 0) {
            this.currentInternalLevel = currentLevel - 1;
            this.currentLvlElement = rootElement.getChild(currentInternalLevel);
        }
    }

    // Get the level's size, by row and column.
    public Vector2 getLevelSize() {
        return new Vector2(currentLvlElement.getInt("x"),currentLvlElement.getInt("y"));
    }

    public void updateGameCell(GameCell gc) {
        for(XmlReader.Element cell : this.currentLvlElement.getChildrenByName("cell")) {
            if((cell.getInt("row") == gc.getLogicCoordinates().x && cell.getInt("col") == gc.getLogicCoordinates().y) ||
                    (cell.getInt("row") == -1 && cell.getInt("col") == gc.getLogicCoordinates().y) ||
                    (cell.getInt("row") == gc.getLogicCoordinates().x && cell.getInt("col") == -1) ||
                    (cell.getInt("row") == -1 && cell.getInt("col") == -1)) {
                gc.setCanBeOccupied(cell.getBoolean("canoccupy"));
                for(XmlReader.Element img : cell.getChildrenByName("bg")) {
                    if(gc.spriteLayerExists(img.getInt("id")) != null) {

                    } else {
                        gc.addSpriteLayer(img.getInt("id"),jAssets.getTextureRegion(img.get("img")));
                    }
                }
            }

        }
    }

}
