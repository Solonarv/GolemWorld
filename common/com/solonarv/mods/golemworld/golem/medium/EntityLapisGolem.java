package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.proxy.ClientProxy;

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
    
    private double lastX, lastY, lastZ;
    
    public static final int TRAIL_LIFETIME=1800; //Half an hour
    public static final double TRAIL_STEP=.1;
    
    public void onLivingUpdate(){
        super.onLivingUpdate();
        int particleCount=(int) (this.rand.nextGaussian()*3+10);
        if((this.posX-this.lastX) * (this.posX-this.lastX) +
                (this.posY-this.lastY) * (this.posY-this.lastY) +
                (this.posZ-this.lastZ) * (this.posZ-this.lastZ) >= TRAIL_STEP){
            if(GolemWorld.proxy instanceof ClientProxy) for(int i=0; i<particleCount; i++){
                GolemWorld.proxy.spawnLapisTrailFX(this.worldObj,
                        this.posX+this.rand.nextGaussian()*2,
                        (this.boundingBox.maxY+this.boundingBox.minY)/2+this.rand.nextGaussian()*3,
                        this.posZ+this.rand.nextGaussian()*2,TRAIL_LIFETIME);
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource src, float dmg){
        /*System.out.println("Stored positions:");
        for(Vec3 pos: lastPositions){
            System.out.println(String.format("  Vec3[%.2f,%.2f,%.2f]", pos.xCoord, pos.yCoord, pos.zCoord));
        }*/
        return super.attackEntityFrom(src, dmg);
    }
}
