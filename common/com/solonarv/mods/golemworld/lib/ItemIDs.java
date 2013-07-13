package com.solonarv.mods.golemworld.lib;

import com.solonarv.mods.golemworld.GolemWorld;

/**
 * Reference class that hold all the itemIDs that I use (they are loaded from
 * the config)
 * 
 * @author Solonarv
 * 
 */
public class ItemIDs {
    
    public static final int PAPER_OF_AWAKENING = GolemWorld.config
                                                       .get("Item",
                                                               "paperOfAwakening",
                                                               5000).getInt(
                                                               5000);
    
}
