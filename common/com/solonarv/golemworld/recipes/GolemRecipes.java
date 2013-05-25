package com.solonarv.golemworld.recipes;

import java.util.ArrayList;

import net.minecraft.world.World;

import com.solonarv.golemworld.entity.golem.EntityDirtGolem;
import com.solonarv.golemworld.entity.golem.EntityGolem;

public class GolemRecipes {
	
	private static ArrayList<GolemRecipe> recipes;
	
	public static RecipeDirtGolem dirtGolem=(RecipeDirtGolem) (EntityDirtGolem.recipe=new RecipeDirtGolem());
	
	public static <T extends GolemRecipe> T addRecipe(T recipe){
	    if(recipe.getClass()==GolemRecipe.class) return null;
	    recipes.add(recipe);
	    return recipe;
	}
	
	public EntityGolem checkAllRecipesAndSpawn(int x, int y, int z, World world){
	    for(GolemRecipe r : recipes){
	        EntityGolem g=r.checkAndSpawnIfValid(x,y,z,world);
	        if(g!=null){
	            return g;
	        }
	    }
	    return null;
	}
}
