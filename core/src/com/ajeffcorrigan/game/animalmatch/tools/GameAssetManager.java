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
    private int sheetCols;
    // Current Sheet Name
    private String sheetName;
    // First gid value, if lower no associated graphics
    private int firstGid;

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
        this.setSheetCols(rootElement.getIntAttribute("columns"));
        this.setFirstGid(rootElement.getIntAttribute("firstgid"));

        // Load texture file and name it.
        jAssets.loadTextureAs(this.sheetName, rootElement.getChildByName("image").getAttribute("source"));
    }

    public void checkTextureRegion(String tR, String sN) {
        if(this.sheetName.equalsIgnoreCase(sN)) {
            if(Integer.valueOf(tR) >= this.firstGid) {
                if(!jAssets.textureRegionExists(tR)) {
                    // find which row the texture is on
                    int rowLoc = Integer.valueOf(tR) / this.sheetCols;
                    // find column position based on row
                    int colLoc = (Integer.valueOf(tR) - (this.sheetCols * rowLoc)) - 1;
                    // Create texture region based on file.
                    jAssets.createTextureRegion(tR,this.sheetName,(int)(colLoc * this.tileSize.x),(int)(rowLoc * this.tileSize.y),(int)this.tileSize.x,(int)this.tileSize.y);
                }
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

    // Get the number of columns per row.
    public int getSheetCols() { return sheetCols; }
    // Set the number of columns per row.
    private void setSheetCols(int sheetCols) { this.sheetCols = sheetCols; }

    public int getFirstGid() { return firstGid; }
    /**
     * @param firstGid
     */
    private void setFirstGid(int firstGid) { this.firstGid = firstGid; }
}
