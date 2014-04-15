package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityGlassGolem extends EntityCustomGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 10;
        stats.attackDamageMean = 12;
        stats.attackDamageStdDev = 6;
        stats.name = "Glass Golem";
        stats.texture = Reference.mobTexture("glass_golem");
        stats.droppedItems(new ItemStack(Blocks.glass, 3));
    }

    private float lastDamageDealt;
    
    public EntityGlassGolem(World world) {
        super(world);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity target){
        boolean result=super.attackEntityAsMob(target);
        this.attackEntityFrom(DamageSource.generic, (float) (this.lastDamageDealt*.1));
        return result;
    }
    
    @Override
    public float getAttackStrength(Entity target, boolean used){
        float dmg=super.getAttackStrength(target, used);
        if(used){
            this.lastDamageDealt=dmg;
        }
        return dmg;
    }
}
