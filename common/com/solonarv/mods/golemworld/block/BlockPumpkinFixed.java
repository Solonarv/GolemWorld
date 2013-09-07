package com.solonarv.mods.golemworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.world.World;

public class BlockPumpkinFixed extends BlockPumpkin {

    protected BlockPumpkinFixed(int par1, boolean par2) {
        super(par1, par2);
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z){
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        return block == null || block.isBlockReplaceable(world, x, y, z);
    }
}
