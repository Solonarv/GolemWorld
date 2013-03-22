package com.solonarv.golemworld.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.solonarv.golemworld.lib.Reference;

/**
 * Golem-World
 * 
 * ItemPaperOfAwakening
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemPaperOfAwakening extends Item {
	
	public ItemPaperOfAwakening(int id){
		super(id-Reference.ITEMID_SHIFT);
	
	}
	
}
