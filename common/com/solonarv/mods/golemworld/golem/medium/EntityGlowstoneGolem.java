package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.block.BlockEnhancedAir;
import com.solonarv.mods.golemworld.block.ModBlocks;
import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityGlowstoneGolem extends EntityCustomGolem {
    
    public static final GolemStats stats = new GolemStats();
    
    static {
        stats.maxHealth = 15;
        stats.attackDamageMean = 6;
        stats.attackDamageStdDev = 1;
        stats.name = "Glowstone Golem";
        stats.texture = Reference.mobTexture("glowstone_golem");
        stats.droppedItems(new ItemStack(Items.glowstone_dust, 3));
    }
    
    public int lastX, lastY, lastZ;
    
    public EntityGlowstoneGolem(World world) {
        super(world);
    }
    
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        int mx=MathHelper.floor_double(this.posX);
        int my=MathHelper.floor_double(this.posY);
        int mz=MathHelper.floor_double(this.posZ);
        if(mx!=this.lastX || my!=this.lastY || mz!=this.lastZ){
            this.lastX=mx; this.lastY=my; this.lastZ=mz;
            for(int x=mx; x<mx+2; x++) for(int y=my; y<my+3; y++) for(int z=mz; z<mz+2; z++)
                if(Block.isEqualTo(this.worldObj.getBlock(x, y, z), ModBlocks.enhancedAir)){
                    this.worldObj.setBlockMetadataWithNotify(x, y, z, this.worldObj.getBlockMetadata(x, y, z) | BlockEnhancedAir.LIGHT, 3);
                }else if(this.worldObj.isAirBlock(x, y, z)){
                    this.worldObj.setBlock(x, y, z, ModBlocks.enhancedAir, BlockEnhancedAir.LIGHT, 3);
                }
        }
    }
    
    @Override
    public void onDeath(DamageSource dmg){
        super.onDeath(dmg);
        int mx=MathHelper.floor_double(this.posX);
        int my=MathHelper.floor_double(this.posY);
        int mz=MathHelper.floor_double(this.posZ);
        for(int x=mx; x<mx+2; x++) for(int y=my; y<my+3; y++) for(int z=mz; z<mz+2; z++)
            if(Block.isEqualTo(this.worldObj.getBlock(x, y, z), ModBlocks.enhancedAir)){
                int meta=this.worldObj.getBlockMetadata(x, y, z);
                if(meta == BlockEnhancedAir.LIGHT){
                    this.worldObj.setBlockToAir(x, y, z);
                }else{
                    this.worldObj.setBlockMetadataWithNotify(x, y, z, meta & ~BlockEnhancedAir.LIGHT, 3);
                }
            }
    }
}
