package com.solonarv.mods.golemworld.lib;

import net.minecraft.util.ResourceLocation;

import com.solonarv.mods.golemworld.item.ItemPaperOfAwakening;

/**
 * GolemWorld
 * 
 * <br>
 * Reference
 * 
 * Holds a few constants used throughout my mod, so I don't need to go through
 * all of my code when something here changes. Also, in the case of numbers, it
 * makes it easier to see what these numbers actually mean; e.g. why is there a
 * random "-256" in {@link ItemPaperOfAwakening}'s constructor?
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Reference {
    public final static String MOD_NAME     = "Golem World";
    public final static String MOD_ID       = "golem";
    public final static String CHANNEL_NAME = MOD_ID;
    public final static String VERSION      = "@VERSION@";
    public final static int    MAX_TPS      = 20;
    public final static int    ITEMID_SHIFT = 256;
    
    public final static String CLIENT_PROXY = "com.solonarv.mods.golemworld.proxy.ClientProxy";
    public final static String COMMON_PROXY = "com.solonarv.mods.golemworld.proxy.CommonProxy";
    
    /**
     * Utility function to easily construct texture paths.
     * 
     * @param texPath the path to the texture
     * @return the texture location in Minecraft's new domain:name format
     */
    public final static String texture(String texPath) {
        return MOD_ID + ":" + texPath;
    }
    
    public final static ResourceLocation mobTexture(String texName) {
        return new ResourceLocation(texture("textures/mob/" + texName + ".png"));
    }

    public final static ResourceLocation guiTexture(String texName) {
        return new ResourceLocation(texture("textures/gui/" + texName + ".png"));
    }
}
