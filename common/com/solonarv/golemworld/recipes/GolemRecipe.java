package com.solonarv.golemworld.recipes;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.solonarv.golemworld.entity.golem.EntityGolem;

public class GolemRecipe<TypeGolem extends EntityGolem> {
	
	// TODO change to use Block objects instead of GameRegistry.addRecipe type syntax internally
	private Block[] midRow;
	private Block[] botRow;
	private Block shoulderBlock;
	private boolean smart;
	
	/**
	 * Creates a new golem recipe: if blocks in the specified shape are
	 * right-clicked with a Paper of Awakening, a golem of the specified
	 * type will be created.
	 * @param recipe 3x3 array of blocks to use in recipe
	 * @param smart whether or not it's an advanced golem
	 */
	public GolemRecipe(Block shoulder, Block[] mid, Block[] bot, boolean smart){
		this.shoulderBlock=shoulder;
		this.midRow=mid;
		this.botRow=bot;
		this.smart=smart;
	}
	/**
	 * Creates a new golem recipe: if blocks in the specified shape are
	 * right-clicked with a Paper of Awakening, a golem of the specified
	 * type will be created.
	 * @param recipe 3x3 array of blocks to use in recipe
	 */
	public GolemRecipe(Block shoulder, Block[] mid, Block[] bot){
	    this.shoulderBlock=shoulder;
        this.midRow=mid;
        this.botRow=bot;
        this.smart=false;
	}
	
	/**
	 * Simplified constructor for plain golems (made of all same block)
	 * @param golemMat
	 */
	public GolemRecipe(Block golemMat){
	    this.shoulderBlock=null;
	    for(int i=0; i<3; i++){
	            this.midRow[i]=golemMat;
	    }
	    this.botRow[1]=golemMat;
	    this.botRow[0]=this.botRow[2]=null;
	}
	
	/**
	 * Is the target block part of a
	 * valid instantiation of this golem recipe?
	 * @param x x coord of clicked block 
	 * @param y y coord of clicked block
	 * @param z z coord of clicked block
	 * @param world world object to check
	 * @return facing (f coordinate) that the golem should have, or -1 if invalid recipe.
	 */
	public int checkRecipeInWorld(int x, int y, int z, World world){
		int tarBlock=world.getBlockId(x,y,z);
		
		int p=this.checkFromPumpkin(x, y, z, world);
		if(p!=-1) return p;
		
		if(tarBlock==this.shoulderBlock.blockID){
		    p=this.checkFromPumpkin(x+1, y, z, world);
		    if(p!=-1) return p;
		    p=this.checkFromPumpkin(x-1, y, z, world);
		    if(p!=-1) return p;
		    p=this.checkFromPumpkin(x, y, z+1, world);
		    if(p!=-1) return p;
		    p=this.checkFromPumpkin(x, y, z-1, world);
		    if(p!=-1) return p;
		}
		if(tarBlock==this.midRow[1].blockID){
		    p=this.checkFromPumpkin(x, y+1, z, world);
		    if(p!=-1) return p;
		}
		if(tarBlock==this.botRow[1].blockID){
            p=this.checkFromPumpkin(x, y+2, z, world);
            if(p!=-1) return p;
        }
		if(this.midRow[0]==null || tarBlock==this.midRow[0].blockID || this.midRow[2]==null || tarBlock==this.midRow[2].blockID){
		    for(int i=-1; i<2; i+=2){
		        if(this.midRow[1]==null || world.getBlockId(x+i,y,z)==this.midRow[1].blockID){
		            p=this.checkFromPumpkin(x+i, y+1, z, world);
		            if(p!=-1) return p;
		        }
		        if(this.midRow[1]==null || world.getBlockId(x,y,z+i)==this.midRow[1].blockID){
                    p=this.checkFromPumpkin(x, y+1, z+i, world);
                    if(p!=-1) return p;
                }
		    }
		}
		if(this.botRow[0]==null || tarBlock==this.botRow[0].blockID || this.botRow[2]==null || tarBlock==this.botRow[2].blockID){
            for(int i=-1; i<2; i+=2){
                if(this.botRow[1]==null || world.getBlockId(x+i,y,z)==this.botRow[1].blockID){
                    p=this.checkFromPumpkin(x+i, y+2, z, world);
                    if(p!=-1) return p;
                }
                if(this.botRow[1]==null || world.getBlockId(x,y,z+i)==this.botRow[1].blockID){
                    p=this.checkFromPumpkin(x, y+2, z+i, world);
                    if(p!=-1) return p;
                }
            }
        }
		return -1;
	}
	
