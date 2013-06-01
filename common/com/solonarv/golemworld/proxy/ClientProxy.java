package com.solonarv.golemworld.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import com.solonarv.golemworld.lib.Reference;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers(){
        MinecraftForgeClient.preloadTexture(Reference.ITEM_TEXTURES+"paperAwakening.png");
        //MinecraftForgeClient.preloadTexture(Reference.MOB_TEXTURES);
    }
}
