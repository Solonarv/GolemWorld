package com.solonarv.golemworld.item;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.golemworld.entity.golem.EntityCustomGolem;
import com.solonarv.golemworld.lib.GolemRegistry;
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
	
	@Override
	public void updateIcons(IconRegister iconRegister){
	    iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":16xpaperAwakening");
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, float hitVecX, float hitVecY, float hitVecZ) {
	    EntityCustomGolem g=null;
	    try {
            g=GolemRegistry.checkAllAndSpawn(world, x, y, z);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
	    if(g==null)
	        entityPlayer.addChatMessage("No golem could be spawned.");
	    else   
	        entityPlayer.addChatMessage("Spawned golem: "+g.name);
	    return g!=null;
	}
	
	public String getTextureFile(){
	    return Reference.ITEM_TEXTURES + "/16xpaperAwakening.png";
	}
	
}
