package com.solonarv.golemworld.entity.golem;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

/**
 * A single entry in the golem registry
 * 
 * @author Solonarv
 * 
 */
public class GolemRegistration {
    /**
     * A block with a metadata. Use meta of -1 for wildcard.
     * 
     * 
     */
    public static class BlockWithMeta {
        public Block b;
        public int m;

        public BlockWithMeta(Block block, int meta) {
            b = block;
            m = meta;
        }

        public BlockWithMeta(Block b) {
            this(b, -1);
        }

        public boolean isAt(IBlockAccess world, int x, int y, int z) {
            return b == null || world.getBlockId(x, y, z) == b.blockID
                    && (m == -1 || world.getBlockMetadata(x, y, z) == m);
        }
    }

    BlockWithMeta upperBody, lowerBody, shoulders, arms, legs;
    GolemFactory factory;

    public GolemRegistration(GolemFactory factory, BlockWithMeta upperBody,
            BlockWithMeta lowerBody, BlockWithMeta shoulders,
            BlockWithMeta arms, BlockWithMeta legs) {
        this.factory = factory;
        this.upperBody = upperBody;
        this.lowerBody = lowerBody;
        this.shoulders = shoulders;
        this.arms = arms;
        this.legs = legs;
    }

    public GolemRegistration(GolemFactory factory, Block upperBody,
            Block lowerBody, Block shoulders, Block arms, Block legs) {
        this(factory, new BlockWithMeta(upperBody),
                new BlockWithMeta(lowerBody), new BlockWithMeta(shoulders),
                new BlockWithMeta(arms), new BlockWithMeta(legs));
    }

    public boolean checkAt(IBlockAccess world, int x, int y, int z) {
        int headID = world.getBlockId(x, y, z);
        if (headID == Block.pumpkinLantern.blockID || !factory.isSmart()
                && headID == Block.pumpkin.blockID) {
            if (upperBody.isAt(world, x, y - 1, z)
                    && lowerBody.isAt(world, x, y - 2, z)) {
                // Check +-x first
                if (shoulders.isAt(world, x - 1, y, z)
                        && shoulders.isAt(world, x + 1, y, z)
                        && arms.isAt(world, x - 1, y - 1, z)
                        && arms.isAt(world, x + 1, y - 1, z)
                        && legs.isAt(world, x - 1, y - 1, z)
                        && legs.isAt(world, x + 1, y - 1, z)) {
                    return true;
                }
                // Check +-z now
                if (shoulders.isAt(world, x, y, z - 1)
                        && shoulders.isAt(world, x, y, z + 1)
                        && arms.isAt(world, x, y - 1, z - 1)
                        && arms.isAt(world, x, y - 1, z + 1)
                        && legs.isAt(world, x, y - 1, z - 1)
                        && legs.isAt(world, x, y - 1, z + 1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
