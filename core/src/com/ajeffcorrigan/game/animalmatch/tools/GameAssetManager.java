package com.ajeffcorrigan.game.animalmatch.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

/**
 * Created by admin on 11/24/2016.
 */

public class GameAssetManager {

    public int xSize;
    public int ySize;
    // XML reader object
    private XmlReader reader;
    // File Handler for xml file.
    private FileHandle xmlFile;
    // Root Element object
    private XmlReader.Element rootElement = null;

    public GameAssetManager() {

        // Initialize the XML reader.
        reader = new XmlReader();
        // Initialize the file handler.
        xmlFile = new FileHandle("textures.xml");

        // Parse file and load root element.
        try { rootElement = reader.parse(xmlFile); } catch (IOException e) { e.printStackTrace(); }

        Gdx.app.debug(this.getClass().getSimpleName(), "Creating texture and texture region references.");
        for(XmlReader.Element textureElement : rootElement.getChildrenByName("texture")) {
            String sheetName = new String(textureElement.getAttribute("sheet"));
            this.xSize = textureElement.getIntAttribute("tileSizeX");
            this.ySize = textureElement.getIntAttribute("tileSizeY");

            // Load texture file and name it.
            jAssets.loadTextureAs(sheetName, textureElement.getAttribute("file"));
            for(XmlReader.Element regionElement : textureElement.getChildrenByName("region")) {
                int xLocation = this.xSize * regionElement.getIntAttribute("xLoc");
                int yLocation = this.xSize * regionElement.getIntAttribute("yLoc");
                //Create texture region
                jAssets.createTextureRegion(regionElement.getAttribute("name"), sheetName, xLocation, yLocation, xSize, ySize);
            }
        }
    }
}
