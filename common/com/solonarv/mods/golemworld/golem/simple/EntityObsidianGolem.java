package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityObsidianGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 150;
        stats.attackDamageMean = 10f;
        stats.attackDamageStdDev = .3f;
        stats.name = "Obsidian Golem";
        stats.texture = Reference.texture("obsidian_golem");
        stats.droppedItems(new ItemStack(Block.obsidian, 2));
    }
    
    public EntityObsidianGolem(World world) {
        super(world);
    }
}
