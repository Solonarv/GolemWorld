package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityEmeraldGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 100;
        stats.attackDamageMean = 16;
        stats.attackDamageStdDev = 0;
        stats.name = "Emerald Golem";
        stats.texture = Reference.mobTexture("emerald_golem");
        stats.droppedItems(new ItemStack(Item.emerald, 2));
    }

    @Override
    public int getMaxHealth() {
        return stats.maxHealth;
    }

    public EntityEmeraldGolem(World world) {
        super(world);
    }
}
