package com.solonarv.mods.golemworld.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Class holding various helper functions concerned with reflection
 * @author Solonarv
 *
 */
public class ReflectionHelper {
    
    /**
     * Grab a field from some Minecraft class that has one of the two given names
     * @param cls the class to grab a field from
     * @param deobfName The field's deobfuscated name
     * @param srgName The field's runtime deobf'ed name (SRG name) 
     * @return The field in the given class whose name is either deobfName or srgName
     */
    public static Field getFieldByNames(Class cls, String deobfName, String srgName){
        for(Field f : cls.getDeclaredFields()){
            f.setAccessible(true);
            String name = f.getName();
            if(name.equals(deobfName) || name.equals(srgName))
                return f;
        }
        return null;
    }
    
    /**
     * Remove the final modifier of a field
     * @param f A field to make not-final
     * @return The same field as given, for method chaining
     */
    public static Field makeNotFinal(Field f){
        try {
            Field fieldMods = Field.class.getDeclaredField("modifiers");
            fieldMods.setAccessible(true);
            fieldMods.setInt(f, f.getModifiers() &~ Modifier.FINAL);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return f;
    }
}
