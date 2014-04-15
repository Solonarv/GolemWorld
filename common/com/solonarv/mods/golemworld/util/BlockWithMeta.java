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
public class BlockWithMeta {
    public String blockName;
    public Block block;
    public int   meta;
    
    /**
     * A simple identity constructor
     * 
     * @param block
     * @param meta
     */
    public BlockWithMeta(String block, int meta) {
        this.blockName = block;
        this.block=(block==null || block=="" || block=="air")?null:Block.getBlockFromName(block);
        this.meta = meta;
    }
    
    /**
     * Constructor that makes checks using this ignore metadata; Provided for
     * convenience.
     * 
     * @param b
     */
    public BlockWithMeta(String b) {
        this(b, -1);
    }
    
    /**
     * Checks if the (block,metadate) tuple represented by this object is
     * located at the specified coords.
     * 
     * @param world The {@link World} to check in.
     * @param x x coord to check at
     * @param y y coord to check at
     * @param z z coord to check at
     * @param remover A {@link TransactionDeleteBlocks} to remove multiple
     *        blocks at once. May be set to null, it will then be ignored.
     * @return
     */
    public boolean isAt(World world, int x, int y, int z,
            TransactionDeleteBlocks remover) {
        boolean result = this.block == null || Block.isEqualTo(world.getBlock(x, y, z), this.block)
                && (this.meta == -1 || world.getBlockMetadata(x, y, z) == this.meta);
        if (result && this.block != null && remover != null) {
            remover.addAction(world, x, y, z);
        }
        return result;
    }
}