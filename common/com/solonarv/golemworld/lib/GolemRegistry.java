package com.solonarv.golemworld.lib;

import net.minecraft.world.World;

import com.solonarv.golemworld.GolemWorld;
import com.solonarv.golemworld.entity.golem.EntityDirtGolem;
import com.solonarv.golemworld.entity.golem.EntityGolem;
import com.solonarv.golemworld.recipes.GolemRecipe;
import com.solonarv.golemworld.recipes.RecipeDirtGolem;

import cpw.mods.fml.common.registry.EntityRegistry;

public class GolemRegistry {
	
	private static GolemRecipe[] recipes=new GolemRecipe[256];
	private static int nextId=0;
	
	public static RecipeDirtGolem dirtGolem=(RecipeDirtGolem) (EntityDirtGolem.recipe=new RecipeDirtGolem());
	
	@SuppressWarnings("static-access")
    public static <T extends GolemRecipe> T addRecipe(T recipe){
	    if(recipe.getClass()==GolemRecipe.class) return null;
	    recipes[nextId]=recipe;
	    //EntityRegistry.registerModEntity(recipe.golemClass(), recipe.golemName(), nextId, GolemWorld.instance, 40, 1, true);
	    
	    nextId++;
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
