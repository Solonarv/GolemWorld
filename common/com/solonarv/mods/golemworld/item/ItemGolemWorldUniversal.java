package com.solonarv.mods.golemworld.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.lib.Reference;

/**
 * Golem-World
 * 
 * ItemPaperOfAwakening
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemGolemWorldUniversal extends Item {
    
    public ItemGolemWorldUniversal(int id) {
        super(id - Reference.ITEMID_SHIFT);
        
    }
    
    protected Icon[] icons;
    
    @Override
    public void registerIcons(IconRegister ir) {
        icons = new Icon[] {
                ir.registerIcon(Reference.texture("paperOfAwakening")),
                Item.fireballCharge.getIconFromDamage(0) };
    }
    
    @Override
    public Icon getIconFromDamage(int meta){
        return this.icons[meta];
    }
    
    public ItemStack stack(int amount, int meta){
        return new ItemStack(this,amount,meta);
    }
    
    public ItemStack stack(int meta){
        return this.stack(1, meta);
    }
    
    protected boolean onItemUsePaper(ItemStack itemStack,
            EntityPlayer entityPlayer, World world, int x, int y, int z) {
        if (world.isRemote) { return true; }
        if (itemStack.stackSize <= 0) { return false; }
        EntityCustomGolem g = GolemRegistry.trySpawn(world, x, y, z);
        if (g != null) {
            if (entityPlayer == null
                    || !entityPlayer.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }
            if (entityPlayer != null)
                entityPlayer.addChatMessage("Spawned golem: " + g.getName());
        } else if (entityPlayer != null) {
            entityPlayer.addChatMessage("No golem could be spawned.");
        }
        if (g != null && entityPlayer != null) {
            g.setPlayerCreated(true);
        }
        return g != null;
    }
    
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer,
            World world, int x, int y, int z, int sideHit, float hitVecX,
            float hitVecY, float hitVecZ) {
        switch (itemStack.getItemDamage()) {
            case 0:
                return this.onItemUsePaper(itemStack, entityPlayer, world, x,
                        y, z);
        }
        return false;
    }
}
