package com.solonarv.mods.golemworld.golem;

import com.solonarv.mods.golemworld.util.BlockRef;

public interface IShapeMatcher {
	
	/**
	 * Check whether the blocks passed constitute a valid shape.
	 */
	public boolean isMatched(BlockRef lshoulder, BlockRef rshoulder, BlockRef upperbody,
			BlockRef larm, BlockRef rarm, BlockRef lowerbody, BlockRef lleg, BlockRef rleg);
	
	/**
	 * Which blocks to remove when spawning a golem.
	 * @return a boolean[][] of the form
	 * <br>{ {a,    c},
	 * <br>  {d, e, f},
	 * <br>  {g, h, i} }
	 */
	public boolean[][] removeWhich();
}
