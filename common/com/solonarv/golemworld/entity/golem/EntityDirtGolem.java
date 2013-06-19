package com.solonarv.golemworld.entity.golem;

import net.minecraft.world.World;
import com.solonarv.golemworld.entity.golem.EntityCustomGolem;

public class EntityDirtGolem extends EntityCustomGolem {
	
	protected static int maxHealth;
	
	protected static float avgAttackDmg;
	protected static float atkDmgStdDev;
	
	public EntityDirtGolem(World world){
		super(world);
	}
}
