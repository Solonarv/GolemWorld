package com.solonarv.mods.golemworld.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.potion.DamageSourceShatter;
import com.solonarv.mods.golemworld.potion.PotionFreeze;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GolemWorldEventHooks {
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event){
    	/*
        if(event.entityLiving.isPotionActive(PotionFreeze.instance)){
            if(event.entityLiving.getActivePotionEffect(PotionFreeze.instance).getDuration() <= 0){
                event.entityLiving.removePotionEffect(PotionFreeze.instance.id);
                return;
            }
            event.entityLiving.motionX=0;
            event.entityLiving.motionY=0;
            event.entityLiving.motionZ=0;
        }   /**/
    }
    
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
    	/*
        if(event.entityLiving.isPotionActive(PotionFreeze.instance) && !(event.source instanceof DamageSourceShatter)){
            event.entityLiving.attackEntityFrom(DamageSourceShatter.instance(), Math.max((float) (3 * event.ammount), 6));
            event.entityLiving.removePotionEffect(PotionFreeze.instance.id);
        } else if(event.source.getSourceOfDamage()!=null
                && event.source.getSourceOfDamage() instanceof EntityLivingBase
                && ((EntityLivingBase)event.source.getSourceOfDamage()).isPotionActive(PotionFreeze.instance)){
            event.setCanceled(true);
        } /**/
    }
    
    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event){
    	/*
        if(event.entityLiving.isPotionActive(PotionFreeze.instance)){
            event.setCanceled(true);
        }  /**/
    }
    
    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent event){
    	/*
        if(event.entityLiving.isPotionActive(PotionFreeze.instance)){
            event.setCanceled(true);
        }  /**/
    }
    
    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event){
    	/*
        if(event.entityLiving.isPotionActive(PotionFreeze.instance)){
            event.setCanceled(true);
        } /**/
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
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        // This code effectively disables the vanilla iron golem by replacing it
        // with one of our golems if it spawns naturally, i.e. in a village, or
        // dropping the blocks used to build it if it was built manually.
        // Configurable.
        if (!e.world.isRemote && e.entity.getClass() == EntityIronGolem.class) {
            EntityIronGolem theGolem = (EntityIronGolem) e.entity;
            theGolem.worldObj.removeEntity(theGolem);
            if(theGolem.isPlayerCreated()){
                GolemRegistry.spawnGolem("iron golem", theGolem.worldObj, theGolem.posX, theGolem.posY, theGolem.posZ).setCreator(theGolem.isPlayerCreated()?"$unknown$":null);
            }else{
                GolemRegistry.spawnRandomVillageGolem(theGolem.worldObj, theGolem.posX, theGolem.posY, theGolem.posZ);
            }
        }
    }

    private static GolemWorldEventHooks instance;
    
    public static GolemWorldEventHooks instance() {
        if(instance==null)
            instance=new GolemWorldEventHooks();
        return instance;
    }
}
