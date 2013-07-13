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
    
    /**
     * Parameters of attack damage normal distr
     */
    public float       attackDamageMean, attackDamageStdDev;
    
    public ItemStack[] droppedItems;
    
    public String      texture;
    
    /**
     * Utility method to fill the droppeItems array
     * 
     * @param itemStacks the dropped items
     */
    public void droppedItems(ItemStack... itemStacks) {
        droppedItems = itemStacks;
    }
}
