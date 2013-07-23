package com.solonarv.mods.golemworld.golem.medium;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;

public class EntityIceGolem extends EntityCustomGolem {
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 20;
        stats.attackDamageMean = 10f;
        stats.attackDamageStdDev = 3.5f;
        stats.name = "Ice Golem";
        stats.texture = Reference.mobTexture("ice_golem_light");
        stats.droppedItems(new ItemStack(Block.ice, 3));
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
            for (int z = zmin; z < zmax; z++)
                if (this.worldObj.getBlockId(x, y, z) == Block.waterMoving.blockID
                        || this.worldObj.getBlockId(x, y, z) == Block.waterStill.blockID) {
                    this.worldObj.setBlock(x, y, z, Block.ice.blockID); // Water
                                                                        // ->
                                                                        // Ice
                } else if (this.worldObj.getBlockId(x, y, z) == Block.lavaStill.blockID) {
                    this.worldObj.setBlock(x, y, z, Block.obsidian.blockID); // Lava
                                                                             // source
                                                                             // ->
                                                                             // Obsidian
                } else if (this.worldObj.getBlockId(x, y, z) == Block.lavaMoving.blockID) {
                    this.worldObj.setBlock(x, y, z, Block.cobblestone.blockID); // Lava
                                                                                // flow
                                                                                // ->
                                                                                // Cobble
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
            Block blockAtFeet=Block.blocksList[this.worldObj.getBlockId(x, y, z)];
            if(blockAtFeet==null || blockAtFeet.isAirBlock(this.worldObj, x, y, z)){
                this.worldObj.setBlock(x, y, z, Block.waterStill.blockID);
                this.worldObj.scheduleBlockUpdate(x, y, z, Block.waterStill.blockID, 1);
            }
        }
    }
}
