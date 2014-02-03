package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;

public class EntityIronGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        // Initialize stats
        stats.maxHealth = 100;
        stats.attackDamageMean = 14;
        stats.attackDamageStdDev = 3;
        stats.name = "Iron Golem";
        stats.texture = new ResourceLocation("textures/entity/iron_golem.png");
        stats.droppedItems(new ItemStack(Item.ingotIron, 5), new ItemStack(
                Block.plantRed, 1));
    }
    
    public EntityIronGolem(World world) {
        super(world);
    }
}
