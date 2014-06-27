package com.solonarv.mods.golemworld.golem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.golem.advanced.EntityCraftingGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityGlassGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityGlowstoneGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityGoldGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityIceGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityLapisGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityNetherrackGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityObsidianGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityQuartzGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityRedstoneGolem;
import com.solonarv.mods.golemworld.golem.medium.EntitySwitchableGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityClayGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityDiamondGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityDirtGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityEmeraldGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityHardenedClayGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityIronGolem;
import com.solonarv.mods.golemworld.golem.simple.EntitySandstoneGolem;
import com.solonarv.mods.golemworld.golem.simple.EntityStoneGolem;
import com.solonarv.mods.golemworld.util.BlockRef;

import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * A list of all golems with their respective construction multiblock recipe.
 * Also handles spawning golems when asked to.
 * 
 * @author Solonarv
 * 
 */
public class GolemRegistry {
    /**
     * A list of all the {@link GolemRegistration}s
     */
    protected static List<GolemRegistration> entries = new ArrayList<GolemRegistration>(),
            villageSpawnableGolems = new ArrayList<GolemRegistration>();
    /**
     * The GolemRegistry has its own RNG
     */
    protected static Random                  rand    = new Random();
    /**
     * The next ID available, for {@link EntityRegistry}.registerModEntity.
     */
    private static int                       nextID  = 1;
    
