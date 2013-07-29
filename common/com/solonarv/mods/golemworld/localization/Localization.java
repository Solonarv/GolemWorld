package com.solonarv.mods.golemworld.localization;

import com.solonarv.mods.golemworld.item.ModItems;
import com.solonarv.mods.golemworld.potion.PotionFreeze;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class Localization {
    
    public static void registerNames(){
        LanguageRegistry.instance().addStringLocalization(PotionFreeze.instance.getName(), "Freeze");
        
        // Temporary -- will be changedonce we have more items
        LanguageRegistry.addName(ModItems.golemWorldUniversal, "Paper of Awakening");
    }
    
}
