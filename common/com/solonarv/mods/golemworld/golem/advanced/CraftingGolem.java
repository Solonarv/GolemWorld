package com.solonarv.mods.golemworld.golem.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.solonarv.mods.golemworld.golem.InventoryGolem;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CraftingGolem extends InventoryGolem {
    public CraftingGolem(World world) {
        super(world);
    }
    
    public ItemStack[]      recipe;
    private List<ItemStack> collatedRecipe;
    
    public boolean canCraft() {
        for (ItemStack stack : getCollatedStacks()) {
            ;;
        }
        return false;
    }
    
    public List<ItemStack> collateRecipe() {
        List<ItemStack> temp = new ArrayList<ItemStack>();
        List<ItemStack> uncollatedStacks = Arrays.asList(recipe);
        for (ItemStack is : uncollatedStacks)
            if (is != null) {
                if (temp.isEmpty()) {
                    temp.add(is);
                } else {
                    for (ItemStack tis : temp) {
                        if (tis.itemID == is.itemID
                                && tis.getItemDamage() == is.getItemDamage()
                                && ((tis.stackTagCompound == null && is.stackTagCompound == null) || (tis.stackTagCompound != null && is.stackTagCompound != null)
                                        && tis.stackTagCompound
                                                .equals(is.stackTagCompound))) {
                            tis.stackSize++;
                        }
                    }
                }
            }
        return temp;
    }
    
    public List<ItemStack> getCollatedStacks() {
        if (this.collatedRecipe == null) {
            this.collatedRecipe = collateRecipe();
        }
        return this.collatedRecipe;
    }
}
