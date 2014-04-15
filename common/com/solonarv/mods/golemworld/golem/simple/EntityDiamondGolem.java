package com.solonarv.mods.golemworld.golem.simple;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityDiamondGolem extends EntitySimpleGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 250;
        stats.attackDamageMean = 20f;
        stats.attackDamageStdDev = 2f;
        stats.name = "Diamond Golem";
        stats.texture = Reference.mobTexture("diamond_golem");
        stats.droppedItems(new ItemStack(Items.diamond, 2));
    }
    
    public EntityDiamondGolem(World world) {
        super(world);
    }
}
