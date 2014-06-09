package com.solonarv.mods.golemworld.item;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
        for(int row=0; row<3; row++) for(int col=0; col<3; col++){
            ItemStack stack = craftMatrix.getStackInRowAndColumn(row, col);
            if(row==0 && col==1){
                if(stack == null || stack.getItem() != ModItems.golemPlacer || stack.getItemDamage() != 1)
                    return null;
            } else {
                if(stack == null || stack.getItem() instanceof ItemBlock){
                    blocks[row][col]=stack==null?null:new BlockRef(((ItemBlock) stack.getItem()).field_150939_a, stack.getItemDamage() % 16);
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
