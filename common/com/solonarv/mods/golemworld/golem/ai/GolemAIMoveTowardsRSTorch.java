package com.solonarv.mods.golemworld.golem.ai;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.solonarv.mods.golemworld.golem.medium.EntityRedstoneGolem;

public class GolemAIMoveTowardsRSTorch {
    
    private EntityRedstoneGolem theGolem;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private double movementSpeed;
    private int searchRadius;
    
    public GolemAIMoveTowardsRSTorch(EntityRedstoneGolem theGolem, int searchRadius) {
        this.theGolem = theGolem;
        this.movementSpeed = theGolem.getAIMoveSpeed();
        this.searchRadius = searchRadius;
    }

    public boolean preRunCheck() {
        int x0 = MathHelper.floor_double(this.theGolem.posX);
        int y0 = MathHelper.floor_double(this.theGolem.posY);
        int z0 = MathHelper.floor_double(this.theGolem.posY);
        LinkedList<Vec3> possibleTargets = new LinkedList<Vec3>();
        for(int x = x0 - this.searchRadius; x < this.searchRadius; x++)
            for(int y = y0 - this.searchRadius; y < this.searchRadius; y++)
                for(int z = z0 - this.searchRadius; z < this.searchRadius; z++){
                    Block blk = this.theGolem.worldObj.getBlock(x, y, z);
                    if(Block.isEqualTo(blk, Block.getBlockFromName("redstone_torch"))){
                        possibleTargets.add(Vec3.fakePool.getVecFromPool(x, y, z));
                    }
                }
        if(!possibleTargets.isEmpty()){
            double smallestDistanceSq = Double.POSITIVE_INFINITY;
            Vec3 closestPos = possibleTargets.getFirst();
            for(Vec3 possibleTarget : possibleTargets){
                double distSq = possibleTarget.squareDistanceTo(this.theGolem.posX, this.theGolem.posY, this.theGolem.posZ);
                if(distSq < smallestDistanceSq){
                    closestPos = possibleTarget;
                    smallestDistanceSq = distSq;
                }
            }
            this.movePosX = closestPos.xCoord;
            this.movePosY = closestPos.yCoord;
            this.movePosZ = closestPos.zCoord;
            return true;
        }else return false;
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.theGolem.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theGolem.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
    }
    
    public void runOnce(){
        if(this.preRunCheck()){
            this.theGolem.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
        }
    }
    
}
