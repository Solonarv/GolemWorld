package com.solonarv.golemworld.entity.golem;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.golemworld.lib.GolemRegistry.GolemRegistration;


public class EntityCustomGolem extends net.minecraft.entity.monster.EntityIronGolem{
    
    public static int id;
    public static String name;

    public EntityCustomGolem(World par1World)
    {
        super(par1World);
        this.texture = "/mob/villager_golem.png";
    }
    
	public static GolemRegistration<EntityCustomGolem> recipe;
	//Attack damage is gaussian-random distributed, floats are ready for 1.6 decimal health
	protected static float avgAttackDmg;
	protected static float atkDmgStdDev;
	
	@Override
	public int getMaxHealth(){
        return 0;
    };
	
	//Final methods, those are common to _all_ golems
	
	public final int getAttackStrength(){
		return MathHelper.floor_double(avgAttackDmg + this.rand.nextGaussian() * atkDmgStdDev);
	};
	
	public static boolean isSmart(){
		return false;
	}
	
	public String getName(){
	    return this.name;
	}
}
