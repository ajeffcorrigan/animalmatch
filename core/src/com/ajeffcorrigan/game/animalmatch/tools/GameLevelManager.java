package com.ajeffcorrigan.game.animalmatch.tools;

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
    // File Handler
    private FileHandle xmlFile;

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
        XmlReader.Element lvl = rootEl.getChild(currentLevel);
        return new Vector2(lvl.getInt("x"),lvl.getInt("y"));
    }

}
