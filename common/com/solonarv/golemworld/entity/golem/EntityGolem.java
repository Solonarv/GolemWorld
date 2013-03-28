package com.solonarv.golemworld.entity.golem;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;


public abstract class EntityGolem extends EntityMob {
	public EntityGolem(World world){
		super(world);
		this.experienceValue=0;
	}
	
	protected static Object[] recipe={};
	protected static int maxHealth;
	//Attack damage is gaussian-random distributed, floats are ready for 1.6 decimal health
	protected static float avgAttackDmg;
	protected static float atkDmgStdDev;
	
	//Final methods, those are common to _all_ golems
	public final int getMaxHealth(){
		return this.maxHealth;
	};
	public final int getAttackStrength(){
		return MathHelper.floor_double(this.avgAttackDmg + this.rand.nextGaussian() * this.atkDmgStdDev);
	};
	/**
     * Decrements the entity's air supply when underwater
     */
	protected final int decreaseAirSupply(int par1){
        return par1;
    }
	
}
