package com.solonarv.mods.golemworld.block;

import com.solonarv.mods.golemworld.GolemWorld;

public class ModBlocks {
    public static final int glowingRSAirID = GolemWorld.config.getBlock("glowingRedstoneAir", 600).getInt();
    
    public static BlockGlowingRedstoneAir glowingRedstoneAir = new BlockGlowingRedstoneAir(glowingRSAirID);
    
    public static void init(){}
}
