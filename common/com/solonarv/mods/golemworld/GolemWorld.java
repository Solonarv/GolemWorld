package com.solonarv.mods.golemworld;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.item.ModItems;
import com.solonarv.mods.golemworld.lib.GolemWorldEventHooks;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.localization.Localization;
import com.solonarv.mods.golemworld.potion.PotionFreeze;
import com.solonarv.mods.golemworld.proxy.CommonProxy;
import com.solonarv.mods.golemworld.util.EntityGolemFireball;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * GolemWorld
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
    public static GolemWorld    instance;
    
    /**
     * A proxy that client-/server-only stuff can be delegated to.
     */
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy   proxy;
    
    public static Configuration config;
    
    /**
     * Initialize all our stuff: items, config, golems
     * 
     * @param event an {@link FMLPreInitializationEvent} containing some info
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ModItems.registerItems();
        GolemRegistry.registerGolems();
        EntityRegistry.registerModEntity(EntityGolemFireball.class,
                EntityGolemFireball.class.getName(), 0, this, 40, 1, true);
        try {
            for(Field f: Potion.class.getDeclaredFields()){
                f.setAccessible(true);
                if(f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")){
                    Field modifiers=Field.class.getDeclaredField("modifiers");
                    modifiers.setAccessible(true);
                    modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    Potion[] oldPotionTypes=(Potion[]) f.get(null);
                    Potion[] newPotionTypes=Arrays.copyOf(oldPotionTypes, 256);
                    f.set(null, newPotionTypes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderer();
        PotionFreeze.init();
        
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
}
