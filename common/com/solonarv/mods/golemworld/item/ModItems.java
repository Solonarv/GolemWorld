package com.solonarv.mods.golemworld.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.solonarv.mods.golemworld.lib.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Proxy of sorts that handles all the items I need to create.
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModItems {
    
    /**
     * The mythic Paper of Awakening; when placed into a pumpkin above a
     * mystical construct, a fearsome creature known as a golem will form, ready
     * to smash your foes into oblivion. This operation permanently destroys the
     * paper used.
     */
    public static ItemGolemWorldUniversal golemWorldUniversal = (ItemGolemWorldUniversal) new ItemGolemWorldUniversal().setMaxStackSize(64)
            .setCreativeTab(CreativeTabs.tabMisc).setUnlocalizedName("paperOfAwakening").setTextureName(Reference.texture("paperOfAwakening").toString());
    
    /**
     * Register my items with the game and make them craftable
     */
    public static void registerItems() {
    	GameRegistry.registerItem(golemWorldUniversal, "itemGolemworldUniversal");
        GameRegistry.addRecipe(golemWorldUniversal.stack(8, 0),new Object[] {"grg", "rpr", "grg",
            'g', Items.glowstone_dust,
            'r', Items.redstone,
            'p', Items.paper });
        GameRegistry.addRecipe(golemWorldUniversal.stack(8, 0),new Object[] {"grg", "rpr", "grg",
            'r', Items.glowstone_dust,
            'g', Items.redstone,
            'p', Items.paper });
        
    }
}
