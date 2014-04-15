package com.solonarv.mods.golemworld.golem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


/**
 * The inventory of a golem (this is also a base class for other smart golems'
 * inventories. As smart golems are currently NYI, this is subject to heavy
 * change.
 * 
 * @author Solonarv
 * 
 */
public abstract class InventoryGolem extends EntityCustomGolem implements
        IInventory {
    public InventoryGolem(World world) {
        super(world);
    }
    
    /**
     * The inventory per se
     */
    protected ItemStack[] invBuffer = new ItemStack[18];
    /**
     * The golem's current 'working' slot index.
     */
    protected int   activeSlot;
	
    @Override
	public int getSizeInventory() {
		return invBuffer.length;
	}
	
    @Override
	public ItemStack getStackInSlot(int slot) {
		return invBuffer[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		// Copy-paste from TileEntityChest
		if (this.invBuffer[slot] != null){
            ItemStack itemstack;

            if (this.invBuffer[slot].stackSize <= amount) {
                itemstack = this.invBuffer[slot];
                this.invBuffer[slot] = null;
                return itemstack;
            } else {
                itemstack = this.invBuffer[slot].splitStack(amount);

                if (this.invBuffer[slot].stackSize == 0) {
                    this.invBuffer[slot] = null;
                }
                return itemstack;
            }
        } else {
            return null;
        }
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.invBuffer[slot]!=null){
			ItemStack stack=this.invBuffer[slot];
			this.invBuffer[slot]=null;
			return stack;
		}
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.invBuffer[slot]=stack;
		if(stack!=null && stack.stackSize > this.getInventoryStackLimit()){
			stack.stackSize=this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName() {
		return this.getName();
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void markDirty() {}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(this.posX, this.posY, this.posZ) <= 64.0D;
	}
	
	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}
}
