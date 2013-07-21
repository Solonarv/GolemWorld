package com.solonarv.mods.golemworld.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

public class AIHelper {
    
    public static void clearAITasks(EntityAITasks tasks,
            Class<? extends EntityAIBase>... toRemove) {
        List<EntityAIBase> temp = new ArrayList<EntityAIBase>();
        List<Class<? extends EntityAIBase>> classes = Arrays.asList(toRemove);
        for (Object aiTemp : tasks.taskEntries) {
            EntityAIBase ai = (EntityAIBase) aiTemp;
            if (classes.contains(ai)) {
                temp.add(ai);
            }
        }
        for (EntityAIBase ai : temp) {
            tasks.removeTask(ai);
        }
    }
}
