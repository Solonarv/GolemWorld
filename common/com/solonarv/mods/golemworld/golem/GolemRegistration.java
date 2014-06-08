package com.solonarv.mods.golemworld.golem;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.solonarv.mods.golemworld.util.BlockRef;

/**
 * A single entry in the golem registry. Stores the golem's in-world
 * construction schematic and is able to create and spawn a golem.
 * 
 * @author Solonarv
 * 
 */
public class GolemRegistration {
    
    protected Class<? extends EntityCustomGolem> golemClass;
    public final boolean smart, villageSpawnable;
    private String golemName;
    public IShapeMatcher shape;
    private boolean[][] toRemove;
    
    /**
     * Full constructor: all other constructors delegate to this one. Also
     * precomputes smartness assertion via reflection.
     * 
     * @param golemClass The class of the golem this registration is for
     * @param upperBody The block used as the golem's uppper body
     * @param lowerBody The block used as the golem's lower body
     * @param shoulders The block used as the golem's shoulders
     * @param arms The block used as the golem's arms
     * @param legs The block used as the golem's legs
     */
    public GolemRegistration(Class<? extends EntityCustomGolem> golemClass, IShapeMatcher shape) {
        this.golemClass = golemClass;
        this.shape=shape;
        boolean tsmart = false, tvillage = true;
        try {
            GolemStats stats = (GolemStats) golemClass.getDeclaredField("stats").get(null);
            tsmart = stats.smart;
            tvillage = stats.villageSpawnable;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.smart = tsmart;
            this.villageSpawnable = tvillage;
        }
        this.toRemove=shape.removeWhich();
        if(!(toRemove.length==3 && toRemove[0].length==2 && toRemove[1].length==3 && toRemove[2].length==3)){
            throw new IllegalArgumentException("IShapeMatcher returned invalid outline when registering golem " + golemClass.getSimpleName());
        }
    }
    
