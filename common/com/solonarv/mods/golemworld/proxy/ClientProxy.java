package com.solonarv.mods.golemworld.proxy;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.RenderCustomGolem;

/**
 * Client-specific stuff goes here, overriding blank methods from
 * {@link CommonProxy}. Currently empty.
 * 
 * @author Solonarv
 * 
 */
public class ClientProxy extends CommonProxy {
    private static RenderCustomGolem golemRenderer;
    
    @Override
    public void registerRenderer() {
        RenderManager.instance.entityRenderMap.remove(EntityIronGolem.class);
        golemRenderer = new RenderCustomGolem();
        RenderManager.instance.entityRenderMap.put(EntityCustomGolem.class,
                golemRenderer);
        golemRenderer.setRenderManager(RenderManager.instance);
    }
}
