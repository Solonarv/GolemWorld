package com.solonarv.mods.golemworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.world.World;

public class BlockPumpkinFixed extends BlockPumpkin {

    protected BlockPumpkinFixed(boolean isLit) {
        super(isLit);
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z){
        Block block = world.getBlock(x, y, z);
        return block == null || block.isReplaceable(world, x, y, z);
    }
}
