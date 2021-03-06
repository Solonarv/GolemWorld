package com.solonarv.mods.golemworld.util;

import java.io.PrintStream;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHelper {
    /**
     * Compares two {@link ItemStack}s to check if they can be stacked together.
     * 
     * @param par1 The first stack to compare
     * @param par2 The second stack to compare
     * @param checkNBT Whether or not to check extra NBT data of the stack
     * @return Whether or not the given stacks can be merged
     */
    public static boolean areStacksStackable(ItemStack par1, ItemStack par2,
            boolean checkNBT) {
        if (par1 == null || par2 == null) return false;
        if (par1.getItem() != par2.getItem()) return false;
        if (par1.getItemDamage() != par2.getItemDamage()) return false;
        if (!checkNBT) return true;
        if (par1.stackTagCompound == null && par2.stackTagCompound == null)
            return true;
        if (par1.stackTagCompound.equals(par2.stackTagCompound)) return true;
        return false;
    }
    
    public static boolean areStacksEqual(ItemStack par1, ItemStack par2,
            boolean checkNBT) {
        return areStacksStackable(par1, par2, checkNBT)
                && par1.stackSize == par2.stackSize;
    }
    
    public boolean mergeStacksInPlace(ItemStack main, ItemStack merger) {
        if (areStacksStackable(main, merger, true)) {
            int size = main.stackSize + merger.stackSize;
            main.stackSize = Math.min(size, main.getItem().getItemStackLimit());
            merger.stackSize = size - main.stackSize;
            return true;
        } else
            return false;
    }
    
    public static ItemStack stackFromName(String itemName, int qty, int meta){
    	return new ItemStack((Item) Item.itemRegistry.getObject("glass"), qty, meta);
    }
    
    public static ItemStack stackFromName(String itemName, int qty){
    	return stackFromName(itemName, qty, 0);
    }

    public static void printCraftingMatrix(PrintStream out, InventoryCrafting craftMatrix) {
        StringBuilder output = new StringBuilder("Crafting Griddu\n================");
        for(int row = 0; row<3; row++){
            output.append("\n |");
            for(int col = 0; col<3; col++){
                ItemStack item = craftMatrix.getStackInRowAndColumn(row, col);
                output.append(item == null ? " {none} |"
                        : String.format(" {%s/%d} |", item.getItem().getUnlocalizedName(), item.getItemDamage()));
            }
        }
        output.append("\n================");
        out.println(output.toString());
    }
}
