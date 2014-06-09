package com.solonarv.mods.golemworld.util;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;

public class WorldHelper {
    
    /**
     * Safely get a usable IInventory instance.
     * @param worldObj the World to look in
     * @param x The x coordinate to look at
     * @param y The y coordinate to look at
     * @param z The z coordinate to look at
     * @return The TileEntity at (x, y, z) in worldObj cast to an IInventory if possible, otherwise null. 
     */
    public static IInventory getIInventory(World worldObj, int x, int y, int z) {
        TileEntity tile=worldObj.getTileEntity(x, y, z);
        if(tile!=null && tile instanceof IInventory){
            return (IInventory) tile;
        }
        return null;
    }
    
    /**
     * Put as much of stack into targetInv from side as possible and drop the rest from actor
     * @param targetInv The inventory to put items into
     * @param stack the {@link ItemStack} to put into targetInv
     * @param side the side to access targetInv from (for {@link ISidedInventory}s)
     * @param actor the entity to drop items from if they can't all be put into targetInv
     */
    public static void shoveStackOrDrop(IInventory targetInv, ItemStack stack, int side, Entity actor){
        ItemStack leftovers=targetInv!=null ? TileEntityHopper.func_145889_a(targetInv, stack, side): stack;
        if(leftovers!=null){
            actor.entityDropItem(leftovers, 0);
        }
    }

}
