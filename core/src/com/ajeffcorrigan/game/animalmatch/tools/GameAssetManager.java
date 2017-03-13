package com.ajeffcorrigan.game.animalmatch.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
    // tile size based on map
    private Vector2 tileSize;
    // array of sheetmanager objects
    private Array<SheetManager> assetSheets;

    public GameAssetManager() {
        // Initialize spritesheet asset manager.
        assetSheets = new Array<SheetManager>();
        // Initialize the XML reader.
        reader = new XmlReader();
        // Initialize the file handler.
        xmlFile = new FileHandle("base_level.xml");
        // Parse file and load root element.
        try { rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }


        Gdx.app.debug(this.getClass().getSimpleName(), "Loading initial assets from base_level.");
        // Set map tile size
        this.tileSize = new Vector2(rootElement.getInt("tilewidth"),rootElement.getInt("tileheight"));
        // Process each tileset.
        for(XmlReader.Element ts : rootElement.getChildrenByNameRecursively("tileset")) {
            assetSheets.add(new SheetManager(ts.get("name"),ts.getChild(0).get("source"), new Vector2(ts.getInt("tilewidth"),ts.getInt("tileheight")),ts.getInt("tilecount"),ts.getInt("firstgid"),ts.getInt("columns")));
            jAssets.loadTextureAs(assetSheets.peek().getAssetName(),assetSheets.peek().getFileName());
        }
    }

    // Returns a sheetManager object based on tile Id
    public SheetManager getSheetByTileId (int id) {
        for(SheetManager sm : assetSheets) { if(sm.getFirstId() <= id && sm.getLastId() >= id) { return sm; } }
        return null;
    }

    // Check if textureRegion entry exists.
    public boolean textureRegionExists(int t) { return jAssets.textureRegionExists(String.valueOf(t)); }
    public boolean textureRegionExists(String t) { return jAssets.textureRegionExists(t); }

    // Add a textureRegion by Id
    public void addTextureRegionById(int Id) {
        SheetManager sm = getSheetByTileId(Id);
        if(sm != null) {
            Vector2 l = sm.getTileLocationById(Id);
            jAssets.createTextureRegion(String.valueOf(Id),sm.getAssetName(),(int)(l.y * sm.getSpriteSize().x),(int)(l.x * sm.getSpriteSize().y),(int)sm.getSpriteSize().x,(int)sm.getSpriteSize().y);
        }
    }

    // Get the tile size in pixels
    public Vector2 getTileSize() { return this.tileSize; }

}
