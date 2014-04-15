package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityHardenedClayGolem extends EntityCustomGolem{
    
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 35;
        stats.attackDamageMean = 8;
        stats.attackDamageStdDev = 4;
        stats.name = "Hardened Clay Golem";
        stats.texture = Reference.mobTexture("hardened_clay_golem");
        stats.droppedItems(new ItemStack(Blocks.hardened_clay, 3));
    }
    
    public EntityHardenedClayGolem(World world) {
        super(world);
    }
    
}
