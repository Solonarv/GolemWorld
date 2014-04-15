package com.solonarv.mods.golemworld.golem;

import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;

public class RenderCustomGolem extends RenderIronGolem {
    
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getGolemTexture((EntityCustomGolem) par1Entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityIronGolem theGolem) {
        return this.getGolemTexture((EntityCustomGolem) theGolem);
    }
    
    protected ResourceLocation getGolemTexture(EntityCustomGolem theGolem){
        return theGolem.stats().texture;
    }
}
