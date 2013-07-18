package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityGlassGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 20;
        stats.attackDamageMean = 10f;
        stats.attackDamageStdDev = 3.5f;
        stats.name = "Glass Golem";
        stats.texture = Reference.mobResource("glass_golem");
        stats.droppedItems(new ItemStack(Block.glass, 3));
    }
    
    public EntityGlassGolem(World world) {
        super(world);
    }
}
