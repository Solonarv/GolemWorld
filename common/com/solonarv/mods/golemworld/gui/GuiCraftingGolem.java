package com.solonarv.mods.golemworld.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.solonarv.mods.golemworld.lib.Reference;

public class GuiCraftingGolem extends GuiGolem {
    
    public GuiCraftingGolem(Container par1Container) {
        super(par1Container);
        
        this.xSize=176;
        this.ySize=154;
    }
    
    private static final ResourceLocation texture=Reference.guiTexture("crafting_golem");
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1,1,1,1);
        
        Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }
    
}
