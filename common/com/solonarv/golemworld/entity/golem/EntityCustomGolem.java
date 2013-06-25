package com.solonarv.golemworld.entity.golem;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.golemworld.GolemWorld;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityCustomGolem extends EntityIronGolem {

    protected int maxHealth;
    protected String name;

    protected double attackDamageMean, attackDamageStdDev;

    protected ItemStack[] droppedItems = new ItemStack[16];

    protected String texture;

    // Make private attackTimer from superclass visible
    protected int attackTimer;

    public EntityCustomGolem(World world, int maxHealth, String name,
            double attackDamageMean, double attackDamageStdDev,
            ItemStack[] droppedItems) {
        super(world);
        func_94058_c(name); // Displayed Nametag
        texture = texture == null ? "/mob/villager_golem.png" : texture;

        this.maxHealth = maxHealth;
        this.name = name;
        this.attackDamageMean = attackDamageMean;
        this.attackDamageStdDev = attackDamageStdDev;
        this.droppedItems = droppedItems;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth == 0 ? 10 : maxHealth;
    };

    // Final methods, those are common to _all_ golems
    @Override
    public final boolean attackEntityAsMob(Entity par1Entity) {
        attackTimer = 10;
        worldObj.setEntityState(this, (byte) 4);
        int dmg = getAttackStrength();
        boolean flag = par1Entity.attackEntityFrom(
                DamageSource.causeMobDamage(this), dmg);

        if (EntityPlayer.class.isAssignableFrom(par1Entity.getClass())) {
            EntityPlayer p = (EntityPlayer) par1Entity;
            p.addChatMessage("Attacked by " + getName() + " for "
                    + String.valueOf(dmg) + " damage: "
                    + (flag ? "success" : "fail"));
        }

        if (flag) {
            par1Entity.motionY += 0.4000000059604645D;
        }

        playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    public final int getAttackStrength() {
        return MathHelper.floor_double(attackDamageMean + rand.nextGaussian()
                * attackDamageStdDev);
    };

    public static boolean isSmart() {
        return false;
    }

    public String getName() {
        return name;
    }

    public static void registerMe() {
        EntityRegistry.registerModEntity(EntityCustomGolem.class,
                "Custom Golem", 1, GolemWorld.instance, 15, 1, true);
        GolemRegistry.registerGolem(new GolemFactory(15, "Dirt Golem", 6, 1.2,
                new ItemStack[] { new ItemStack(Block.dirt, 3) }, null),
                Block.dirt, GolemShapes.DEFAULT);
    }
}
