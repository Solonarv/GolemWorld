package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityQuartzGolem extends EntityCustomGolem {
    
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 100;
        stats.attackDamageMean = 14;
        stats.attackDamageStdDev = 2;
        stats.name = "Quartz Golem";
        stats.texture = Reference.mobTexture("quartz_golem");
        stats.droppedItems(new ItemStack(Items.quartz, 5));
    }

    private int charging=0, chargeCooldown=0;
    
    public EntityQuartzGolem(World world) {
        super(world);
    }
    
    @Override
    public void setAttackTarget(EntityLivingBase target){
        super.setAttackTarget(target);
        this.charge();
    }
    
    @Override
    public float getAttackStrength(Entity target, boolean used){
        return super.getAttackStrength(target, used) * (this.charging > 0 && target == this.getAttackTarget() ? 1.5f: 1);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity target){
        if(super.attackEntityAsMob(target)){
            this.setCharging(0);
            return true;
        } else return false;
    }
    
    public void charge(){
        this.setCharging(200);
    }
    
    public void setCharging(int time){
        if(time == 0){
            this.setChargingForced(0);
            return;
        }
        if(this.chargeCooldown <=0){
            this.chargeCooldown = 400;
            this.setChargingForced(time);
        }
    }
    
    public void setChargingForced(int time) {
        if(time > this.charging){
            this.charging = time;
            this.setSprinting(true);
        }
        if(time == 0){
            this.charging = time;
            this.setSprinting(false);
        }
    }
    
}
