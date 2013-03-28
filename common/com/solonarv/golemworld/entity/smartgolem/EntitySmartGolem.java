package com.solonarv.golemworld.entity.smartgolem;

import net.minecraft.world.World;

import com.solonarv.golemworld.entity.golem.EntityGolem;
import com.solonarv.golemworld.inventory.InventoryGolem;

public abstract class EntitySmartGolem extends EntityGolem {
	public EntitySmartGolem(World world){
		super(world);
	}
	
	protected final static float avgAttackDmg=1;
	protected final static float atkDmgStdDev=0;
	
	private InventoryGolem mainInventory;
	
	
}
