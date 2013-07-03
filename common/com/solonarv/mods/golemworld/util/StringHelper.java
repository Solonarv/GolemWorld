package com.solonarv.mods.golemworld.util;

import net.minecraft.item.ItemStack;

public class StringHelper {
    public static String stringify(ItemStack is) {
        return is != null ? String.format(
                "ItemStack{ ItemID=%d, stackSize=%d, damage=%d}", is.itemID,
                is.stackSize, is.getItemDamage()) : "ItemStack:null";
    }

    public static String stringify(ItemStack[] is) {
        if (is == null) {
            return "ItemStack[]: null";
        }
        String r = "";
        for (ItemStack i : is) {
            r = r + "\n" + stringify(i);
        }
        return "ItemStack[]{" + r + "}";
    }
}
