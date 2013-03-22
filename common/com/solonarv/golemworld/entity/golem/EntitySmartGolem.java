package com.solonarv.golemworld.entity.golem;

import com.solonarv.golemworld.entity.EntityGolem;
import net.minecraft.world.World;

public abstract class EntitySmartGolem extends EntityGolem {
	public EntitySmartGolem(World world){
		super(world);
	}
	
	protected static float avgAttackDmg=1;
	protected static float atkDmgStdDev=0;
}
