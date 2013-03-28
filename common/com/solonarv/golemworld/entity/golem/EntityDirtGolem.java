package com.solonarv.golemworld.entity.golem;

import net.minecraft.world.World;
import net.minecraft.block.Block;

import com.solonarv.golemworld.entity.golem.EntityGolem;

public class EntityDirtGolem extends EntityGolem {
	
	protected static Object[] recipe={" p ","ddd"," g ",
		"p", Block.pumpkin,
		"d", Block.dirt,
		"g", Block.grass};
	protected static int maxHealth;
	
	protected static float avgAttackDmg;
	protected static float atkDmgStdDev;
	
	public EntityDirtGolem(World world){
		super(world);
		this.experienceValue=0;
	}
}
