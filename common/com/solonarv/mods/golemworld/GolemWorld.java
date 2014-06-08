package com.solonarv.mods.golemworld;

import java.lang.reflect.Field;
import java.util.Arrays;

import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;

import com.solonarv.mods.golemworld.block.ModBlocks;
import com.solonarv.mods.golemworld.block.TileEntityTicker;
import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.item.ModItems;
import com.solonarv.mods.golemworld.lib.GolemWorldEventHooks;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.localization.Localization;
import com.solonarv.mods.golemworld.potion.PotionFreeze;
import com.solonarv.mods.golemworld.proxy.CommonProxy;
import com.solonarv.mods.golemworld.util.EntityGolemFireball;
import com.solonarv.mods.golemworld.util.ReflectionHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * GolemWorld
 * 
 * GolemWorld
 * 
 * @author solonarv
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:iChunUtil@[3.0.0,)")
public class GolemWorld {
    
    @Instance(Reference.MOD_ID)
    public static GolemWorld    instance;
    
    /**
     * A proxy that client-/server-only stuff can be delegated to.
     */
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy   proxy;
    
    /**
     * Initialize all our stuff: items, blocks, golems
     * 
     * @param event an {@link FMLPreInitializationEvent} containing some info
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.registerItems();
        ModBlocks.init();
        GolemRegistry.registerGolems();
        EntityRegistry.registerModEntity(EntityGolemFireball.class,
                EntityGolemFireball.class.getName(), 0, this, 40, 1, true);
        GameRegistry.registerTileEntity(TileEntityTicker.class, "TileEntityTicker");
        //fixPotionArray(); // Disabled until tested/adapted to 1.7
    }

    
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderer();
        //PotionFreeze.init();
        
        MinecraftForge.EVENT_BUS.register(GolemWorldEventHooks.instance());
       Localization.registerNames();
    }
    
    /**
     * Interactions with other mods, eventually
     * 
     * @param event an {@link FMLPostInitializationEvent} containing some info
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
    
    /**
     * Make potion array bigger so so we can have more then 32 different potion effects
     */
    @Deprecated // TODO update to 1.7
    public void fixPotionArray() {
        try {
            Field f = ReflectionHelper.getFieldByNames(Potion.class, "potionTypes", "field_76425_a");
            Field modifiers=Field.class.getDeclaredField("modifiers");
            ReflectionHelper.makeNotFinal(f);
            Potion[] oldPotionTypes=(Potion[]) f.get(null);
            Potion[] newPotionTypes=Arrays.copyOf(oldPotionTypes, 256);
            f.set(null, newPotionTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
