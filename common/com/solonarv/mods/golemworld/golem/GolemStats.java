package com.solonarv.mods.golemworld.golem;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * GolemStats Holds stats of a golem: maxHealth, name, attackDamageMean/StdDev,
 * droppedItems, texture
 * 
 * @author Solonarv
 * 
 */
public class GolemStats {
    public int              maxHealth;
    public String           name;
    
    public double           attackDamageMean, attackDamageStdDev;
    
    public ItemStack[]      droppedItems;
    
    public ResourceLocation texture;
    
    public void droppedItems(ItemStack... itemStacks) {
        droppedItems = itemStacks;
    }
}
