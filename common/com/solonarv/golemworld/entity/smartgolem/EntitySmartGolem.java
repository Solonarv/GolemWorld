package com.solonarv.golemworld.entity.smartgolem;

import net.minecraft.world.World;

import com.solonarv.golemworld.entity.golem.EntityCustomGolem;
import com.solonarv.golemworld.inventory.InventoryGolem;

public abstract class EntitySmartGolem extends EntityCustomGolem {
	public EntitySmartGolem(World world){
		super(world);
	}
	
	protected final static float avgAttackDmg=1;
	protected final static float atkDmgStdDev=0;
	
	public final static boolean isSmart(){
		return true;
	}
	
	private InventoryGolem mainInventory;
	
	
}
