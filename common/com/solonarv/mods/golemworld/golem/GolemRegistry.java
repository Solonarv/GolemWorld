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
    private static int nextID = 0;

    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass,
            BlockWithMeta upperBody, BlockWithMeta lowerBody,
            BlockWithMeta shoulders, BlockWithMeta arms, BlockWithMeta legs) {
        entries.add(new GolemRegistration(golemClass, upperBody, lowerBody,
                shoulders, arms, legs));
        EntityRegistry.registerModEntity(golemClass, golemClass.getName(),
                nextID++, GolemWorld.instance, 40, 1, true);
    }

    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass, Block upperBody,
            Block lowerBody, Block shoulders, Block arms, Block legs) {
        entries.add(new GolemRegistration(golemClass, upperBody, lowerBody,
                shoulders, arms, legs));
    }

    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass, BlockWithMeta mat,
            GolemShapes shape) {
        switch (shape) {
            case DEFAULT:
                registerGolem(golemClass, mat, mat, null, mat, null);
                break;
            case FULL:
                registerGolem(golemClass, mat, mat, mat, mat, mat);
                break;
            case PILLAR:
                registerGolem(golemClass, mat, mat, null, null, null);
                break;
            default:
                return;

        }
    }

    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass, Block mat,
            GolemShapes shape) {
        registerGolem(golemClass, new BlockWithMeta(mat), shape);
    }

    public static GolemRegistration findMatch(World world, int x, int y, int z) {
        for (GolemRegistration gr : entries) {
            if (gr.checkAt(world, x, y, z, true)) {
                return gr;
            }
        }
        return null;
    }

    public static EntityCustomGolem trySpawn(World world, int x, int y, int z) {
        GolemRegistration f = findMatch(world, x, y, z);
        if (f != null) {
            return f.spawn(world, x, y, z);
        }
        return null;
    }

    public static void spawnRandomGolem(World world, int x, int y, int z) {
        ArrayList<GolemRegistration> dumbGolems = new ArrayList<GolemRegistration>();
        for (GolemRegistration gr : entries) {
            if (!gr.smart) {
                dumbGolems.add(gr);
            }
        }
        int i = rand.nextInt(dumbGolems.size());
        dumbGolems.get(i).spawn(world, x, y, z);
    }

    public static final void registerGolems() {
        // Register all OUR golems with the golemRegistry
        GolemRegistry.registerGolem(EntityIronGolem.class, Block.blockIron,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityDirtGolem.class, Block.dirt,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntitySandstoneGolem.class,
                Block.sandStone, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityStoneGolem.class, Block.stone,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityClayGolem.class, Block.blockClay,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityEmeraldGolem.class,
                Block.blockEmerald, GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityGoldGolem.class, Block.blockGold,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityLapisGolem.class, Block.blockLapis,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityGlassGolem.class, Block.glass,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityObsidianGolem.class, Block.obsidian,
                GolemShapes.DEFAULT);
        GolemRegistry.registerGolem(EntityDiamondGolem.class,
                Block.blockDiamond, GolemShapes.DEFAULT);
    }
}
