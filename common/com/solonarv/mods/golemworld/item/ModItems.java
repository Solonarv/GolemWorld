package com.solonarv.mods.golemworld.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.solonarv.mods.golemworld.lib.ItemIDs;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Golem-World
 * 
 * ModItems
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModItems {
    
    public static Item paperAwaken = new ItemPaperOfAwakening(
                                           ItemIDs.PAPER_OF_AWAKENING)
                                           .setMaxStackSize(64)
                                           .setCreativeTab(CreativeTabs.tabMisc)
                                           .setUnlocalizedName(
                                                   "paperOfAwakening");
    
    public static void registerItems() {
        LanguageRegistry.addName(paperAwaken, "Paper of Awakening");
        
        GameRegistry.addRecipe(new ItemStack(paperAwaken), new Object[] {
                "grg", "rpr", "grg", 'g', Item.glowstone, 'r', Item.redstone,
                'p', Item.paper });
        GameRegistry.addRecipe(new ItemStack(paperAwaken), new Object[] {
                "grg", "rpr", "grg", 'g', Item.redstone, 'r', Item.glowstone,
                'p', Item.paper });
        
    }
}
