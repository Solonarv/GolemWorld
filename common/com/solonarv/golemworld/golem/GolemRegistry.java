package com.solonarv.golemworld.golem;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.golemworld.GolemWorld;
import com.solonarv.golemworld.util.BlockWithMeta;

import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * 
 * @author Solonarv
 * 
 */
public class GolemRegistry {
    protected static ArrayList<GolemRegistration> entries = new ArrayList<GolemRegistration>();

    public static void registerGolem(GolemFactory factory,
            BlockWithMeta upperBody, BlockWithMeta lowerBody,
            BlockWithMeta shoulders, BlockWithMeta arms, BlockWithMeta legs) {
        entries.add(new GolemRegistration(factory, upperBody, lowerBody,
                shoulders, arms, legs));
    }

    public static void registerGolem(GolemFactory factory, Block upperBody,
            Block lowerBody, Block shoulders, Block arms, Block legs) {
        entries.add(new GolemRegistration(factory, upperBody, lowerBody,
                shoulders, arms, legs));
    }

    public static void registerGolem(GolemFactory factory, Block mat,
            GolemShapes shape) {
        switch (shape) {
            case DEFAULT:
                registerGolem(factory, mat, mat, null, mat, null);
                break;
            case FULL:
                registerGolem(factory, mat, mat, mat, mat, mat);
                break;
            case PILLAR:
                registerGolem(factory, mat, mat, null, null, null);
                break;
            default:
                return;

        }
    }

    public static GolemFactory findMatch(World world, int x, int y, int z) {
        for (GolemRegistration gr : entries) {
            if (gr.checkAt(world, x, y, z, true)) {
                return gr.factory;
            }
        }
        return null;
    }

    public static EntityCustomGolem trySpawn(World world, int x, int y, int z) {
        GolemFactory f = findMatch(world, x, y, z);
        if (f != null) {
            return f.make(world, x, y, z);
        }
        return null;
    }

    public static final void registerGolems() { // makes as much sense here as
        // anywhere else
        EntityRegistry.registerModEntity(EntityCustomGolem.class,
                "Custom Golem", 1, GolemWorld.instance, 15, 1, true);
        // Register all OUR golems with the golemRegistry
        GolemRegistry.registerGolem(new GolemFactory(15, "Dirt Golem", 6, 1.2,
                new ItemStack[] { new ItemStack(Block.dirt, 3) }, null),
                Block.dirt, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(20, "Glass Golem", 10,
                3.5, new ItemStack[] { new ItemStack(Block.glass, 3) }, null),
                Block.glass, GolemShapes.DEFAULT);
    }
}
