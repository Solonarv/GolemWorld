package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityClayGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 50;
        stats.attackDamageMean = 4;
        stats.attackDamageStdDev = 4;
        stats.name = "Clay Golem";
        stats.texture = Reference.mobTexture("clay_golem");
        stats.droppedItems(new ItemStack(Items.clay_ball, 5));
    }
    
    public EntityClayGolem(World world) {
        super(world);
    }
}
