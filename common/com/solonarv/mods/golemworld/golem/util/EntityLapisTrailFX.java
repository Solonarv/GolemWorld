package com.solonarv.mods.golemworld.golem.util;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityLapisTrailFX extends EntityBreakingFX {

    public EntityLapisTrailFX(World par1World, double par2, double par4, double par6, int maxAge) {
        super(par1World, par2, par4, par6, Item.itemsList[Block.blockLapis.blockID]);
        this.particleMaxAge=maxAge;
        this.particleGravity=0;
    }
    
}
