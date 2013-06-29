package com.solonarv.mods.golemworld.golem;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.util.BlockWithMeta;
import com.solonarv.mods.golemworld.util.TransactionDeleteBlocks;

/**
 * A single entry in the golem registry
 * 
 * @author Solonarv
 * 
 */
public class GolemRegistration {

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

    public boolean checkAt(World world, int x, int y, int z, boolean clearShape) {
        int headID = world.getBlockId(x, y, z);
        TransactionDeleteBlocks remove = clearShape ? new TransactionDeleteBlocks()
                : null;
        if (headID == Block.pumpkinLantern.blockID || !factory.isSmart()
                && headID == Block.pumpkin.blockID) {
            if (upperBody.isAt(world, x, y - 1, z, remove)
                    && lowerBody.isAt(world, x, y - 2, z, remove)) {
                // Check +-x first
                if (shoulders.isAt(world, x - 1, y, z, remove)
                        && shoulders.isAt(world, x + 1, y, z, remove)
                        && arms.isAt(world, x - 1, y - 1, z, remove)
                        && arms.isAt(world, x + 1, y - 1, z, remove)
                        && legs.isAt(world, x - 1, y - 1, z, remove)
                        && legs.isAt(world, x + 1, y - 1, z, remove)) {
                    if (clearShape) {
                        world.setBlockToAir(x, y, z);
                        remove.commit();
                    }
                    return true;
                } else if (clearShape) {
                    remove.abort();
                }
                // Check +-z now
                if (shoulders.isAt(world, x, y, z - 1, remove)
                        && shoulders.isAt(world, x, y, z + 1, remove)
                        && arms.isAt(world, x, y - 1, z - 1, remove)
                        && arms.isAt(world, x, y - 1, z + 1, remove)
                        && legs.isAt(world, x, y - 1, z - 1, remove)
                        && legs.isAt(world, x, y - 1, z + 1, remove)) {
                    if (clearShape) {
                        world.setBlockToAir(x, y, z);
                        remove.commit();
                    }
                    return true;
                } else if (clearShape) {
                    remove.abort();
                }
            }
        }
        return false;
    }
}
