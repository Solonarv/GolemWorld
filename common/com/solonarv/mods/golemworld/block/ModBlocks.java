package com.solonarv.mods.golemworld.block;

import java.lang.reflect.Field;

import net.minecraft.block.Block;

import com.solonarv.mods.golemworld.GolemWorld;
import com.solonarv.mods.golemworld.util.ReflectionHelper;

public class ModBlocks {
    public static final int enhancedAirID = GolemWorld.config.getBlock("enhancedAir", 600).getInt();
    
    public static BlockEnhancedAir enhancedAir = new BlockEnhancedAir(enhancedAirID);
    public static BlockPumpkinFixed fixedPumpkin, fixedPumpkinLantern;
    
    public static void init(){
        fixPumpkin();
    }

    public static void fixPumpkin() {
        if(!(Block.pumpkin instanceof BlockPumpkinFixed)){ // Don't patch twice
            int pumpkinID = Block.pumpkin.blockID;
            Field pumpkinField = ReflectionHelper.getFieldByNames(Block.class, "pumpkin", "field_72061_ba");
            ReflectionHelper.makeNotFinal(pumpkinField);
            try {
                pumpkinField.set(null, null);
                Block.blocksList[pumpkinID] = null;
                fixedPumpkin = (BlockPumpkinFixed) new BlockPumpkinFixed(pumpkinID, false).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pumpkin").func_111022_d("pumpkin");
                Block.blocksList[pumpkinID] = fixedPumpkin;
                pumpkinField.set(null, fixedPumpkin);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(!(Block.pumpkinLantern instanceof BlockPumpkinFixed)){ // Don't patch twice
            int pumpkinLanternID = Block.pumpkinLantern.blockID;
            Field pumpkinLanternField = ReflectionHelper.getFieldByNames(Block.class, "pumpkinLantern", "field_72008_bf");
            ReflectionHelper.makeNotFinal(pumpkinLanternField);
            try {
                pumpkinLanternField.set(null, null);
                Block.blocksList[pumpkinLanternID] = null;
                fixedPumpkinLantern = (BlockPumpkinFixed) new BlockPumpkinFixed(pumpkinLanternID, true).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setLightValue(1.0F).setUnlocalizedName("litpumpkin").func_111022_d("pumpkin");
                Block.blocksList[pumpkinLanternID] = fixedPumpkinLantern;
                pumpkinLanternField.set(null, fixedPumpkinLantern);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
