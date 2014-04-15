package com.solonarv.mods.golemworld.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

public class InternalPlayer extends EntityPlayer {
    
	protected static GameProfile dummyGameProfile = new GameProfile("GW_DUMMY", "GW_DUMMY");
	
    public InternalPlayer(World w, double x, double y, double z) {
        super(w, dummyGameProfile);
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        
    }
    
    @Override
    public boolean canCommandSenderUseCommand(int i, String s) {
        return false;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return null;
    }

	@Override
	public void addChatMessage(IChatComponent var1) {
		
	}
    
}
