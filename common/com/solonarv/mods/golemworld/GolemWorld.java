package com.solonarv.mods.golemworld;

import ichun.common.core.config.Config;
import ichun.common.core.config.ConfigHandler;
import ichun.common.core.config.IConfigUser;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;

import com.solonarv.mods.golemworld.block.ModBlocks;
import com.solonarv.mods.golemworld.block.TileEntityTicker;
import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.item.ModItems;
import com.solonarv.mods.golemworld.lib.GolemWorldEventHooks;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.localization.Localization;
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
public class GolemWorld implements IConfigUser{
    
    @Instance(Reference.MOD_ID)
    public static GolemWorld    instance;
    
    public static Config config;
    
    public static Logger logger = LogManager.getLogger("GolemWorld");

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy   proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = ConfigHandler.createConfig(event.getSuggestedConfigurationFile(), Reference.MOD_ID, "GolemWorld", logger, instance);
        initConfig();
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
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
    
    @Deprecated // TODO update to 1.7
    public void fixPotionArray() {
        try {
            Field f = ReflectionHelper.getFieldByNames(Potion.class, "potionTypes", "field_76425_a");
            ReflectionHelper.makeNotFinal(f);
            Potion[] oldPotionTypes=(Potion[]) f.get(null);
            Potion[] newPotionTypes=Arrays.copyOf(oldPotionTypes, 256);
            f.set(null, newPotionTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onConfigChange(Config cfg, Property prop) {
        return false;
    }

    public static void initConfig() {
        
        // Programming with sed.
        //
        // cd com/solonarv/golemworld;
        // ls golem/medium/ \
        //   | sed s/.java// \
        //   | sed s/Entity// \
        //   | sed 's/^.*/config.createIntBoolProperty("village&", "Spawn & golems in villages", "", true, false, true);\nconfig.createIntBoolProperty("build&", "Make & golems buildable", "", true, false, true);/'
        //
        // Fixed strings manually.
        // I'm aware this could be improved.
        config.setCurrentCategory("golemRegistry", "Golem Registry", "Which golems can be built and which can be spawned in villages.\n");
        config.createIntBoolProperty("villageClayGolem", "Spawn Clay golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildClayGolem", "Make Clay golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageDiamondGolem", "Spawn Diamond golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildDiamondGolem", "Make Diamond golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageDirtGolem", "Spawn Dirt golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildDirtGolem", "Make Dirt golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageEmeraldGolem", "Spawn Emerald golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildEmeraldGolem", "Make Emerald golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageHardenedClayGolem", "Spawn Hardened Clay golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildHardenedClayGolem", "Make Hardened Clay golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageIronGolem", "Spawn Iron golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildIronGolem", "Make Iron golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageQuartzGolem", "Spawn Quartz golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildQuartzGolem", "Make Quartz golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageSandstoneGolem", "Spawn Sandstone golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildSandstoneGolem", "Make Sandstone golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageStoneGolem", "Spawn Stone golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildStoneGolem", "Make Stone golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageGlassGolem", "Spawn Glass golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildGlassGolem", "Make Glass golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageGlowstoneGolem", "Spawn Glowstone golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildGlowstoneGolem", "Make Glowstone golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageGoldGolem", "Spawn Gold golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildGoldGolem", "Make Gold golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageIceGolem", "Spawn Ice golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildIceGolem", "Make Ice golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageLapisGolem", "Spawn Lapis golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildLapisGolem", "Make Lapis golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageNetherrackGolem", "Spawn Netherrack golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildNetherrackGolem", "Make Netherrack golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageObsidianGolem", "Spawn Obsidian golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildObsidianGolem", "Make Obsidian golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageQuartzGolem", "Spawn Quartz golems in villages", "", true, false, true);
        config.createIntBoolProperty("buildQuartzGolem", "Make Quartz golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageRedstoneGolem", "Spawn Redstone golems in villages", "", true, false, false);
        config.createIntBoolProperty("buildRedstoneGolem", "Make Redstone golems buildable", "", true, false, true);
        config.createIntBoolProperty("villageSwitchableGolem", "Spawn Switchable golems in villages", "", true, false, false);
        config.createIntBoolProperty("buildSwitchableGolem", "Make Switchable golems buildable", "", true, false, true);


    }
}
