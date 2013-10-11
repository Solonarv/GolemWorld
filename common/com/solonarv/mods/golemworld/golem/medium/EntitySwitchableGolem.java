package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntitySwitchableGolem extends EntityCustomGolem {
    
    public static final GolemStats stats = new GolemStats();
    
    static {
        stats.maxHealth = 20;
        stats.attackDamageMean = 5f;
        stats.attackDamageStdDev = 1f;
        stats.name = "Switchable Golem";
        stats.texture = Reference.mobTexture("redstone_golem");
        stats.droppedItems(new ItemStack(Block.pistonBase), new ItemStack(Block.lever));
        stats.villageSpawnable = false;
    }
    
    private boolean active; 
    
    public EntitySwitchableGolem(World world) {
        super(world);
    }
    
    public void toggle(){
        this.active = !this.active;
        if(this.active){
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(.25D);
        }else{
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0D);
        }
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt){
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("active", this.active);
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt){
        super.readEntityFromNBT(nbt);
        this.active = !nbt.getBoolean("active");
        this.toggle();
    }
    
    @Override
    public void collideWithEntity(Entity e){
        if(e instanceof EntityRedstoneGolem){
            this.toggle();
        }
    }
    
    @Override
    public boolean interact(EntityPlayer player){
        this.toggle();
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource dmg, float amount){
        return this.active && super.attackEntityFrom(dmg, amount);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity tar){
        return this.active && super.attackEntityAsMob(tar);
    }
}
