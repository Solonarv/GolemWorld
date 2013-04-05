package com.solonarv.golemworld.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import com.solonarv.golemworld.lib.ItemIDs;
import com.solonarv.golemworld.item.ItemPaperOfAwakening;

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
	
	public static Item paperAwaken=new ItemPaperOfAwakening(ItemIDs.PAPER_OF_AWAKENING)
	    .setMaxStackSize(64)
		.setCreativeTab(CreativeTabs.tabMisc)
		.setUnlocalizedName("paperOfAwakening");
	
	public static void registerItems(){
	    LanguageRegistry.addName(paperAwaken, "Paper of Awakening");
	    
	    GameRegistry.addShapedRecipe(new ItemStack(paperAwaken), new Object[] {
	        "grg",
	        "rpr",
	        "grg", "g",Item.lightStoneDust, "r",Item.redstone, "p",Item.paper});
	    GameRegistry.addShapedRecipe(new ItemStack(paperAwaken), new Object[] {
            "grg",
            "rpr",
            "grg", "g",Item.redstone, "r",Item.lightStoneDust, "p",Item.paper});
	}
}