    public static void registerGolem(Class<? extends EntityCustomGolem> golemClass, IShapeMatcher shapeMatcher){
        GolemRegistration reg = new GolemRegistration(golemClass, shapeMatcher); 
        entries.add(reg);
        if(GolemWorld.config.getInt("village" + reg.getGolemID())==1)
            villageSpawnableGolems.add(reg);
        EntityRegistry.registerModEntity(golemClass, golemClass.getName(),
                nextID++, GolemWorld.instance, 40, 1, true);
    }
    
    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass,
            BlockRef upperBody, BlockRef lowerBody,
            BlockRef shoulders, BlockRef arms, BlockRef legs) {
        registerGolem(golemClass, new ExactShapeMatcher(shoulders, shoulders, upperBody, arms, arms, lowerBody, legs, legs));
    }
    
    /**
     * Wrapper for registerGolem(golemClass, BlockRef, ...) that lets one
     * use just plain old Block instances. Params are the same as wrapped
     * method.
     */
    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass, Block upperBody,
            Block lowerBody, Block shoulders, Block arms, Block legs) {
        registerGolem(golemClass, new BlockRef(upperBody),
                new BlockRef(lowerBody), new BlockRef(shoulders),
                new BlockRef(arms), new BlockRef(legs));
    }
    
    /**
     * Another wrapper for registerGolem(golemClass, BlockRef, ...) that
     * lets one specify a shape from an enum and a material (a BlockRef
     * that the golem is built out of); generally more readable and shorter that
     * the full invocation.
     * 
     * @param golemClass The class of the golem to register
     * @param mat A {@link BlockRef} the golem is built out of
     * @param shape A {@link GolemShapes} the golem is built in
     */
    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass, BlockRef mat,
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
    
    /**
     * A wrapper for registerGolem(Class golemClass, {@link BlockRef} mat,
     * {@link GolemShapes} shape) that takes a {@link Block} as material
     * instead. Params are the same as wrapped method.
     */
    public static void registerGolem(
            Class<? extends EntityCustomGolem> golemClass, Block mat,
            GolemShapes shape) {
        registerGolem(golemClass, new BlockRef(mat), shape);
    }
    
    /**
     * Finds the first {@link GolemRegistration} that matches at the specified
     * coords.
     * 
     * @param world The world to check in.
     * @param x x coord to check at
     * @param y y coord to check at
     * @param z z coord to check at
     * @return The first matching {@link GolemRegistration} if found, null
     *         otherwise.
     */
    public static GolemRegistration findMatch(World world, int x, int y, int z) {
        for (GolemRegistration gr : entries) {
            if (gr.checkAt(world, x, y, z, false)!=-1) { return gr; }
        }
        return null;
    }
    
    /**
     * Attempts to spawn the golem constructed at (x,y,z) in world.
     * 
     * @param world The world to check in.
     * @param x x coord to check at
     * @param y y coord to check at
     * @param z z coord to check at
     * @return The spawned golem if successful, null otherwise
     */
    public static EntityCustomGolem trySpawn(World world, int x, int y, int z) {
        for (GolemRegistration gr : entries){
            EntityCustomGolem theGolem=gr.checkAndSpawn(world, x, y, z);
            if(theGolem!=null) return theGolem;
        }
        return null;
    }
    
    /**
     * Spawns a random non-advanced golem. Currently used to replace vanilla
     * Iron Golems spawned in villages.
     * 
     * @param world The world to spawn in
     * @param posX x coord to spawn at
     * @param posY y coord to spawn at
     * @param posZ z coord to spawn at
     */
    public static void spawnRandomVillageGolem(World world, double posX, double posY, double posZ) {
        villageSpawnableGolems.get(rand.nextInt(villageSpawnableGolems.size())).spawn(world, posX, posY, posZ);
    }
    
    /**
     * Registers all golems added by the standalone mod
     */
    public static final void registerGolems() {
        // Register all OUR golems with the GolemRegistry
        registerGolem(EntityIronGolem.class, Blocks.iron_block, GolemShapes.DEFAULT);
        registerGolem(EntityDirtGolem.class, Blocks.dirt, GolemShapes.DEFAULT);
        registerGolem(EntitySandstoneGolem.class, Blocks.sandstone, GolemShapes.DEFAULT);
        registerGolem(EntityStoneGolem.class, Blocks.stone, GolemShapes.DEFAULT);
        registerGolem(EntityClayGolem.class, Blocks.clay, GolemShapes.DEFAULT);
        registerGolem(EntityEmeraldGolem.class, Blocks.emerald_block, GolemShapes.DEFAULT);
        registerGolem(EntityGoldGolem.class, Blocks.gold_block, GolemShapes.DEFAULT);
        registerGolem(EntityLapisGolem.class, Blocks.lapis_block, GolemShapes.DEFAULT);
        registerGolem(EntityGlassGolem.class, Blocks.glass, GolemShapes.DEFAULT);
        registerGolem(EntityObsidianGolem.class, Blocks.obsidian, GolemShapes.DEFAULT);
        registerGolem(EntityDiamondGolem.class, Blocks.diamond_block, GolemShapes.DEFAULT);
        registerGolem(EntityIceGolem.class, Blocks.ice, GolemShapes.DEFAULT);
        registerGolem(EntityNetherrackGolem.class, Blocks.netherrack, Blocks.netherrack, Blocks.fire, Blocks.netherrack, null);
        registerGolem(EntityRedstoneGolem.class, Blocks.redstone_block, GolemShapes.DEFAULT);
        registerGolem(EntityGlowstoneGolem.class, Blocks.glowstone, GolemShapes.DEFAULT);
        registerGolem(EntitySwitchableGolem.class, Blocks.lever, Blocks.redstone_block, null, Blocks.iron_block, null);
        registerGolem(EntityQuartzGolem.class, Blocks.quartz_block, GolemShapes.DEFAULT);
        registerGolem(EntityHardenedClayGolem.class, Blocks.hardened_clay, GolemShapes.DEFAULT);
        registerGolem(EntityCraftingGolem.class, Blocks.crafting_table, GolemShapes.DEFAULT);
        System.out.println("Registered " + entries.size() + " golems, of which " + villageSpawnableGolems.size() + " are village-spawnable.");
    }
    
    public static List<Class<? extends EntityCustomGolem>> getGolemClasses() {
        List<Class<? extends EntityCustomGolem>> ret = new ArrayList<Class<? extends EntityCustomGolem>>();
        for (GolemRegistration reg : entries) {
            ret.add(reg.golemClass);
        }
        return ret;
    }
    
    public static EntityCustomGolem spawnGolem(String golemName, World world, double x, double y, double z){
        GolemRegistration reg = null;
        for(GolemRegistration gr : entries){
            if(golemName.equalsIgnoreCase(gr.getGolemName())){
                reg=gr;
                break;
            }
        }
        if(reg != null){
            return reg.spawn(world, x, y, z);
        } else return null;
    }
    
    public static GolemRegistration findGolemReg(BlockRef lshoulder, BlockRef rshoulder, BlockRef upperbody, BlockRef larm, BlockRef rarm, BlockRef lowerbody, BlockRef lleg, BlockRef rleg){
        for(GolemRegistration reg : entries){
            if(reg.isValidShape(lshoulder, rshoulder, upperbody, larm, rarm, lowerbody, lleg, rleg)) return reg;
        }
        return null;
    }
}
