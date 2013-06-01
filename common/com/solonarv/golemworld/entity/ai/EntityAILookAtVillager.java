package com.solonarv.golemworld.entity.ai;

import com.solonarv.golemworld.entity.golem.EntityGolem;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAILookAtVillager extends EntityAIBase
{
    private EntityGolem theGolem;
    private EntityVillager theVillager;
    private int lookTime;

    public EntityAILookAtVillager(EntityGolem par1EntityIronGolem)
    {
        this.theGolem = par1EntityIronGolem;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.theGolem.worldObj.isDaytime())
        {
            return false;
        }
        else if (this.theGolem.getRNG().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            this.theVillager = (EntityVillager)this.theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.theGolem.boundingBox.expand(6.0D, 2.0D, 6.0D), this.theGolem);
            return this.theVillager != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.lookTime > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.lookTime = 400;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theVillager = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.theGolem.getLookHelper().setLookPositionWithEntity(this.theVillager, 30.0F, 30.0F);
        --this.lookTime;
    }
}