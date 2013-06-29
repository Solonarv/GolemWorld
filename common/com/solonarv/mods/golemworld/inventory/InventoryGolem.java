package com.solonarv.mods.golemworld.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryGolem implements IInventory {
	
	private ItemStack[] invBuffer=new ItemStack[18];
	private int selectedSlot=0;
	
	@Override
	public int getSizeInventory() {
		return this.invBuffer.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.invBuffer[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if ( this.invBuffer[i] == null ){
			return null;
		} else {
			ItemStack out;
			if ( j >= this.invBuffer[i].stackSize ){
				out=this.invBuffer[i];
				this.invBuffer[i]=null;
			} else {
				out=this.invBuffer[i].splitStack(j);
				
				if ( this.invBuffer[i].stackSize == 0 ){
					this.invBuffer[i]=null;
				}
			}
			return out;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.invBuffer[i]=itemstack;
		if( itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize=this.getInventoryStackLimit();
		}
		
	}

	@Override
	public String getInvName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack itemstack) {
        return true;
    }

}
