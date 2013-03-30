package com.solonarv.golemworld;

import com.solonarv.golemworld.lib.Reference;
import com.solonarv.golemworld.lib.Config;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.NetworkMod;


/**
 * Golem-World
 * 
 * GolemWorld
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class GolemWorld {
	
	@Instance(Reference.MOD_ID)
	public static GolemWorld instance;
}
