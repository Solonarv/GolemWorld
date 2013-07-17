package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;

public class EntityGoldGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 70;
        stats.attackDamageMean = 16;
        stats.attackDamageStdDev = 1;
        stats.name = "Gold Golem";
        stats.texture = "gold_golem";
        stats.droppedItems(new ItemStack(Item.ingotGold, 2));
    }
    
    public EntityGoldGolem(World world) {
        super(world);
    }
}
