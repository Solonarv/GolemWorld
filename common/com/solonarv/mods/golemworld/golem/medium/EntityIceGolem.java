package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.potion.PotionFreeze;
import com.solonarv.mods.golemworld.util.ItemHelper;

public class EntityIceGolem extends EntityCustomGolem {
    public static final GolemStats stats = new GolemStats();
    
    public static final int freezeDuration=5 * Reference.MAX_TPS;
    
    static {
        stats.maxHealth = 15;
        stats.attackDamageMean = 8;
        stats.attackDamageStdDev = 3;
        stats.name = "Ice Golem";
        stats.texture = Reference.mobTexture("ice_golem_light");
        stats.droppedItems(new ItemStack(Blocks.ice, 3));
        stats.villageSpawnable = false;
    }
    
    public EntityIceGolem(World world) {
        super(world);
        this.getNavigator().setAvoidsWater(false);
        this.tasks.addTask(1, new EntityAISwimming(this));
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        
        // Transform water blocks below the golem into ice, lava into
        // obsidian/cobble
        int xmin = MathHelper.floor_double(this.posX) - 1, xmax = MathHelper
                .ceiling_double_int(this.posX) + 1;
        int zmin = MathHelper.floor_double(this.posZ) - 1, zmax = MathHelper
                .ceiling_double_int(this.posZ) + 1;
        int y = MathHelper.floor_double(this.posY) - 1;
        for (int x = xmin; x < xmax; x++)
            for (int z = zmin; z < zmax; z++){
            	Block block=this.worldObj.getBlock(x, y, z);
                if (Block.isEqualTo(block, Block.getBlockFromName("water"))
                		|| Block.isEqualTo(block, Block.getBlockFromName("flowing_water"))) {
                    this.worldObj.setBlock(x, y, z, Block.getBlockFromName("ice")); // Water -> Ice
                } else if (Block.isEqualTo(block, Block.getBlockFromName("lava"))
                		|| Block.isEqualTo(block, Block.getBlockFromName("flowing_lava"))) {
                    int meta=this.worldObj.getBlockMetadata(x, y, z);
                    if(meta == 0){ // Source block
                        this.worldObj.setBlock(x, y, z, Block.getBlockFromName("obsidian")); // Lava source -> Obsidian
                    }else if(meta < 8){
                        this.worldObj.setBlock(x, y, z, Block.getBlockFromName("cobblestone")); // Lava flow -> Cobble
                    }
                }
            }
        if (this.rand.nextInt(20) < 3) { // He /may/ extinguish himself
            this.extinguish();
        }
    }
    
    @Override
    public void onDeath(DamageSource src){
        super.onDeath(src);
        int _x = MathHelper.ceiling_double_int(this.posX);
        int _y = MathHelper.ceiling_double_int(this.posY);
        int _z = MathHelper.ceiling_double_int(this.posZ);
        for(int x=_x-1; x<=_x; x++) for(int y=_y-1; y<=_y; y++) for(int z=_z-1; z<=_z; z++){
            Block blockAtFeet=this.worldObj.getBlock(x, y, z);
            if(blockAtFeet==null || blockAtFeet.isAir(this.worldObj, x, y, z)){
                this.worldObj.setBlock(x, y, z, Block.getBlockFromName("water"));
                this.worldObj.scheduleBlockUpdate(x, y, z, Block.getBlockFromName("water"), 1);
            }
        }
    }
    
    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean ret=super.attackEntityAsMob(par1Entity);
        if(par1Entity instanceof EntityLivingBase && ret){
            EntityLivingBase living=(EntityLivingBase) par1Entity;
            // Freeze da target
            if(this.rand.nextInt(20)<7){
                living.addPotionEffect(new PotionEffect(PotionFreeze.instance.id, freezeDuration));
            }
        }
        return ret;
    }
}
