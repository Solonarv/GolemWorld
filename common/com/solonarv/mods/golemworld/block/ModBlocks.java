package com.solonarv.mods.golemworld.block;

import com.solonarv.mods.golemworld.GolemWorld;

public class ModBlocks {
    public static final int enhancedAirID = GolemWorld.config.getBlock("enhancedAir", 600).getInt();
    
    public static BlockEnhancedAir enhancedAir = new BlockEnhancedAir(enhancedAirID);
    
    public static void init(){}
}
