package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityDiamondGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 80;
        stats.attackDamageMean = 18f;
        stats.attackDamageStdDev = .8f;
        stats.name = "Diamond Golem";
        stats.texture = Reference.mobTexture("diamond_golem");
        stats.droppedItems(new ItemStack(Item.diamond, 2));
    }
    
    public EntityDiamondGolem(World world) {
        super(world);
    }
}
