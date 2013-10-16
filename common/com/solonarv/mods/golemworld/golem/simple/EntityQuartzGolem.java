package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityQuartzGolem extends EntityCustomGolem {
    
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 60;
        stats.attackDamageMean = 10f;
        stats.attackDamageStdDev = 1f;
        stats.name = "Quartz Golem";
        stats.texture = Reference.mobTexture("quartz_golem");
        stats.droppedItems(new ItemStack(Item.netherQuartz, 9));
    }
    
    public EntityQuartzGolem(World world) {
        super(world);
    }
    
}
