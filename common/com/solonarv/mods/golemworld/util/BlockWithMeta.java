package com.solonarv.mods.golemworld.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockWithMeta {
    public Block b;
    public int m;

    public BlockWithMeta(Block block, int meta) {
        b = block;
        m = meta;
    }

    public BlockWithMeta(Block b) {
        this(b, -1);
    }

    public boolean isAt(World world, int x, int y, int z,
            TransactionDeleteBlocks remover) {
        boolean result = b == null || world.getBlockId(x, y, z) == b.blockID
                && (m == -1 || world.getBlockMetadata(x, y, z) == m);
        if (result && b != null && remover != null) {
            remover.addAction(world, x, y, z);
        }
        return result;
    }
}