package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityStoneGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 110;
        stats.attackDamageMean = 12f;
        stats.attackDamageStdDev = 1f;
        stats.name = "Stone Golem";
        stats.texture = Reference.texture("stone_golem");
        stats.droppedItems(new ItemStack(Block.cobblestone, 2));
    }
    
    public EntityStoneGolem(World world) {
        super(world);
    }
    
}