	/**
	 * Checks whether the given block is the head of a valid instantiation of this golem recipe.
     * @param x x coord of pumpkin 
     * @param y y coord of pumpkin
     * @param z z coord of pumpkin
     * @param world world object to check
     * @return facing (f coordinate) that the golem should have, or -1 if invalid recipe.
	 */
	private int checkFromPumpkin(int x, int y, int z, World world){
	    int tarBlock=world.getBlockId(x,y,z);
        boolean[] flagXp={false,false,false}, flagXn=flagXp, flagZp=flagXp, flagZn=flagZp;
        
        if ( (tarBlock == Block.pumpkin.blockID && !this.smart) || tarBlock == Block.pumpkinLantern.blockID ){
            if(this.shoulderBlock!=null){
                if( world.getBlockId(x+1, y, z) == this.shoulderBlock.blockID
                        && world.getBlockId(x-1, y, z) == this.shoulderBlock.blockID){
                    flagXp[0]=flagXn[0]=true;
                }
                if( world.getBlockId(x, y, z+1) == this.shoulderBlock.blockID
                        && world.getBlockId(x, y, z-1) == this.shoulderBlock.blockID){
                    flagZp[0]=flagZn[0]=true;
                }
            }else{
                flagXp[0]=flagXn[0]=flagZp[0]=flagZn[0]=true;
            }
            for(int i=0; i<3; i++){
                if( (this.midRow[0]==null || world.getBlockId(x-1, y-1, z) == this.midRow[0].blockID) 
                        && (this.midRow[1]==null || world.getBlockId(x, y-1, z) == this.midRow[1].blockID)
                        && (this.midRow[2]==null || world.getBlockId(x+1, y-1, z) == this.midRow[2].blockID)){
                    flagXp[1]=true;
                }
                if( (this.midRow[2]==null || world.getBlockId(x-1, y-1, z) == this.midRow[2].blockID)
                        && (this.midRow[1]==null || world.getBlockId(x, y-1, z) == this.midRow[1].blockID)
                        && (this.midRow[0]==null || world.getBlockId(x+1, y-1, z) == this.midRow[0].blockID)){
                    flagXn[1]=true;
                }
                if( (this.midRow[0]==null || world.getBlockId(x, y-1, z-1) == this.midRow[0].blockID)
                        && (this.midRow[1]==null || world.getBlockId(x, y-1, z) == this.midRow[1].blockID)
                        && (this.midRow[2]==null || world.getBlockId(x, y-1, z+1) == this.midRow[2].blockID)){
                    flagZp[1]=true;
                }
                if( (this.midRow[2]==null || world.getBlockId(x, y-1, z-1) == this.midRow[2].blockID)
                        && (this.midRow[1]==null || world.getBlockId(x, y-1, z) == this.midRow[1].blockID)
                        && (this.midRow[0]==null || world.getBlockId(x, y-1, z+1) == this.midRow[0].blockID)){
                    flagZn[1]=true;
                }
                
                if( (this.botRow[0]==null || world.getBlockId(x-1, y-2, z) == this.botRow[0].blockID)
                        && (this.botRow[1]==null || world.getBlockId(x, y-2, z) == this.botRow[1].blockID)
                        && (this.botRow[2]==null || world.getBlockId(x+1, y-2, z) == this.botRow[2].blockID)){
                    flagXp[2]=true;
                }
                if( (this.botRow[2]==null || world.getBlockId(x-1, y-2, z) == this.botRow[2].blockID)
                        && (this.botRow[1]==null || world.getBlockId(x, y-2, z) == this.botRow[1].blockID)
                        && (this.botRow[0]==null || world.getBlockId(x+1, y-2, z) == this.botRow[0].blockID)){
                    flagXn[2]=true;
                }
                if( (this.botRow[0]==null || world.getBlockId(x, y-2, z-1) == this.botRow[0].blockID)
                        && (this.botRow[1]==null || world.getBlockId(x, y-2, z) == this.botRow[1].blockID)
                        && (this.botRow[2]==null || world.getBlockId(x, y-2, z+1) == this.botRow[2].blockID)){
                    flagZp[2]=true;
                }
                if( (this.botRow[2]==null || world.getBlockId(x, y-2, z-1) == this.botRow[2].blockID)
                        && (this.botRow[1]==null || world.getBlockId(x, y-2, z) == this.botRow[1].blockID)
                        && (this.botRow[0]==null || world.getBlockId(x, y-2, z+1) == this.botRow[0].blockID)){
                    flagZn[2]=true;
                }
            }
        }
        
        if(flagXp[0] && flagXp[1] && flagXp[2]){ return 2; }
        if(flagXn[0] && flagXn[1] && flagXn[2]){ return 0; }
        if(flagZp[0] && flagZp[1] && flagZp[2]){ return 3; }
        if(flagZn[0] && flagZn[1] && flagZn[2]){ return 1; }
        
        return -1;
	}
}
