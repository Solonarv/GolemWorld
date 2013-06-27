package com.solonarv.golemworld.golem;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GolemFactory {

    protected int maxHealth;
    protected String name;

    protected double attackDamageMean, attackDamageStdDev;

    protected ItemStack[] droppedItems = new ItemStack[16];

    protected String texture;

    public GolemFactory(int maxHealth, String name,
            double attackDamageMean, double attackDamageStdDev,
            ItemStack[] droppedItems, String texture) {
        this.maxHealth = maxHealth;
        this.name = name;
        this.attackDamageMean = attackDamageMean;
        this.attackDamageStdDev = attackDamageStdDev;
        this.droppedItems = droppedItems;
        this.texture = texture;
    }

    public EntityCustomGolem make(World world, int x, int y, int z) {
        EntityCustomGolem g = new EntityCustomGolem(world, maxHealth, name,
                attackDamageMean, attackDamageStdDev, droppedItems);
        g.rotationYawHead = g.rotationYaw;
        g.renderYawOffset = g.rotationYaw;
        g.initCreature();
        g.setPosition(x, y, z);
        world.spawnEntityInWorld(g);
        return g;
    }

    public boolean isSmart() {
        return false;
    }
}
