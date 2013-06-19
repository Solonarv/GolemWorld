package com.solonarv.golemworld.entity.ai;

import com.solonarv.golemworld.entity.golem.EntityCustomGolem;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.village.Village;

public class EntityAIDefendVillage extends EntityAITarget
{
    EntityCustomGolem thegolem;

    /**
     * The aggressor of the iron golem's village which is now the golem's attack target.
     */
    EntityLiving villageAgressorTarget;

    public EntityAIDefendVillage(EntityCustomGolem par1EntityGolem)
    {
        super(par1EntityGolem, 16.0F, false, true);
        this.thegolem = par1EntityGolem;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        Village village = this.thegolem.getVillage();

        if (village == null)
        {
            return false;
        }
        else
        {
            this.villageAgressorTarget = village.findNearestVillageAggressor(this.thegolem);

            if (!this.isSuitableTarget(this.villageAgressorTarget, false))
            {
                if (this.taskOwner.getRNG().nextInt(20) == 0)
                {
                    this.villageAgressorTarget = village.func_82685_c(this.thegolem);
                    return this.isSuitableTarget(this.villageAgressorTarget, false);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.thegolem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }
}

