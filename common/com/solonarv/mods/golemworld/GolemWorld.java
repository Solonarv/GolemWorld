package com.solonarv.mods.golemworld;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.item.ModItems;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

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
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderer();
    }
    
    /**
     * Interactions with other mods, eventually
     * 
     * @param event an {@link FMLPostInitializationEvent} containing some info
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    /**
     * Listens to an {@link EntityJoinWorldEvent} and responds depending on the
     * type of entity that spawned. This code effectively disables the vanilla
     * iron golem by replacing it with one of our golems if it spawns naturally,
     * i.e. in a village, or dropping the blocks used to build it if it was
     * built manually. Configurable.
     * 
     * @param e the {@link EntityJoinWorldEvent}
     */
    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        // This code effectively disables the vanilla iron golem by replacing it
        // with one of our golems if it spawns naturally, i.e. in a village, or
        // dropping the blocks used to build it if it was built manually.
        // Configurable.
        if (EntityIronGolem.class.isInstance(e.entity)) {
            EntityIronGolem theGolem = (EntityIronGolem) e.entity;
            if (!theGolem.isPlayerCreated()) {
                if (config.get("Vanilla", "replaceVillageSpawns", true)
                        .getBoolean(true)) {
                    int x, y, z;
                    x = MathHelper.floor_double(theGolem.posX);
                    y = MathHelper.floor_double(theGolem.posY);
                    z = MathHelper.floor_double(theGolem.posZ);
                    e.world.removeEntity(theGolem);
                    GolemRegistry.spawnRandomGolem(e.world, x, y, z);
                }
            } else if (config.get("Vanilla", "deleteConstructedGolems", false)
                    .getBoolean(false)) {
                theGolem.entityDropItem(new ItemStack(Block.blockIron, 3), 0F);
                theGolem.entityDropItem(new ItemStack(Block.pumpkin), 0F);
                theGolem.setDead();
            }
        }
    }
}
