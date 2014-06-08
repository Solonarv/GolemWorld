package com.solonarv.mods.golemworld.golem;

import com.solonarv.mods.golemworld.util.BlockRef;

public class ExactShapeMatcher implements IShapeMatcher {
	
	public final BlockRef part_lshoulder, part_rshoulder, part_upperbody, part_larm, part_rarm, part_lowerbody, part_lleg, part_rleg;
	
	public ExactShapeMatcher(BlockRef lshoulder,
			BlockRef rshoulder, BlockRef upperbody, BlockRef larm,
			BlockRef rarm, BlockRef lowerbody, BlockRef lleg, BlockRef rleg){
		this.part_lshoulder=lshoulder;
		this.part_rshoulder=rshoulder;
		this.part_upperbody=upperbody;
		this.part_larm=larm;
		this.part_rarm=rarm;
		this.part_lowerbody=lowerbody;
		this.part_lleg=lleg;
		this.part_rleg=rleg;
	}
	
	@Override
	public boolean isMatched(BlockRef lshoulder,
			BlockRef rshoulder, BlockRef upperbody, BlockRef larm,
			BlockRef rarm, BlockRef lowerbody, BlockRef lleg, BlockRef rleg) {
		return (part_lshoulder==null || part_lshoulder.matches(lshoulder)) &&
				(part_rshoulder==null || part_rshoulder.matches(rshoulder)) &&
				(part_upperbody==null || part_upperbody.matches(upperbody)) &&
				(part_larm==null || part_larm.matches(larm)) &&
				(part_rarm==null || part_rarm.matches(rarm)) &&
				(part_lowerbody==null || part_lowerbody.matches(lowerbody)) &&
				(part_lleg==null || part_lleg.matches(lleg)) &&
				(part_rleg==null || part_rleg.matches(rleg));
	}

	@Override
	public boolean[][] removeWhich() {
		return new boolean[][]{ 
				 {part_lshoulder != null,                         part_rshoulder != null},
				 {part_larm != null,      part_upperbody != null, part_rarm != null},
				 {part_lleg != null,      part_lowerbody != null, part_rleg != null}
			};
	}

}
