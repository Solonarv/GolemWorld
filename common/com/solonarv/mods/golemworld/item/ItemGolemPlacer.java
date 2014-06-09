package com.solonarv.mods.golemworld.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemRegistry;
import com.solonarv.mods.golemworld.lib.Reference;

public class ItemGolemPlacer extends Item{

    public static final String itemName="golemPlacer";
    public static String[] subtypes = {"_empty", "_headed", "_ready"};
    public IIcon[] icons;
    
    public ItemGolemPlacer(){
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    @Override
    public void registerIcons(IIconRegister iconRegister){
        icons = new IIcon[subtypes.length];
        for (int i = 0; i < subtypes.length; i++) {
            icons[i] = iconRegister.registerIcon(Reference.texture(itemName + subtypes[i]));
        }
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack){
        int damage=MathHelper.clamp_int(stack.getItemDamage(), 0, subtypes.length-1);
        return itemName + subtypes[damage];
        
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" }) // Dammit Mojang, y u no generic
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List types){
        types.add(new ItemStack(item, 1, 0));
        types.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    public boolean onItemUse(ItemStack itemStack,
            EntityPlayer entityPlayer, World world, int x, int y, int z, int sideHit, float hitVecX,
            float hitVecY, float hitVecZ) {
        if(world.isRemote) return true;
        if(itemStack.stackSize <= 0) return false;
        if(itemStack.getItemDamage()!=2) return false;
        if(itemStack.hasTagCompound() && itemStack.stackTagCompound.hasKey("GolemName")
                && itemStack.stackTagCompound.getTag("GolemName") instanceof NBTTagString){
            ForgeDirection dir = ForgeDirection.getOrientation(sideHit);
            x+=dir.offsetX;
            y+=dir.offsetY;
            z+=dir.offsetZ;
            EntityCustomGolem theGolem = GolemRegistry.spawnGolem(itemStack.stackTagCompound.getString("GolemName"), world, x, y, z);
            if (theGolem != null) {
                if (entityPlayer == null
                        || !entityPlayer.capabilities.isCreativeMode) {
                    itemStack.stackSize--;
                }
                if (entityPlayer != null){
                    theGolem.setCreator(entityPlayer.getDisplayName());
                    theGolem.setPlayerCreated(true);
                }
            }
            return theGolem!=null;
        } else return false;
    }
}
