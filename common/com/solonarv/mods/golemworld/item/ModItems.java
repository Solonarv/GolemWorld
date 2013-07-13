package com.solonarv.mods.golemworld.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.solonarv.mods.golemworld.lib.ItemIDs;
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
    
    /**
     * The mythic Paper of Awakening; when placed into a pumpkin above a
     * mystical construct, a fearsome creature known as a golem will form, ready
     * to smash your foes into oblivion. This operation permanently destroys the
     * paper used.
     */
    public static Item paperAwaken = new ItemPaperOfAwakening(
                                           ItemIDs.PAPER_OF_AWAKENING)
                                           .setMaxStackSize(64)
                                           .setCreativeTab(CreativeTabs.tabMisc)
                                           .setUnlocalizedName(
                                                   "paperOfAwakening")
                                           .func_111206_d(
                                                   Reference.texture(
                                                           "paperOfAwakening")
                                                           .toString());
    
    /**
     * Register my items with the game and make them craftable
     */
    public static void registerItems() {
        LanguageRegistry.addName(paperAwaken, "Paper of Awakening");
        
        GameRegistry.addRecipe(new ItemStack(paperAwaken), new Object[] {
                "grg", "rpr", "grg", 'g', Item.glowstone, 'r', Item.redstone,
                'p', Item.paper });
        GameRegistry.addRecipe(new ItemStack(paperAwaken), new Object[] {
                "grg", "rpr", "grg", 'g', Item.redstone, 'r', Item.glowstone,
                'p', Item.paper });
        
    }
}
