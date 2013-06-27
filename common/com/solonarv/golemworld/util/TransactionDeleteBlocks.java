package com.solonarv.golemworld.util;

import java.util.ArrayList;

import net.minecraft.world.World;

public class TransactionDeleteBlocks {

    protected class DeleteAction {
        protected World world;
        protected int x;
        protected int y;
        protected int z;

        public DeleteAction(World world, int x, int y, int z) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void run() {
            world.setBlockToAir(x, y, z);
        }
    }

    protected ArrayList<DeleteAction> actions = new ArrayList<DeleteAction>();

    public void addAction(World world, int x, int y, int z) {
        actions.add(new DeleteAction(world, x, y, z));
    }

    public void commit() {
        for (DeleteAction a : actions) {
            if (a != null) {
                a.run();
            }
        }
    }

    public void abort() {
        actions.clear();
    }
}
