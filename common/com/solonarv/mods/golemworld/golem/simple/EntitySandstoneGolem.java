package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntitySandstoneGolem extends EntitySimpleGolem {
    
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 30;
        stats.attackDamageMean = 8;
        stats.attackDamageStdDev = 1.5f;
        stats.name = "Sandstone Golem";
        stats.texture = Reference.mobTexture("sandstone_golem");
        stats.droppedItems(new ItemStack(Blocks.sand, 4));
    }
    
    public EntitySandstoneGolem(World world) {
        super(world);
    }
    
}
