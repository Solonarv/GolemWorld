package com.solonarv.golemworld.item;

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
	
	//@Override
	public void updateIcons(IconRegister iconRegister){
	    iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":16xpaperAwakening");
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, float hitVecX, float hitVecY, float hitVecZ) {
	    if(world.isRemote)
	        return true;
	    if(itemStack.stackSize<=0)
	        return false;
	    EntityCustomGolem g=spawnAGolem(world,x,y,z);
	    if(g!=null){
	        if(!entityPlayer.capabilities.isCreativeMode)
                itemStack.stackSize--;
            entityPlayer.addChatMessage("Spawned golem: "+g.getName());
	    }else{
	        entityPlayer.addChatMessage("No golem could be spawned.");
	    }
	    return g!=null;
	}
	
	public static EntityCustomGolem spawnAGolem(World world, int x, int y, int z) {
	    EntityCustomGolem golem=null;
	    try{
            golem=GolemRegistry.checkAllAndSpawn(world, x, y, z);
        } catch(Exception e){
            e.printStackTrace();
        }
	    if(golem!=null){
	        golem.rotationYawHead=golem.rotationYaw;
	        golem.renderYawOffset=golem.rotationYaw;
	        golem.initCreature();
	        world.spawnEntityInWorld(golem);
	    }
        return golem;
    }

    public String getTextureFile(){
	    return Reference.ITEM_TEXTURES + "/16xpaperAwakening.png";
	}
	
}
