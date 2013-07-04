package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;

public abstract class EntitySimpleGolem extends EntityCustomGolem {

    public EntitySimpleGolem(World world) {
        super(world);
    }

    public static final boolean isSmart() {
        return false;
    }
}
