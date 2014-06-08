package com.solonarv.mods.golemworld.net;

import ichun.common.core.network.AbstractPacket;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.solonarv.mods.golemworld.golem.medium.EntityNetherrackGolem;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;

public class PacketNetherrackGolemFuel extends AbstractPacket {
	
	public int fireballsReady, fireballsStored, refillCD;
	
	public int golemID;
	//public UUID golemUUID;
	
	public PacketNetherrackGolemFuel(){}
	
	public PacketNetherrackGolemFuel(int fireballsReady, int fireballsStored, int refillCD, int golemID){
		this.fireballsReady=fireballsReady;
		this.fireballsStored=fireballsStored;
		this.refillCD=refillCD;
		this.golemID=golemID;
		//this.golemUUID=golemUUID;
	}
	
	public PacketNetherrackGolemFuel(EntityNetherrackGolem theGolem){
		this(theGolem.fireballChargesReady, theGolem.fireballChargesStored, theGolem.fireballRechargeTimer, theGolem.getEntityId());
	}
	
	@Override
	public void writeTo(ByteBuf buf, Side side) {
		buf.writeInt(this.golemID);
		//buf.writeLong(this.golemUUID.getMostSignificantBits());
		//buf.writeLong(this.golemUUID.getLeastSignificantBits());
		buf.writeInt(this.fireballsReady);
		buf.writeInt(this.fireballsStored);
		buf.writeInt(this.refillCD);
	}

	@Override
	public void readFrom(ByteBuf buf, Side side) {
		this.golemID=buf.readInt();
		//long uuidMost=buf.readLong(), uuidLeast=buf.readLong();
		//this.golemUUID=new UUID(uuidMost, uuidLeast);
		this.fireballsReady=buf.readInt();
		this.fireballsStored=buf.readInt();
		this.refillCD=buf.readInt();
	}

	@Override
	public void execute(Side side, EntityPlayer player) {
		if(side.isClient() && player!=null){
			Entity e=player.worldObj.getEntityByID(this.golemID);
			
			if(e!=null && e instanceof EntityNetherrackGolem){
				EntityNetherrackGolem theGolem=(EntityNetherrackGolem) e;
				System.out.println("Found golem to execute packet on");
				theGolem.fireballChargesReady=this.fireballsReady;
				theGolem.fireballChargesStored=this.fireballsStored;
				theGolem.fireballRechargeTimer=this.refillCD;
			}
		}
	}

}
