package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityObsidianGolem extends EntityCustomGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 300;
        stats.attackDamageMean = 8;
        stats.attackDamageStdDev = .5f;
        stats.name = "Obsidian Golem";
        stats.texture = Reference.mobTexture("obsidian_golem");
        stats.droppedItems(new ItemStack(Blocks.obsidian, 2));
    }
    
    public EntityObsidianGolem(World world) {
        super(world);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float dmg){
        // Immune to explosions
        return source.isExplosion() ? false : super.attackEntityFrom(source, dmg);
    }
}
