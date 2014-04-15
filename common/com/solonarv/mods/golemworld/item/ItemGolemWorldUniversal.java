package com.solonarv.mods.golemworld.item;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
    
    protected IIcon[] icons;
    
    @Override
    public void registerIcons(IIconRegister ir) {
        icons = new IIcon[] {
                ir.registerIcon(Reference.texture("paperOfAwakening")),
                Items.fire_charge.getIconFromDamage(0) };
    }
    
    @Override
    public IIcon getIconFromDamage(int meta){
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
            if (entityPlayer != null){
                g.setCreator(entityPlayer.getDisplayName());
            }
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
