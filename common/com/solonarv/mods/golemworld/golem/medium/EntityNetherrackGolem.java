package com.solonarv.mods.golemworld.golem.medium;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.EntityGolemFireball;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityNetherrackGolem extends EntityCustomGolem implements IRangedAttackMob {
    public EntityNetherrackGolem(World world) {
        super(world);
        this.tasks.taskEntries.clear();
        this.tasks.addTask(1, new EntityAIArrowAttack(this, this.getAIMoveSpeed(), 60, 64));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, .9d, true));
        this.tasks.addTask(3, new EntityAILookAtVillager(this));
        this.tasks.addTask(4, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }
    
    public static final GolemStats stats                 = new GolemStats();
    static {
        stats.maxHealth = 30;
        stats.attackDamageMean = 0;
        stats.attackDamageStdDev = 0;
        stats.name = "Netherrack Golem";
        stats.texture = Reference.mobTexture("netherrack_golem");
        stats.droppedItems(new ItemStack(Blocks.netherrack, 4));
    }
    
    public static final Map<Item,Integer> fuelValues = new HashMap<Item, Integer>();
    static {
    	fuelValues.put(Items.fire_charge, 16);
    	fuelValues.put(Items.coal, 4);
    	fuelValues.put(Items.blaze_powder, 6);
    	fuelValues.put(Items.blaze_rod, 12);
    }
    
    private int fireballChargesReady = 0;
    private int fireballRechargeTimer = 60;
    private int fireballChargesStored = 16;
    private boolean burning;
    
    @Override
    public boolean attackEntityAsMob(Entity e) {
        if (this.fireballChargesReady<=0) {
            return super.attackEntityAsMob(e);
        }
        else return false;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.fireballRechargeTimer > 0) {
            this.fireballRechargeTimer--;
        } else if (this.fireballChargesReady < 8 && this.fireballChargesStored > 0) {
            this.fireballChargesReady++;
            this.fireballChargesStored--;
            this.fireballRechargeTimer = this.rand.nextInt(20)-this.rand.nextInt(20) + 40;
        }
        
        if(!this.worldObj.isRemote){
        	@SuppressWarnings("unchecked") // GGWP for clean Vanilla code
			List<Entity> intersectingItems = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox);
	        for(Entity e: intersectingItems){
	        	EntityItem theItem = (EntityItem) e; // No need to check, since we ask for only EntityItem's
	        	ItemStack istack = theItem.getEntityItem();
	        	if(fuelValues.containsKey(istack.getItem()) && istack.stackSize>0){
	        		this.fireballChargesStored+=istack.stackSize * fuelValues.get(istack.getItem());
	        		theItem.setDead();
	        	}
	        }
        }
        //this.setPlayerCreated(false);
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("fireballChargesReady", this.fireballChargesReady);
        nbt.setInteger("fireballRechargeTimer", this.fireballRechargeTimer);
        nbt.setInteger("fireballChargesStored", this.fireballChargesStored);
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.fireballChargesReady = nbt.getInteger("fireballChargesReady");
        this.fireballRechargeTimer = nbt.getInteger("fireballRechargeTimer");
        this.fireballChargesStored = nbt.getInteger("fireballChargesStored");
    }
    
    protected void dealFireDamage(int i){}

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distSq) {
        double x = target.posX - this.posX;
        double y = target.posY - this.posY;
        double z = target.posZ - this.posZ;
        if (this.fireballChargesReady > 0){
        	for (int i = 0; i < 3; i++) {
	            EntityGolemFireball egf = new EntityGolemFireball(this.worldObj,
	                    this.posX + this.rand.nextGaussian() * .1D,
	                    this.posY + 2.5D + this.rand.nextGaussian() * .1D,
	                    this.posZ + this.rand.nextGaussian() * .1D,
	                    x + this.rand.nextGaussian() * distSq * 5D,
	                    y + this.rand.nextGaussian() * distSq * 5D - 2D,
	                    z + this.rand.nextGaussian() * distSq * 5D);
	            egf.shootingEntity=this;
	            this.worldObj.spawnEntityInWorld(egf);
        	}
        	this.fireballChargesReady--;
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource src, float dmg){
        // Make this golem immune to any kind of fire
        // Also, he reignites on burn tick, so he'll burn forever omghax!
        if(src==DamageSource.inFire || src==DamageSource.onFire || src==DamageSource.lava){
            this.setFire(10); // Will burn for 1 day straight
            return false;
        }else{
            return super.attackEntityFrom(src, dmg);
        }
    }
    
    // DEBUG
    @Override
    public boolean interact(EntityPlayer player){
    	if(!this.worldObj.isRemote){
    			player.addChatMessage(new ChatComponentText("Current fuel value (stored,ready): " + this.fireballChargesStored + ", "+ this.fireballChargesReady));
    	}
    	return true;
    }
}