    /**
     * Checks if a a valid multiblock for this golem is constructed with its
     * head at coords (x,y,z).
     * 
     * @param world The {@link World} to check in
     * @param x The x coord to check at
     * @param y The y coord to check at
     * @param z The z coord to check at
     * @param clearShape Whether or not to remove the affected blocks on
     *        successful match
     * @return The direction the golem is facing, or null if it's not a valid build
     */
    public int checkAt(World world, int x, int y, int z, boolean remove) {
        Block head = world.getBlock(x, y, z);
        if(! (head==Blocks.lit_pumpkin || (!this.smart && head==Blocks.pumpkin)) ){
            return -1;
        }
        // Check for the golem facing North (-z)
        if(shape.isMatched(BlockRef.fromWorld(world, x-1, y, z), BlockRef.fromWorld(world, x+1, y, z),
                BlockRef.fromWorld(world, x, y-1, z), BlockRef.fromWorld(world, x-1, y-1, z), BlockRef.fromWorld(world, x+1, y-1, z),
                BlockRef.fromWorld(world, x, y-2, z), BlockRef.fromWorld(world, x-1, y-2, z), BlockRef.fromWorld(world, x+1, y-2, z))){
            if(remove){
                world.setBlockToAir(x, y, z);
                if(toRemove[0][0]) world.setBlockToAir(x-1, y, z);
                if(toRemove[0][1]) world.setBlockToAir(x+1, y, z);
                if(toRemove[1][0]) world.setBlockToAir(x-1, y-1, z);
                if(toRemove[1][1]) world.setBlockToAir(x, y-1, z);
                if(toRemove[1][2]) world.setBlockToAir(x+1, y-1, z);
                if(toRemove[2][0]) world.setBlockToAir(x-1, y-2, z);
                if(toRemove[2][1]) world.setBlockToAir(x, y-2, z);
                if(toRemove[2][2]) world.setBlockToAir(x+1, y-2, z);
            }
            return 2;
        }
     // Check for the golem facing South (+z)
        if(shape.isMatched(BlockRef.fromWorld(world, x+1, y, z), BlockRef.fromWorld(world, x-1, y, z),
                BlockRef.fromWorld(world, x, y-1, z), BlockRef.fromWorld(world, x+1, y-1, z), BlockRef.fromWorld(world, x-1, y-1, z),
                BlockRef.fromWorld(world, x, y-2, z), BlockRef.fromWorld(world, x+1, y-2, z), BlockRef.fromWorld(world, x-1, y-2, z))){
            if(remove){
                world.setBlockToAir(x, y, z);
                if(toRemove[0][0]) world.setBlockToAir(x+1, y, z);
                if(toRemove[0][1]) world.setBlockToAir(x-1, y, z);
                if(toRemove[1][0]) world.setBlockToAir(x+1, y-1, z);
                if(toRemove[1][1]) world.setBlockToAir(x, y-1, z);
                if(toRemove[1][2]) world.setBlockToAir(x-1, y-1, z);
                if(toRemove[2][0]) world.setBlockToAir(x+1, y-2, z);
                if(toRemove[2][1]) world.setBlockToAir(x, y-2, z);
                if(toRemove[2][2]) world.setBlockToAir(x-1, y-2, z);
            }
            return 0;
        }
        // Check for the golem facing East (+x)
        if(shape.isMatched(BlockRef.fromWorld(world, x, y, z-1), BlockRef.fromWorld(world, x, y, z+1),
                BlockRef.fromWorld(world, x, y-1, z), BlockRef.fromWorld(world, x, y-1, z-1), BlockRef.fromWorld(world, x, y-1, z+1),
                BlockRef.fromWorld(world, x, y-2, z), BlockRef.fromWorld(world, x, y-2, z-1), BlockRef.fromWorld(world, x, y-2, z+1))){
            if(remove){
                world.setBlockToAir(x, y, z);
                if(toRemove[0][0]) world.setBlockToAir(x, y, z-1);
                if(toRemove[0][1]) world.setBlockToAir(x, y, z+1);
                if(toRemove[1][0]) world.setBlockToAir(x, y-1, z-1);
                if(toRemove[1][1]) world.setBlockToAir(x, y-1, z);
                if(toRemove[1][2]) world.setBlockToAir(x, y-1, z+1);
                if(toRemove[2][0]) world.setBlockToAir(x, y-2, z-1);
                if(toRemove[2][1]) world.setBlockToAir(x, y-2, z);
                if(toRemove[2][2]) world.setBlockToAir(x, y-2, z+1);
            }
            return 1;
        }
        // Check for the golem facing West (-x)
        if(shape.isMatched(BlockRef.fromWorld(world, x, y, z+1), BlockRef.fromWorld(world, x, y, z-1),
                BlockRef.fromWorld(world, x, y-1, z), BlockRef.fromWorld(world, x, y-1, z+1), BlockRef.fromWorld(world, x, y-1, z-1),
                BlockRef.fromWorld(world, x, y-2, z), BlockRef.fromWorld(world, x, y-2, z+1), BlockRef.fromWorld(world, x, y-2, z-1))){
            if(remove){
                world.setBlockToAir(x, y, z);
                if(toRemove[0][0]) world.setBlockToAir(x, y, z+1);
                if(toRemove[0][1]) world.setBlockToAir(x, y, z-1);
                if(toRemove[1][0]) world.setBlockToAir(x, y-1, z+1);
                if(toRemove[1][1]) world.setBlockToAir(x, y-1, z);
                if(toRemove[1][2]) world.setBlockToAir(x, y-1, z-1);
                if(toRemove[2][0]) world.setBlockToAir(x, y-2, z+1);
                if(toRemove[2][1]) world.setBlockToAir(x, y-2, z);
                if(toRemove[2][2]) world.setBlockToAir(x, y-2, z-1);
            }
            return 3;
        }
        return -1;
    }
    
    /**
     * Spawn a golem after passing a check for valid construction.
     */
    public EntityCustomGolem checkAndSpawn(World world, int x, int y, int z){
        int golemFacing=this.checkAt(world, x, y, z, true);
        if(golemFacing!=-1){
            EntityCustomGolem theGolem=this.spawn(world, x, y-2, z);
            if(theGolem!=null){
                theGolem.setAngles(golemFacing*90.0F, 0);
                return theGolem;
            }
        }
        return null;
    }
    
    /**
     * Spawns a golem if this registration's type at the specified location via
     * reflection.
     * 
     * @param world The world to spawn the golem in
     * @param x The x coord to spawn at
     * @param y The y coord to spawn at
     * @param z The z coord to spawn at
     * @return
     */
    public EntityCustomGolem spawn(World world, double x, double y, double z) {
        EntityCustomGolem theGolem = null;
        try {
            theGolem = golemClass.getConstructor(World.class)
                    .newInstance(world);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (theGolem != null) {
            theGolem.setLocationAndAngles(x, y, z, 0, 0);
            world.spawnEntityInWorld(theGolem);
        }
        return theGolem;
    }

    public String getGolemName() {
        if(this.golemName == null){
            try{
                this.golemName = ((GolemStats) this.golemClass.getDeclaredField("stats").get(null)).name;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return this.golemName;
    }
}
