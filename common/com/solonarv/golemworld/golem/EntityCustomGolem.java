package com.solonarv.golemworld.golem;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCustomGolem extends EntityIronGolem {

    protected int maxHealth;
    protected String name;

    protected double attackDamageMean, attackDamageStdDev;

    protected ItemStack[] droppedItems;

    protected String texture;

    // Make private attackTimer from superclass visible
    protected int attackTimer;

    public EntityCustomGolem(World world) {
        this(world, 15, "Dirt Golem", 6, 1.2, new ItemStack[] { new ItemStack(
                Block.dirt, 3) });
    }

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
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        // Write fields to NBT
        nbt.setByte("maxhealth", (byte) maxHealth);
        nbt.setString("name", name);
        nbt.setDouble("atkdmgMean", attackDamageMean);
        nbt.setDouble("atkdmgStdDev", attackDamageStdDev);
        // Advanced NBT hackery to shove my ItemStack[] into an NBTTagCompound
        NBTTagCompound dropped = new NBTTagCompound();
        for (int i = 0; i < droppedItems.length; i++) {
            dropped.setCompoundTag(String.valueOf(i),
                    droppedItems[i].writeToNBT(new NBTTagCompound()));
        }
        nbt.setCompoundTag("dropped", dropped);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        maxHealth = nbt.getByte("maxhealth");
        name = nbt.getString("name");
        attackDamageMean = nbt.getDouble("atkdmgMean");
        attackDamageStdDev = nbt.getDouble("atkdmgStdDev");
        NBTTagCompound dropped = nbt.getCompoundTag("dropped");
        ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();
        int i = 0;
        while (true) {
            NBTTagCompound temp = dropped.getCompoundTag(String.valueOf(i++));
            if (temp.hasNoTags()) {
                break;
            }
            droppedStacks.add(ItemStack.loadItemStackFromNBT(temp));
        }
        droppedItems = droppedStacks
                .toArray(new ItemStack[droppedStacks.size()]);
    }

    @Override
    public int getMaxHealth() {
        return maxHealth == 0 ? 10 : maxHealth;
    };

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
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
}
