package com.solonarv.golemworld.entity.golem;

import com.solonarv.golemworld.entity.ai.EntityAIDefendVillage;
import com.solonarv.golemworld.entity.ai.EntityAILookAtVillager;
import com.solonarv.golemworld.recipes.GolemRecipe;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;


public class EntityGolem extends EntityMob {
    
    private int homeCheckTimer = 0;
    Village villageObj = null;
    private int attackTimer;

    public EntityGolem(World par1World)
    {
        super(par1World);
        this.texture = "/mob/villager_golem.png";
        this.setSize(1.4F, 2.9F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.25F, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.22F, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.16F, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, 0.16F));
        this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(6, new EntityAIWander(this, 0.16F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, false, true, IMob.mobSelector));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        if (--this.homeCheckTimer <= 0)
        {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null)
            {
                this.detachHome();
            }
            else
            {
                ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
                this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));
            }
        }

        super.updateAITick();
    }
	
    public Village getVillage()
    {
        return this.villageObj;
    }
    
	public static GolemRecipe recipe;;
	protected static int maxHealth;
	//Attack damage is gaussian-random distributed, floats are ready for 1.6 decimal health
	protected static float avgAttackDmg;
	protected static float atkDmgStdDev;
	
	//Final methods, those are common to _all_ golems
	public final int getMaxHealth(){
		return maxHealth;
	};
	public final int getAttackStrength(){
		return MathHelper.floor_double(avgAttackDmg + this.rand.nextGaussian() * atkDmgStdDev);
	};
	/**
     * Decrements the entity's air supply when underwater
     */
	protected final int decreaseAirSupply(int par1){
        return par1;
    }
	public static boolean isSmart(){
		return false;
	}
}
