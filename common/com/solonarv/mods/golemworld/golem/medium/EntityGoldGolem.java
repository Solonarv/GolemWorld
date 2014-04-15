package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityGoldGolem extends EntityCustomGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 40;
        stats.attackDamageMean = 20; // Actually max here
        stats.attackDamageStdDev = .15f; // Actually this * theGolem.atkStrength 
        stats.name = "Gold Golem";
        stats.texture = Reference.mobTexture("gold_golem");
        stats.droppedItems(new ItemStack(Items.gold_ingot, 2));
    }
    
    public EntityGoldGolem(World world) {
        super(world);
    }
    
    private float atkStrength=20;
    private int atkStrengthRegenStartCD=0;
    public final int atkStrengthRegenStartMaxCD=100, // 5s after end of combat, then regen starts
            atkStrengthRegenTickMax=20;              // Regen 1atk/sec then
    private int atkStrengthRegenTick=0;
    
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if(this.atkStrengthRegenStartCD>0){
            this.atkStrengthRegenStartCD--;
        }
        if(this.atkStrengthRegenTick>0){
            this.atkStrengthRegenTick--;
        }
        if(this.atkStrengthRegenStartCD==0 && this.atkStrengthRegenTick==0 && this.atkStrength<this.stats().attackDamageMean){
            this.atkStrength++;
            this.atkStrengthRegenTick=this.atkStrengthRegenTickMax;
        }
    }
    
    @Override
    public float getAttackStrength(Entity target, boolean used){
        float val=(float) (this.atkStrength*(1+this.rand.nextGaussian()*this.stats().attackDamageStdDev));
        if(used){
            if(this.atkStrength>10){
                this.atkStrength-=2;
            }else if(this.atkStrength>2){
                this.atkStrength--;
            }
            this.atkStrengthRegenStartCD=this.atkStrengthRegenStartMaxCD;
            this.atkStrengthRegenTick=0;
        }
        return val;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float dmg){
        if(super.attackEntityFrom(source, dmg)){
            this.atkStrengthRegenStartCD=this.atkStrengthRegenStartMaxCD;
            this.atkStrengthRegenTick=0;
            return true;
        }
        return false;
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt){
        super.writeEntityToNBT(nbt);
        nbt.setFloat("CurrentAttackStrength", this.atkStrength);
        nbt.setInteger("AttackStrengthRegenStartCD", this.atkStrengthRegenStartCD);
        nbt.setInteger("AttackStrengthRegenTick", this.atkStrengthRegenTick);
    }
    
     @Override
     public void readEntityFromNBT(NBTTagCompound nbt){
         super.readEntityFromNBT(nbt);
         this.atkStrength=nbt.getFloat("CurrentAttackStrength");
         this.atkStrengthRegenStartCD=nbt.getInteger("AttackStrengthRegenStartCD");
         this.atkStrengthRegenTick=nbt.getInteger("AttackStrengthRegenTick");
     }
}
