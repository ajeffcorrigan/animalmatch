package com.ajeffcorrigan.game.animalmatch.tools;

/**
 * Created by admin on 11/24/2016.
 */

public class GameAssetManager {
    public GameAssetManager() {
        jAssets.loadTextureAs("bgsheet", "RPGpack_sheet.png");
        jAssets.createTextureRegion("dirt", "bgsheet", 576, 864, 70, 70);
    }
}
