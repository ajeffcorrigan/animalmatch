package com.ajeffcorrigan.game.animalmatch.tools;

import com.ajeffcorrigan.game.animalmatch.gamesystem.GameCell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by jacorrigan on 11/25/2016.
 */

public class GameLevelManager {

    // Holds the current level.
    private int currentLevel;
    // XML reader
    private XmlReader reader;
    // Root element
    private XmlReader.Element rootEl = null;
    private XmlReader.Element currlvl = null;
    // File Handler
    private FileHandle xmlFile;
    // Tile Size
    public int tileSize = 64;


    public GameLevelManager() {
        // Initialize the XML reader.
        reader = new XmlReader();
        // Initialize the file handler.
        xmlFile = new FileHandle("levels.xml");

        try {
            rootEl = reader.parse(xmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.currentLevel = 0;
    }

    // Getter and setter for current level.
    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public Vector2 getLevelSize() {
        this.currlvl = rootEl.getChild(currentLevel);
        return new Vector2(currlvl.getInt("x"),currlvl.getInt("y"));
    }

    public void updateGameCell(GameCell gc) {
        for(XmlReader.Element cell : this.currlvl.getChildrenByName("cell")) {
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
