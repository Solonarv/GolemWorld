package com.solonarv.golemworld.lib;

import com.solonarv.golemworld.GolemWorld;

public class ItemIDs {
    
    public static final int PAPER_OF_AWAKENING=GolemWorld.config.get("Item", "paperOfAwakening", 5000).getInt(5000);
    
}
