package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityHardenedClayGolem extends EntityCustomGolem{
    
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 60;
        stats.attackDamageMean = 10f;
        stats.attackDamageStdDev = 1f;
        stats.name = "Hardened Clay Golem";
        stats.texture = Reference.mobTexture("hardened_clay_golem");
        stats.droppedItems(new ItemStack(Block.hardenedClay, 3));
    }
    
    public EntityHardenedClayGolem(World world) {
        super(world);
    }
    
}
