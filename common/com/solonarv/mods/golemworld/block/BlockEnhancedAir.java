package com.solonarv.mods.golemworld.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.medium.EntityGlowstoneGolem;
import com.solonarv.mods.golemworld.golem.medium.EntityRedstoneGolem;

public class BlockEnhancedAir extends BlockContainer {
    
    public static final int LIGHT = 1, REDSTONE = 2;
    
    public BlockEnhancedAir() {
        super(Material.air);
        this.setBlockBounds(0, 0, 0, 0, 0, 0);
    }
    
    @Override
    public boolean isAir(IBlockAccess world, int x, int y, int z){
        return true;
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return (world.getBlockMetadata(x, y, z) & 1) * 15;
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side){
        return (world.getBlockMetadata(x, y, z) & 2) * 15;
    }
    
    @Override
    public boolean canProvidePower(){
        return true;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z){
        return null;
    }
    
    @Override
    public boolean isOpaqueCube(){
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }
    
    @Override
    public int getRenderType(){
        return -1;
    }
    
    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side){
        return false;
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand){
        this.recheckBlockState(world, x, y, z);
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor){
        this.recheckBlockState(world, x, y, z);
    }
    
    @SuppressWarnings("rawtypes")
	public void recheckBlockState(World world, int x, int y, int z){
        AxisAlignedBB myAABB=AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1);
        List collidingGlowstoneGolems=world.getEntitiesWithinAABB(EntityGlowstoneGolem.class, myAABB);
        List collidingRedstoneGolems=world.getEntitiesWithinAABB(EntityRedstoneGolem.class, myAABB);
        if(collidingGlowstoneGolems.isEmpty() && collidingRedstoneGolems.isEmpty()){
            world.setBlock(x, y, z, Block.getBlockFromName("air"));
        }else{
            world.setBlockMetadataWithNotify(x, y, z, (collidingGlowstoneGolems.isEmpty()?0:LIGHT) | (collidingRedstoneGolems.isEmpty()?0:REDSTONE), 3);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int x) {
        return new TileEntityTicker(10);
    }
}
