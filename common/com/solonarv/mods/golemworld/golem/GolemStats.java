package com.solonarv.mods.golemworld.golem;

import net.minecraft.item.ItemStack;

/**
 * GolemStats Holds stats of a golem: maxHealth, name, attackDamageMean/StdDev,
 * droppedItems, texture
 * 
 * @author Solonarv
 * 
 */
public class GolemStats {
    public int         maxHealth;
    public String      name;
    
    public float       attackDamageMean, attackDamageStdDev;
    
    public ItemStack[] droppedItems;
    
    public String      texture;
    
    public void droppedItems(ItemStack... itemStacks) {
        droppedItems = itemStacks;
    }
}
