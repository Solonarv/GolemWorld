package com.solonarv.mods.golemworld.item;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemRegistry;

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
	
	public static final String itemName="paperOfAwakening";
	
	@Override
    public boolean onItemUse(ItemStack itemStack,
            EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, float hitVecX,
            float hitVecY, float hitVecZ) {
        if (world.isRemote) { return true; }
        if (itemStack.stackSize <= 0) { return false; }
        EntityCustomGolem g = GolemRegistry.trySpawn(world, x, y, z);
        if (g != null) {
            if (entityPlayer == null
                    || !entityPlayer.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }
            if (entityPlayer != null){
                g.setCreator(entityPlayer.getDisplayName());
                g.setPlayerCreated(true);
            }
        }
        return g != null;
    }
}
