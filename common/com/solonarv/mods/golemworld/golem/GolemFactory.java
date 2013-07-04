package com.solonarv.mods.golemworld.golem;

import java.lang.reflect.Method;

import net.minecraft.world.World;

public class GolemFactory {

    protected Class<? extends EntityCustomGolem> golemClass;
    public final boolean smart;

    public GolemFactory(Class<? extends EntityCustomGolem> golemClass) {
        this.golemClass = golemClass;
        boolean tmp = false;
        try {
            Method isSmart = golemClass.getMethod("isSmart", new Class[] {});
            if (isSmart != null) {
                tmp = (Boolean) isSmart.invoke(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            smart = tmp;
        }
    }

    public EntityCustomGolem make(World world, int x, int y, int z) {
        EntityCustomGolem g = null;
        try {
            g = golemClass.getConstructor(World.class).newInstance(world);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (g != null) {
            g.rotationYawHead = g.rotationYaw;
            g.renderYawOffset = g.rotationYaw;
            g.initCreature();
            g.setPosition(x, y, z);
            world.spawnEntityInWorld(g);
        }
        return g;
    }

    public boolean isSmart() {
        return false;
    }
}
