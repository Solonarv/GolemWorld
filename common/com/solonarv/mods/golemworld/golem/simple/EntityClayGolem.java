package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;

public class EntityClayGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 110;
        stats.attackDamageMean = 8f;
        stats.attackDamageStdDev = 2.3f;
        stats.name = "Clay Golem";
        stats.texture = "clay_golem";
        stats.droppedItems(new ItemStack(Item.clay, 5));
    }
    
    public EntityClayGolem(World world) {
        super(world);
    }
}
