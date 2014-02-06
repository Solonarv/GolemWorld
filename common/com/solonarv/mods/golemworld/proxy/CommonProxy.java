package com.solonarv.mods.golemworld.proxy;

import net.minecraft.world.World;

/**
 * A proxy to handle client-specific stuff. Client-only methods will be blank
 * here and overridden in {@link ClientProxy}. This is currently empty.
 * 
 * @author Solonarv
 * 
 */
public class CommonProxy {
    public void registerRenderer() {
    }

    public void tellPlayer(String string) {}

    public void spawnLapisTrailFX(World world, double x, double y, double z, int lifetime) {}
}
