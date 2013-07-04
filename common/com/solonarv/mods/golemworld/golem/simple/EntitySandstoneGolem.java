package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntitySandstoneGolem extends EntitySimpleGolem {

    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 60;
        stats.attackDamageMean = 10;
        stats.attackDamageStdDev = 1;
        stats.name = "Sandstone Golem";
        stats.texture = Reference.mobTexture("sandstone_golem");
        stats.droppedItems(new ItemStack(Block.sand, 4));
    }

    @Override
    public int getMaxHealth() {
        return stats.maxHealth;
    }

    public EntitySandstoneGolem(World world) {
        super(world);
    }

}