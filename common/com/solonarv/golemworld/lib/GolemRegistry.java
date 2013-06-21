package com.solonarv.golemworld.lib;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.solonarv.golemworld.GolemWorld;
import com.solonarv.golemworld.entity.golem.EntityCustomGolem;
import com.solonarv.golemworld.entity.golem.EntityDirtGolem;

import cpw.mods.fml.common.registry.EntityRegistry;

public class GolemRegistry {
	
    public static class GolemRegistration<T extends EntityCustomGolem>{
        public Class<T> golemClass;
        public Block shoulders, upperBody, lowerBody, arms, legs;
        public boolean isSmart;
        
        public GolemRegistration(Class<T> golemClass, Block shoulders, Block upperBody, Block lowerBody, Block arms, Block legs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
            this.golemClass=golemClass;
            this.shoulders=shoulders;
            this.upperBody=upperBody;
            this.lowerBody=lowerBody;
            this.arms=arms;
            this.legs=legs;
            this.isSmart=(Boolean)this.golemClass.getMethod("isSmart").invoke(null);
        }
        
        /**
         * Checks if the target block is considered a valid head for this golem recipe:
         * A pumpkinLantern will always work, and nos-smart golems will be OK with a regular pumpkin as well. 
         * @param world A World to check in
         * @param x x coord of target
         * @param y y coord of target
         * @param z z coord of target
         * @return whether or not the target block is a valid golem head
         */
        private boolean isBlockValidHead(IBlockAccess world, int x, int y, int z){
            return (!this.isSmart && world.getBlockId(x, y, z)==Block.pumpkin.blockID) || world.getBlockId(x, y, z)==Block.pumpkinLantern.blockID;
        }
        
        /**
         * Checks for a valid construction of this recipe at target location
         * @param world A World to check in
         * @param x x coord of target
         * @param y y coord of target
         * @param z z coord of target
         * @return coords where a golem should spawn if valid contruction, else null.
         */
        public EntityPos checkFromPumpkin(IBlockAccess world, int x, int y, int z){
                
                if(!this.isBlockValidHead(world, x, y, z))
                    return null;
                // Check the 'stem' first
                if(!(this.upperBody==null || world.getBlockId(x, y-1, z)==this.upperBody.blockID)
                        || !(this.lowerBody==null || world.getBlockId(x, y-2, z)==this.lowerBody.blockID))
                    return null;
                // Attempt to find out what orientation the golem has
                Boolean orientation=null; // false: along x-axis, true: along z-axis
                // If possible, use its shoulders
                if(this.shoulders!=null){
                    if(world.getBlockId(x+1, y, z) == this.shoulders.blockID
                            && world.getBlockId(x-1, y, z) == this.shoulders.blockID)
                        orientation=false;
                    else if(world.getBlockId(x, y, z+1) == this.shoulders.blockID
                            && world.getBlockId(x, y, z+1)==this.shoulders.blockID)
                        orientation=true;
                    else return null; // shoulders don't match
                }
                // Now, try the arms
                if(this.arms!=null)
                    // Shoulder check was uninformative: this.shoulder==null
                    if(orientation==null){
                        if(world.getBlockId(x+1, y-1, z) == this.arms.blockID
                                && world.getBlockId(x-1, y-1, z) == this.arms.blockID)
                            orientation=false;
                        else if(world.getBlockId(x, y-1, z+1) == this.arms.blockID
                                && world.getBlockId(x, y-1, z+1)==this.arms.blockID)
                            orientation=true;
                        else return null; // arms don't match
                    }else if(orientation){
                        if(!(world.getBlockId(x, y-1, z+1) == this.arms.blockID
                                && world.getBlockId(x, y-1, z+1)==this.arms.blockID))
                            return null;
                    }else{
                        if(!(world.getBlockId(x+1, y-1, z) == this.arms.blockID
                                && world.getBlockId(x-1, y-1, z) == this.arms.blockID))
                            return null;
                    }
                // Finally, the legs
                if(this.legs!=null)
                    // Shoulder and arm checks were uninformative: this.shoulder==null && this.arms==null
                    if(orientation==null){
                        if(world.getBlockId(x+1, y-2, z) == this.legs.blockID
                                && world.getBlockId(x-1, y-2, z) == this.legs.blockID)
                            orientation=false;
                        else if(world.getBlockId(x, y-2, z+1) == this.legs.blockID
                                && world.getBlockId(x, y-2, z+1)==this.legs.blockID)
                            orientation=true;
                        else return null; // arms don't match
                    }else if(orientation){
                        if(!(world.getBlockId(x, y-2, z+1) == this.legs.blockID
                                && world.getBlockId(x, y-2, z+1)==this.legs.blockID))
                            return null;
                    }else{
                        if(!(world.getBlockId(x+1, y-2, z) == this.legs.blockID
                                && world.getBlockId(x-1, y-2, z) == this.legs.blockID))
                            return null;
                    }
            
            return new EntityPos(x, y-2, z, orientation?0:3);
        }
        
