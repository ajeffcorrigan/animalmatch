package com.ajeffcorrigan.game.animalmatch.tools;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jacorrigan on 3/13/2017.
 */

public class SheetManager {
    // asset name
    private String assetName;
    // file name
    private String fileName;
    // dimensions of sprites
    private Vector2 spriteSize;
    // Number of sprites on sheet
    private int numSprites;
    // Number of columns
    private int numCols;
    // Starting identifier
    private int firstId;

    public SheetManager(String name, String file, Vector2 sd, int ns, int fi, int nc) {
        this.assetName = name;
        this.fileName = file;
        this.spriteSize = sd;
        this.numSprites = ns;
        this.firstId = fi;
        this.numCols = nc;
    }

    public String getAssetName() { return assetName; }
    public String getFileName() { return fileName; }
    public Vector2 getSpriteSize() { return spriteSize; }
    public int getNumSprites() { return numSprites; }
    public int getFirstId() { return firstId; }
    public int getLastId() { return firstId + numSprites - 1; }
    // Return a vector of the location of the tile on spritesheet
    public Vector2 getTileLocationById(int i) {
        Vector2 tileLoc = new Vector2(0,0);
        tileLoc.x = (int)((i - firstId) / this.numCols);
        tileLoc.y = (int)((((i - firstId) + 1) - (this.numCols * tileLoc.x)) - 1);
        return tileLoc;
    }
}
