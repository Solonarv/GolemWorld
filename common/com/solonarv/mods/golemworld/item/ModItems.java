package com.solonarv.mods.golemworld.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
    public static ItemPaperOfAwakening paperOfAwakening = (ItemPaperOfAwakening) new ItemPaperOfAwakening()
    	.setMaxStackSize(64)
    	.setCreativeTab(CreativeTabs.tabMisc)
    	.setUnlocalizedName(ItemPaperOfAwakening.itemName)
    	.setTextureName(Reference.texture(ItemPaperOfAwakening.itemName));
    
    public static ItemGolemPlacer golemPlacer = (ItemGolemPlacer) new ItemGolemPlacer()
        .setMaxStackSize(64)
        .setCreativeTab(CreativeTabs.tabMisc);
    
    /**
     * Register my items with the game and make them craftable
     */
    public static void registerItems() {
    	GameRegistry.registerItem(paperOfAwakening, "paperOfAwakening");
        GameRegistry.addRecipe(new ItemStack(paperOfAwakening, 8), new Object[] {"grg", "rpr", "grg",
            'g', Items.glowstone_dust,
            'r', Items.redstone,
            'p', Items.paper });
        GameRegistry.addRecipe(new ItemStack(paperOfAwakening, 8), new Object[] {"grg", "rpr", "grg",
            'r', Items.glowstone_dust,
            'g', Items.redstone,
            'p', Items.paper });
        
        GameRegistry.registerItem(golemPlacer, "golemPlacer");
        GameRegistry.addRecipe(new ItemStack(golemPlacer), new Object[]{" # ", "0X0", ".P.",
            '#', Blocks.iron_bars,
            '0', Blocks.glass,
            'X', Blocks.tnt,
            '.', Items.redstone,
            'P', Blocks.piston});
        GameRegistry.addShapelessRecipe(new ItemStack(golemPlacer, 1, 1), new ItemStack(golemPlacer, 1, 0), paperOfAwakening, Blocks.lit_pumpkin);
        GameRegistry.addRecipe(GolemPlacerRecipes.getInstance());
        
    }
}
