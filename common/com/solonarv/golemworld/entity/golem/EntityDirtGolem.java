package com.solonarv.golemworld.entity.golem;

import net.minecraft.world.World;

public class EntityDirtGolem extends EntityCustomGolem {
	
    public static final String name="Dirt Golem";
    
	protected static int maxHealth=20; // 10 hearts
	
	public EntityDirtGolem(World world){
		super(world);
	}
	
	@Override
    public int getMaxHealth(){
        return 20;
    };
    
    @Override
    public final String getName(){
        return "Dirt Golem";
    }
}
