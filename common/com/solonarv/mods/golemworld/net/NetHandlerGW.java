package com.solonarv.mods.golemworld.net;

import ichun.common.core.network.ChannelHandler;

import java.util.EnumMap;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.relauncher.Side;

public class NetHandlerGW {

	public static EnumMap<Side, FMLEmbeddedChannel> channels;
	
	public static void registerToNetworkHandler(){
		channels=ChannelHandler.getChannelHandlers("golem", PacketNetherrackGolemFuel.class);
	}

}
