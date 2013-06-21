package com.solonarv.golemworld.entity.golem;

import net.minecraft.world.World;

public class EntityDirtGolem extends EntityCustomGolem {
	
    public static final String name="Dirt Golem";
    
	protected static int maxHealth;
	
	protected static float avgAttackDmg;
	protected static float atkDmgStdDev;
	
	public EntityDirtGolem(World world){
		super(world);
	}
}
