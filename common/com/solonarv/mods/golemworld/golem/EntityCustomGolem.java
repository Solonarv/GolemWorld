package com.solonarv.mods.golemworld.golem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityCustomGolem extends EntityIronGolem {
    
    public abstract GolemStats stats();
    
    // Make private attackTimer from superclass visible
    protected int attackTimer;
    
    public EntityCustomGolem(World world) {
        super(world);
        // func_94058_c(stats().name);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        attackTimer = 10;
        worldObj.setEntityState(this, (byte) 4);
        int dmg = getAttackStrength();
        boolean flag = par1Entity.attackEntityFrom(
                DamageSource.causeMobDamage(this), dmg);
        
        if (EntityPlayer.class.isAssignableFrom(par1Entity.getClass())) {
            EntityPlayer p = (EntityPlayer) par1Entity;
            p.addChatMessage("Attacked by " + getName() + " for "
                    + String.valueOf(dmg) + " damage: "
                    + (flag ? "success" : "fail"));
        }
        
        if (flag) {
            par1Entity.motionY += 0.4000000059604645D;
        }
        
        playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }
    
    public final int getAttackStrength() {
        return MathHelper.floor_double(stats().attackDamageMean
                + rand.nextGaussian() * stats().attackDamageStdDev);
    };
    
    public static boolean isSmart() {
        return false;
    }
    
    public final String getName() {
        return stats() != null ? stats().name : "stats not initialized!";
    }
    
    @Override
    public final void dropFewItems(boolean recentlyHit, int lootingLevel) {
        for (ItemStack is : stats().droppedItems) {
            entityDropItem(is, 0F);
        }
    }
    
}
