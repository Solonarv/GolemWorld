package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityStoneGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 50;
        stats.attackDamageMean = 10;
        stats.attackDamageStdDev = 1.5f;
        stats.name = "Stone Golem";
        stats.texture = Reference.mobTexture("stone_golem");
        stats.droppedItems(new ItemStack(Blocks.cobblestone, 2));
    }
    
    public EntityStoneGolem(World world) {
        super(world);
    }
    
}
