package com.solonarv.mods.golemworld.util;

import net.minecraft.item.ItemStack;

/**
 * Utility class that holds a few methods to deal with strings, especially
 * converting various things to strings.
 * 
 * @author Solonarv
 * 
 */
public class StringHelper {
    public static String stringify(ItemStack is) {
        return is != null ? String.format(
                "ItemStack{ Item=%s, stackSize=%d, damage=%d}", is.getUnlocalizedName(),
                is.stackSize, is.getItemDamage()) : "ItemStack:null";
    }
    
    public static String stringify(ItemStack[] is) {
        if (is == null) { return "ItemStack[]: null"; }
        String r = "";
        for (ItemStack i : is) {
            r = r + "\n" + stringify(i);
        }
        return "ItemStack[]{" + r + "}";
    }
}
