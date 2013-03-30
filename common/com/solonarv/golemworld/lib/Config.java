package com.solonarv.golemworld.lib;

import java.io.File;
import java.util.HashMap;

public class Config{
    private static HashMap<String,Object> config;
    
    public static void loadConfig(String path){
        File configFile=new File(path);
        // TODO magically:
        // configFile => config
        config.put("foo","bar");
        
        if( !configFile.isFile()){
            // Use default
        };
    }
}
