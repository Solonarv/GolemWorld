package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityDirtGolem extends EntitySimpleGolem {
    
    public static final GolemStats stats = new GolemStats();
    static {
        // Initialize stats
        stats.maxHealth = 10;
        stats.attackDamageMean = 2;
        stats.attackDamageStdDev = 1;
        stats.name = "Dirt Golem";
        stats.texture = Reference.mobTexture("dirt_golem");
        stats.droppedItems(new ItemStack(Block.dirt, 3));
    }
    
    public EntityDirtGolem(World world) {
        super(world);
    }
    
}
