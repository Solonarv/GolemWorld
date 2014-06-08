package com.solonarv.mods.golemworld.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;

public class WorldHelper {

    public static IInventory getIInventory(World worldObj, int x, int y, int z) {
        TileEntity tile=worldObj.getTileEntity(x, y, z);
        if(tile!=null && tile instanceof IInventory){
            return (IInventory) tile;
        }
        return null;
    }
    
    public static void shoveStackOrDrop(IInventory targetInv, ItemStack stack, int side, Entity actor){
        ItemStack leftovers=targetInv!=null ? TileEntityHopper.func_145889_a(targetInv, stack, side): stack;
        if(leftovers!=null){
            actor.entityDropItem(leftovers, 0);
        }
    }

}
