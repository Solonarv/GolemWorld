package com.solonarv.mods.golemworld.lib;

/**
 * Golem-World
 * 
 * Reference
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Reference {
    public final static String MOD_NAME = "Golem World";
    public final static String MOD_ID = "golemworld";
    public final static String CHANNEL_NAME = MOD_ID;
    public final static String VERSION = "@VERSION@";
    public final static int MAX_TPS = 20;
    public final static int ITEMID_SHIFT = 256;

    public final static String CONFIG_FILE = "";

    public final static String CLIENT_PROXY = "com.solonarv.mods.golemworld.proxy.ClientProxy";

    public final static String COMMON_PROXY = "com.solonarv.mods.golemworld.proxy.CommonProxy";

    public final static String mobTexture(String textureName) {
        return "mods/" + MOD_ID + "/textures/mob/" + textureName + ".png";
    }

    public final static String itemTexture(String textureName) {
        return MOD_ID + ":" + textureName;
    }
}
