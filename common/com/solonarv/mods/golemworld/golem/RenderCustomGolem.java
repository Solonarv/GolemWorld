package com.solonarv.mods.golemworld.golem;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCustomGolem extends RenderIronGolem {
    public static Map<String, ResourceLocation> texCache = new HashMap<String, ResourceLocation>();
    
    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.func_110898_a((EntityCustomGolem) par1Entity);
    }
    
    protected ResourceLocation func_110898_a(EntityCustomGolem theGolem) {
        EntityCustomGolem ecg = (EntityCustomGolem) theGolem;
        String texName = ecg.stats().texture;
        if (!texCache.containsKey(texName)) {
            texCache.put(texName, new ResourceLocation("golem:textures/mob/"
                    + texName + ".png"));
        }
        return texCache.get(texName);
    }
}
