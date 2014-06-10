package com.solonarv.mods.golemworld.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.GolemRegistry;

/**
 * Holds information about not only a block, but also its metadata (damage
 * value). Used so that metadata-sensitive checks can be made easily in the
 * {@link GolemRegistry}
 * 
 * @author Solonarv
 * 
 */
public class BlockRef {
    public Block block;
    public int   meta;
    
	public BlockRef(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

	public BlockRef(Block block) {
		this(block, -1);
	}

	public boolean matches(BlockRef other) {
		return other!=null && (this.block==null || this.block==other.block) &&
				(this.meta==-1 || this.meta==other.meta);
	}

	public static BlockRef fromWorld(World world, int x, int y, int z) {
		return new BlockRef(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
	}
	
	public String toString() {
	    return block.getUnlocalizedName() + "/" + (meta==-1? "*" : meta); 
	}
}