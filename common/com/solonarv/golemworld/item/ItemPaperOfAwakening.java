package com.solonarv.golemworld.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.solonarv.golemworld.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister){
	    iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":paperOfAwakening");
	}
	
	public String getTextureFile(){
	    return Reference.ITEM_TEXTURES + "/paperOfAwakening.png";
	}
	
}
