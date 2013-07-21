package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.AIHelper;
import com.solonarv.mods.golemworld.util.EntityGolemFireball;

public class EntityNetherrackGolem extends EntityCustomGolem {
    public EntityNetherrackGolem(World world) {
        super(world);
        AIHelper.clearAITasks(this.tasks, EntityAIMoveTowardsTarget.class);
    }
    
    public static final GolemStats stats                 = new GolemStats();
    static {
        stats.maxHealth = 60;
        stats.attackDamageMean = 10f;
        stats.attackDamageStdDev = 1f;
        stats.name = "Netherrack Golem";
        stats.texture = Reference.mobResource("netherrack_golem");
        stats.droppedItems(new ItemStack(Block.netherrack, 4));
    }
    
    private int fireballCharges       = 64;
    private int fireballRechargeTimer = 60;
    private boolean burning;
    
    @Override
    public void attackEntity(Entity e, float foo) {
        double distanceSq = e.getDistanceSqToEntity(this);
        if (distanceSq <= 4096f && this.fireballCharges > 0) { // 64m
                                                               // range
            double x = e.posX - this.posX;
            double y = e.posY - this.posY;
            double z = e.posZ - this.posZ;
            for (int i = 0; i < 3; i++) {
                EntityGolemFireball egf = new EntityGolemFireball(this.worldObj, this,
                        x + this.rand.nextGaussian() * distanceSq,
                        y + this.rand.nextGaussian() * distanceSq,
                        z + this.rand.nextGaussian() * distanceSq);
                this.worldObj.spawnEntityInWorld(egf);
            }
        } else if (distanceSq <= 8f) {
            this.attackEntityAsMob(e);
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.isWet()){
            this.burning=false;
        }
        if (this.fireballRechargeTimer > 0) {
            this.fireballRechargeTimer--;
        } else if (this.fireballCharges < 64) {
            // Normal distro, I like these!
            this.fireballCharges += MathHelper.clamp_int((int) Math.round(this.rand.nextGaussian() * 2 + 3), 1, 5);
            this.fireballRechargeTimer = this.rand.nextInt(400) + 200;
        }
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("fireballCharges", this.fireballCharges);
        nbt.setInteger("fireballRechargeTimer", this.fireballRechargeTimer);
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.fireballCharges = nbt.getInteger("fireballCharges");
        this.fireballRechargeTimer = nbt.getInteger("fireballRechargeTimer");
    }
    
    protected void dealFireDamage(int i){}
}