        /**
         * Checks checkFromPumpkin and spawns the golem if true.
         * @param world A World to check in
         * @param x x coord of target
         * @param y y coord of target
         * @param z z coord of target
         * @return The golem if spawned, else null.
         * @throws InstantiationException
         * @throws IllegalAccessException
         * @throws IllegalArgumentException
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         * @throws SecurityException
         */
        public T checkAndSpawnIfValid(World world, int x, int y, int z) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
            EntityPos pos=this.checkFromPumpkin(world, x, y, z);
            if(pos==null)
                return null;
            this.clearFromWorld(world,x,y,z, (pos.facing%2==1));
            T golem=this.golemClass.getConstructor(World.class).newInstance(world);
            golem.setPositionAndRotation(pos.posX, pos.posY, pos.posZ, pos.facing*360f, 0);
            return golem;
        }

        private void clearFromWorld(World world, int x, int y, int z, boolean f) {
            world.setBlockToAir(x, y, z);
            if(this.upperBody!=null)
                world.setBlockToAir(x, y-1, z);
            if(this.lowerBody!=null)
                world.setBlockToAir(x, y-2, z);
            if(f){ // golem faces +-z, we have to clear +-x
                if(this.shoulders!=null){
                    world.setBlockToAir(x-1, y, z);
                    world.setBlockToAir(x+1, y, z);
                }
                if(this.arms!=null){
                    world.setBlockToAir(x-1, y-1, z);
                    world.setBlockToAir(x+1, y-1, z);
                }
                if(this.legs!=null){
                    world.setBlockToAir(x-1, y-2, z);
                    world.setBlockToAir(x+1, y-2, z);
                }
            }else{ // golem faces +-x, we have to clear +-z
                if(this.shoulders!=null){
                    world.setBlockToAir(x, y, z-1);
                    world.setBlockToAir(x, y, z+1);
                }
                if(this.arms!=null){
                    world.setBlockToAir(x, y-1, z-1);
                    world.setBlockToAir(x, y-1, z+1);
                }
                if(this.legs!=null){
                    world.setBlockToAir(x, y-2, z-1);
                    world.setBlockToAir(x, y-2, z+1);
                }
            }
        }
    }
    
    protected static class EntityPos{
        public double posX;
        public double posY;
        public double posZ;
        
        public int facing;
        
        public EntityPos(){
            this.posX=0D;
            this.posY=0D;
            this.posZ=0D;
            this.facing=0;
        }
        public EntityPos(double x, double y, double z, int f){
            this.posX=x;
            this.posY=y;
            this.posZ=z;
            this.facing=f;
        }
    };
    
	@SuppressWarnings("rawtypes")
    public static GolemRegistration[] entries=new GolemRegistration[4096];
	private static int nextId=0;
	
	/**
	 * Adds a golem recipe in the following shape (P is the head -- a pumpkin):
	 * <kbd>
	 * <br>sPs
	 * <br>aUa
	 * <br>lLl
	 * </kbd>
	 * @param golemClass Class of the golem whose recipe is being added.
	 * @param shoulder (s) Block to use as the golem's shoulders
	 * @param upperBody (U) Block to use as the golem's upper body (below the head)
	 * @param lowerBody (L) Block to use as the golem's lower body (below U)
	 * @param arms (a) Block to use as the golem's arms (left/right of U)
	 * @param legs (l) Block to use as the golem's legs(left/right of L)
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
    public static <T extends EntityCustomGolem> void addRecipe(Class<T> golemClass, Block shoulders, Block upperBody, Block lowerBody, Block arms, Block legs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
	    
        if(nextId>4095)
            return;
	    entries[nextId]=new GolemRegistration<T>(golemClass, shoulders, upperBody, lowerBody, arms, legs);
	    try {
            EntityRegistry.registerModEntity(golemClass, (String)(golemClass.getField("name").get(null)), nextId, GolemWorld.instance, 40, 1, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
	    
	    nextId++;
	}
    
    /**
     * Adds a golem recipe in the default shape: <bR>
     * _P_<br>
     * ### <br>
     * _#_ <br>
     * where P is the pumpkin, # is the given material, _ is anything.
     * @param golemClass
     * @param golemMat
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static <T extends EntityCustomGolem> void addRecipe(Class<T> golemClass, Block golemMat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
        addRecipe(golemClass, null, golemMat, golemMat, golemMat, null);
    }
    
    public static EntityCustomGolem checkAllAndSpawn(World world, int x, int y, int z) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
        for(GolemRegistration<?> gr : entries) if(gr!=null){
            EntityCustomGolem g=gr.checkAndSpawnIfValid(world, x, y, z);
            if(g!=null)
                return g;
        }
        return null;
    }

    public static void registerGolems() {
        try {
            addRecipe(EntityDirtGolem.class, Block.dirt);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}