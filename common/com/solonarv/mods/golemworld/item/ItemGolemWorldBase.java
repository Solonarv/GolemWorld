package com.solonarv.mods.golemworld.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGolemWorldBase extends Item {

	public ItemGolemWorldBase() {
		super();
	}

	public ItemStack stack(int amount, int meta) {
	    return new ItemStack(this,amount,meta);
	}

	public ItemStack stack(int meta) {
	    return this.stack(1, meta);
	}

}