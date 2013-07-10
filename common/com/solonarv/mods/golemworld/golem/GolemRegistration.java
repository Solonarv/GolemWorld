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
    protected Class<? extends EntityCustomGolem> golemClass;
    public final boolean smart;

    public GolemRegistration(Class<? extends EntityCustomGolem> golemClass,
            BlockWithMeta upperBody, BlockWithMeta lowerBody,
            BlockWithMeta shoulders, BlockWithMeta arms, BlockWithMeta legs) {
        this.golemClass = golemClass;
        this.upperBody = upperBody != null ? upperBody
                : new BlockWithMeta(null);
        this.lowerBody = lowerBody != null ? lowerBody
                : new BlockWithMeta(null);
        ;
        this.shoulders = shoulders != null ? shoulders
                : new BlockWithMeta(null);
        ;
        this.arms = arms != null ? arms : new BlockWithMeta(null);
        ;
        this.legs = legs != null ? legs : new BlockWithMeta(null);
        ;
        boolean temp = false;
        try {
            temp = (Boolean) golemClass.getMethod("isSmart").invoke(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            smart = temp;
        }
    }

    public GolemRegistration(Class<? extends EntityCustomGolem> golemClass,
            Block upperBody, Block lowerBody, Block shoulders, Block arms,
            Block legs) {
        this(golemClass, new BlockWithMeta(upperBody), new BlockWithMeta(
                lowerBody), new BlockWithMeta(shoulders), new BlockWithMeta(
                arms), new BlockWithMeta(legs));
    }

    public boolean checkAt(World world, int x, int y, int z, boolean clearShape) {
        int headID = world.getBlockId(x, y, z);
        TransactionDeleteBlocks remove = clearShape ? new TransactionDeleteBlocks()
                : null;
        if (headID == Block.pumpkinLantern.blockID || !smart
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

    public EntityCustomGolem spawn(World world, int x, int y, int z) {
        EntityCustomGolem theGolem = null;
        try {
            theGolem = golemClass.getConstructor(World.class)
                    .newInstance(world);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (theGolem != null) {
            theGolem.setLocationAndAngles(x + .5d, y - 2, z + .5d, 0, 0);
            world.spawnEntityInWorld(theGolem);
        }
        return theGolem;
    }
}
