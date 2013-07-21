package com.solonarv.mods.golemworld.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.lib.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Proxy of sorts that handles all the items I need to create.
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModItems {
    
    public static final int ItemGWId = GolemWorld.config.getItem("GolemWorldUniversal", 5000).getInt();
    
    /**
     * The mythic Paper of Awakening; when placed into a pumpkin above a
     * mystical construct, a fearsome creature known as a golem will form, ready
     * to smash your foes into oblivion. This operation permanently destroys the
     * paper used.
     */
    public static ItemGolemWorldUniversal golemWorldUniversal = (ItemGolemWorldUniversal) new ItemGolemWorldUniversal(ItemGWId).setMaxStackSize(64)
            .setCreativeTab(CreativeTabs.tabMisc).setUnlocalizedName("paperOfAwakening").func_111206_d(Reference.texture("paperOfAwakening").toString());
    
    /**
     * Register my items with the game and make them craftable
     */
    public static void registerItems() {
        LanguageRegistry.addName(golemWorldUniversal, "Paper of Awakening");
        
        GameRegistry.addRecipe(golemWorldUniversal.stack(0),new Object[] {"grg", "rpr", "grg",
            'g', Item.glowstone,
            'r', Item.redstone,
            'p', Item.paper });
        GameRegistry.addRecipe(golemWorldUniversal.stack(0),new Object[] {"grg", "rpr", "grg",
            'r', Item.glowstone,
            'g', Item.redstone,
            'p', Item.paper });
        
    }
}
