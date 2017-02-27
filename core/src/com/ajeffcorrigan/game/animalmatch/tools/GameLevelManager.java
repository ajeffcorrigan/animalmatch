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
    private Vector2 tileSize;
    // Game Asset Manager object
    private GameAssetManager gam;


    public GameLevelManager(GameAssetManager gam) {
        // GameAssetManager object
        this.gam = gam;
        // Set initial level to zero.
        this.currentInternalLevel = 0;
        // Load the level.
        this.LoadLevel(this.currentInternalLevel);
        // Populate the initial level's details
        this.currentLvlElement = rootElement.getChild(currentInternalLevel);
    }

    // Load the level file.
    private void LoadLevel(int lvl) {
        // Construct the level file.
        String levelFile = "level_"+ this.currentInternalLevel +".xml";
        // Initialize the XML reader.
        reader = new XmlReader();
        // Initialize the file handler.
        xmlFile = new FileHandle(levelFile);
        // Parse and load root details.
        try { rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }
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
            if((cell.getInt("x") == gc.getLogicCoordinates().x && cell.getInt("y") == gc.getLogicCoordinates().y) ||
                    (cell.getInt("x") == -1 && cell.getInt("y") == gc.getLogicCoordinates().y) ||
                    (cell.getInt("x") == gc.getLogicCoordinates().x && cell.getInt("y") == -1) ||
                    (cell.getInt("x") == -1 && cell.getInt("y") == -1)) {
                gc.setCanBeOccupied(cell.getBoolean("canoccupy"));
                for(XmlReader.Element imgElement : cell.getChildrenByName("bg")) {
                    if(gc.spriteLayerExists(imgElement.getInt("id")) != null) {

                    } else {
                        gc.addSpriteLayer(imgElement.getInt("id"),jAssets.getTextureRegion(imgElement.get("img")));
                    }
                }
            }

        }
    }

}
