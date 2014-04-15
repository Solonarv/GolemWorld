package com.solonarv.mods.golemworld.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.RenderCustomGolem;
import com.solonarv.mods.golemworld.golem.util.EntityLapisTrailFX;

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
    
    @Override
    public void tellPlayer(String string) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(string);
    }
    
    @Override
    public void spawnLapisTrailFX(World world, double x, double y, double z, int lifetime){
        EntityFX particle=new EntityLapisTrailFX(world,x,y,z,lifetime);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
