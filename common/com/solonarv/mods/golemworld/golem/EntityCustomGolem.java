package com.solonarv.mods.golemworld.golem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityCustomGolem extends EntityIronGolem {
    
    private static GolemStats stats;
    private GolemStats        actualStats = null;
    
    // Make private attackTimer from superclass visible
    protected int             attackTimer;
    
    public EntityCustomGolem(World world) {
        super(world);
        // func_94058_c(this.getStats().name);
    }
    
    private void updateStats() {
        try {
            this.actualStats = (GolemStats) this.getClass().getField("stats")
                    .get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return MathHelper.floor_double(this.getStats().attackDamageMean
                + rand.nextGaussian() * this.getStats().attackDamageStdDev);
    };
    
    private GolemStats getStats() {
        if (this.actualStats == null) this.updateStats();
        return this.actualStats;
    }
    
    public static boolean isSmart() {
        return false;
    }
    
    public final String getName() {
        return stats != null ? this.getStats().name : "stats not initialized!";
    }
    
    /**
     * Updates the mob's attributes, probably gets called on init.
     */
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a)
                .func_111128_a(this.getStats().maxHealth);
    }
    
    @Override
    public final void dropFewItems(boolean recentlyHit, int lootingLevel) {
        for (ItemStack is : this.getStats().droppedItems) {
            entityDropItem(is, 0F);
        }
    }
    
}
