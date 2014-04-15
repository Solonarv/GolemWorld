package com.solonarv.mods.golemworld.block;

import java.lang.reflect.Field;

import net.minecraft.block.Block;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.util.ReflectionHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    
    public static BlockEnhancedAir enhancedAir = (BlockEnhancedAir) new BlockEnhancedAir().setBlockName("enhancedAir");
    public static BlockPumpkinFixed fixedPumpkin, fixedPumpkinLantern;
    
    public static void init(){
    	GameRegistry.registerBlock(enhancedAir, "enhancedAir");
        fixPumpkin();
    }

    public static void fixPumpkin() {
        // TODO replace vanilla pumpkin with fixed ones
    }
}
