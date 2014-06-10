package com.solonarv.mods.golemworld.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemRegistration;
import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.util.BlockRef;

public class GolemPlacerRecipes implements IRecipe {
    
    private static GolemPlacerRecipes instance = new GolemPlacerRecipes();
    private GolemPlacerRecipes(){};
    
    public GolemRegistration findMatchingGolem(InventoryCrafting craftMatrix){
        if(craftMatrix.getSizeInventory()!=9) return null;
        BlockRef[][] blocks = new BlockRef[3][3];
        for(int row=0; row<3; row++) for(int col=0; col<3; col++){ // Vanilla code is weird.
            ItemStack stack = craftMatrix.getStackInRowAndColumn(col, row); // CHEATERS! LIARS! srg pls
            if(col==1 && row==0){
                if(stack == null || stack.getItem() != ModItems.golemPlacer || stack.getItemDamage() != 1)
                    return null;
            } else {
                if(stack == null || stack.getItem() instanceof ItemBlock){
                    blocks[row][col]=stack==null?null:new BlockRef(((ItemBlock) stack.getItem()).field_150939_a, stack.getItemDamage() % 16);
                } else if (stack.getItem() == Items.fire_charge){
                    blocks[row][col] = new BlockRef(Blocks.fire, 0); // Special case for Netherrack golem, will add more
                } else return null;
            }
        }
        return GolemRegistry.findGolemReg(blocks[0][0], blocks[0][2],
                blocks[1][1], blocks[1][0], blocks[1][2],
                blocks[2][1], blocks[2][0], blocks[2][2]);
    }
    
    @Override
    public boolean matches(InventoryCrafting craftMatrix, World world) {
        return findMatchingGolem(craftMatrix)!=null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craftMatrix) {
        GolemRegistration golemReg = findMatchingGolem(craftMatrix);
        if(golemReg==null) return null;
        else {
            ItemStack result = getRecipeOutput();
            if(!result.hasTagCompound())
                result.stackTagCompound = new NBTTagCompound(); 
            result.stackTagCompound.setString("GolemName", golemReg.getGolemName());
            return result;
        }
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.golemPlacer, 1, 2);
    }

    public static GolemPlacerRecipes getInstance() {
        return instance;
    }

}
