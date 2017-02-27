package com.ajeffcorrigan.game.animalmatch.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

/**
 * Created by admin on 11/24/2016.
 */

public class GameAssetManager {

    // XML reader object
    private XmlReader reader;
    // File Handler for xml file.
    private FileHandle xmlFile;
    // Root Element object
    private XmlReader.Element rootElement = null;
    // tile size
    private Vector2 tileSize;
    // number of tiles
    private int numberOfTiles;
    // number of columns
    private Vector2 sheetLayoutCount;
    // Current Sheet Name
    private String sheetName;

    public GameAssetManager() {

        // Initialize the XML reader.
        reader = new XmlReader();
        // Initialize the file handler.
        xmlFile = new FileHandle("spritesheet.xml");
        // Parse file and load root element.
        try { rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }

        Gdx.app.debug(this.getClass().getSimpleName(), "Setting the initial texture asset.");

        // Set the current sheet properties.
        this.setSheetName(rootElement.getAttribute("name"));
        this.setTileSize(new Vector2(rootElement.getIntAttribute("tilewidth"),rootElement.getIntAttribute("tileheight")));
        this.setNumberOfTiles(rootElement.getIntAttribute("tilecount"));
        this.setSheetLayoutCount(rootElement.getIntAttribute("columns"));

        // Load texture file and name it.
        jAssets.loadTextureAs(this.sheetName, rootElement.getChildByName("image").getAttribute("source"));
    }

    public void checkTextureRegion(String tR, String sN) {
        if(this.sheetName.equalsIgnoreCase(sN)) {
            if(!jAssets.textureRegionExists(tR)) {
                Vector2 regLocation = new Vector2(0,0);
                int startRegion = 1;
                for(int y = 0; y <= this.sheetLayoutCount.y; y++) {
                    for(int x = 0; x <= this.sheetLayoutCount.x; x++) {
                        if(startRegion == Integer.valueOf(tR)) {
                            regLocation.set(x,y);
                            break;
                        }
                        startRegion++;
                    }
                    if(startRegion == Integer.valueOf(tR)) { break; }
                }
                jAssets.createTextureRegion(tR,this.sheetName,(int)(regLocation.x * this.tileSize.x),(int)(regLocation.y * this.tileSize.y),(int)this.tileSize.x,(int)this.tileSize.y);
            }
        } else {
            Gdx.app.debug(this.getClass().getSimpleName(), "ERROR - Current sheet does not match.");
        }
    }

    // Get the tile size in pixels
    public Vector2 getTileSize() { return this.tileSize; }
    // Set the tile size
    private void setTileSize(Vector2 ts) { this.tileSize = ts; }

    // Get number of tiles
    public int getNumberOfTiles() { return this.numberOfTiles; }
    // Set the number of tiles
    private void setNumberOfTiles(int t) { this.numberOfTiles = t; }

    // Get the current sheet name
    public String getSheetName() { return sheetName; }
    // Set the current sheetname
    private void setSheetName(String s) { this.sheetName = s; }

    // Get the sheet matrix layout, nubmer of columns and rows.
    public Vector2 getSheetLayoutCount() { return sheetLayoutCount; }
    // Set the sheet matrix
    private void setSheetLayoutCount(int cCount) {
        float rCount = this.numberOfTiles / cCount;
        this.sheetLayoutCount = new Vector2(cCount,rCount);
    }
}
