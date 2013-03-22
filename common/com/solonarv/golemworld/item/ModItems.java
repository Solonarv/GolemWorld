package com.solonarv.golemworld.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import com.solonarv.golemworld.lib.Reference;
import com.solonarv.golemworld.item.ItemPaperOfAwakening;

import cpw.mods.fml.common.registry.GameRegistry;


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
	
	public static int idPaperAwaken=500;
	
	public static Item paperAwaken=new ItemPaperOfAwakening(idPaperAwaken)
		.setCreativeTab(CreativeTabs.tabMisc)
		.setUnlocalizedName("paperOfAwakening");
}
