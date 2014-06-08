package com.solonarv.mods.golemworld.golem.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class DummyContainer extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return false;
    }
    
    @Override
    public void onCraftMatrixChanged(IInventory inventory){}
}
