package com.solonarv.mods.golemworld.golem.medium;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityLapisGolem extends EntityCustomGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 30;
        stats.attackDamageMean = 8;
        stats.attackDamageStdDev = 1.5f;
        stats.name = "Lapis Golem";
        stats.texture = Reference.mobTexture("lapis_golem");
        stats.droppedItems(new ItemStack(Item.dyePowder, 5, 4));
    }
    
    public EntityLapisGolem(World world) {
        super(world);
    }
    
    private LinkedList<Vec3> lastPositions=new LinkedList<Vec3>();
    
    public static final String LAPIS_PARTICLE="tilecrack_"+Block.blockLapis.blockID+"_0";
    public static final int MAX_TRAIL_LENGTH=64;
    
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if(!this.worldObj.isRemote){
            // Advance the past position queue if necessary
            Vec3 lastPos=lastPositions.peekLast();
            if(lastPos==null || lastPos.squareDistanceTo(this.posX, this.posY, this.posZ)>=1){
                if(lastPositions.size()>=MAX_TRAIL_LENGTH){
                    lastPositions.remove();
                }
                lastPositions.offer(Vec3.fakePool.getVecFromPool(this.posX, this.posY, this.posZ));
            }
        }
        for(Vec3 point: lastPositions){
            int particleCount=this.rand.nextInt(10);
            for(int i=0; i<particleCount; i++){
                this.worldObj.spawnParticle(LAPIS_PARTICLE,
                        point.xCoord-1+this.rand.nextDouble()*2, point.yCoord-1+this.rand.nextDouble()*2, point.zCoord-1+this.rand.nextDouble()*2,
                        4 * ((double)this.rand.nextDouble() - 0.5D), 1, ((double)this.rand.nextDouble() - 0.5D) * 4.0D);
            }
        }
    }
}
