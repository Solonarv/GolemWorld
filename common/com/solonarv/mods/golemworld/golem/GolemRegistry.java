package com.solonarv.mods.golemworld.golem;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.golem.simple.EntityClayGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityDiamondGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityDirtGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityEmeraldGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityGlassGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityGoldGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityIronGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityLapisGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityObsidianGolem;
import com.solonarv.mods.golemworld.golem.simple.EntitySandstoneGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityStoneGolem;
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
        GolemRegistry.registerGolem(new GolemFactory(EntityIronGolem.class),
                Block.blockIron, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityDirtGolem.class),
                Block.dirt, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(
                new GolemFactory(EntitySandstoneGolem.class), Block.sandStone,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityStoneGolem.class),
                Block.stone, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityClayGolem.class),
                Block.blockClay, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityEmeraldGolem.class),
                Block.blockEmerald, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityGoldGolem.class),
                Block.blockGold, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityLapisGolem.class),
                Block.blockLapis, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityGlassGolem.class),
                Block.glass, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(
                new GolemFactory(EntityObsidianGolem.class), Block.obsidian,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(new GolemFactory(EntityDiamondGolem.class),
                Block.blockDiamond, GolemShapes.DEFAULT);
    }
}
