package com.solonarv.mods.golemworld.golem;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.util.BlockWithMeta;

import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * 
 * @author Solonarv
 * 
 */
public class GolemRegistry {
    protected static ArrayList<GolemRegistration> entries = new ArrayList<GolemRegistration>();
    protected static Random rand = new Random();

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

    public static void registerGolem(GolemFactory factory, BlockWithMeta mat,
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

    public static void registerGolem(GolemFactory factory, Block mat,
            GolemShapes shape) {
        registerGolem(factory, new BlockWithMeta(mat), shape);
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

    public static void spawnRandomGolem(World world, int x, int y, int z) {
        ArrayList<GolemRegistration> dumbGolems = new ArrayList<GolemRegistration>();
        for (GolemRegistration gr : entries) {
            if (!gr.factory.isSmart()) {
                dumbGolems.add(gr);
            }
        }
        int i = rand.nextInt(dumbGolems.size());
        dumbGolems.get(i).factory.make(world, x, y, z);
    }

    public static final void registerGolems() { // makes as much sense here as
        // anywhere else
        EntityRegistry.registerModEntity(EntityCustomGolem.class,
                "Custom Golem", 1, GolemWorld.instance, 15, 1, true);

        // Register all OUR golems with the golemRegistry
        GolemRegistry.registerGolem(new GolemFactory(100, "Iron Golem", 14.5,
                1.5, new ItemStack[] { new ItemStack(Item.ingotIron, 5),
                        new ItemStack(Block.plantRed, 1) }, null),
                Block.blockIron, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(15, "Dirt Golem", 6, 1.2,
                new ItemStack[] { new ItemStack(Block.dirt, 3) }, null),
                Block.dirt, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(60, "Sandstone Golem", 10,
                1, new ItemStack[] { new ItemStack(Block.sand, 4) }, null),
                Block.sandStone, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(110, "Stone Golem", 12, 1,
                new ItemStack[] { new ItemStack(Block.cobblestone, 2) }, null),
                Block.stone, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(110, "Clay Golem", 8, 2.3,
                new ItemStack[] { new ItemStack(Item.clay, 5) }, null),
                Block.blockClay, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(100, "Emerald Golem", 16,
                0, new ItemStack[] { new ItemStack(Item.emerald, 2) }, null),
                Block.blockEmerald, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(70, "Gold Golem", 16, 1,
                new ItemStack[] { new ItemStack(Item.ingotGold, 2) }, null),
                Block.blockGold, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(80, "Lapis Golem", 12,
                2.6, new ItemStack[] { new ItemStack(Item.dyePowder, 5, 4) },
                null), Block.blockLapis, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(20, "Glass Golem", 10,
                3.5, new ItemStack[] { new ItemStack(Block.glass, 3) }, null),
                Block.glass, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(
                new GolemFactory(150, "Obsidian Golem", 10, .3,
                        new ItemStack[] { new ItemStack(Block.obsidian, 2) },
                        null), Block.obsidian, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(80, "Diamond Golem", 18,
                .8, new ItemStack[] { new ItemStack(Item.diamond, 2) }, null),
                Block.blockDiamond, GolemShapes.DEFAULT);
    }
}
