package com.ajeffcorrigan.game.animalmatch.tools;

/**
 * Created by admin on 11/24/2016.
 */

public class GameAssetManager {

    public int xSize = 64;
    public int ySize = 64;

    public GameAssetManager() {
        jAssets.loadTextureAs("bgsheet", "RPGpack_sheet.png");
        jAssets.createTextureRegion("grass", "bgsheet", 64, 64, xSize, ySize);
        jAssets.createTextureRegion("grass2", "bgsheet", 192, 128, xSize, ySize);
        jAssets.createTextureRegion("grass3", "bgsheet", 256, 128, xSize, ySize);
        jAssets.createTextureRegion("box", "bgsheet", 512, 576, xSize, ySize);
        jAssets.createTextureRegion("greenbush", "bgsheet", 0, 576, xSize, ySize);
        jAssets.createTextureRegion("smgreenbush", "bgsheet", 64, 576, xSize, ySize);
        jAssets.createTextureRegion("orangebush", "bgsheet", 128, 576, xSize, ySize);
        jAssets.createTextureRegion("smorangebush", "bgsheet", 192, 576, xSize, ySize);

    }
}
