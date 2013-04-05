package com.solonarv.golemworld.recipes;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import com.solonarv.golemworld.entity.golem.EntityDirtGolem;

public class RecipeDirtGolem extends GolemRecipe {
    public RecipeDirtGolem(){
        super(null, new Block[] {Block.dirt,Block.dirt,Block.dirt}, new Block[] {null,Block.grass,null});
    }
    public EntityDirtGolem checkAndSpawnIfValid(int x, int y, int z, World world){
        EntityPos pos=this.checkRecipeInWorld(x, y, z, world);
        if(pos!=null){
            EntityDirtGolem golem=new EntityDirtGolem(world);
            world.spawnEntityInWorld(golem);
            golem.setPosition(pos.posX, pos.posY, pos.posZ);
            golem.setAngles(pos.facing*13.5F, 0);
            return golem;
        }
        return null;
    }
    public RecipeDirtGolem getThis(){return this;}
}
