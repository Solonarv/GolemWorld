package com.solonarv.mods.golemworld.golem.advanced;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.solonarv.mods.golemworld.golem.EntityCustomGolem;
import com.solonarv.mods.golemworld.golem.GolemStats;
import com.solonarv.mods.golemworld.golem.util.DummyContainer;
import com.solonarv.mods.golemworld.lib.Reference;
import com.solonarv.mods.golemworld.util.WorldHelper;

public class EntityCraftingGolem extends EntityCustomGolem {
    public EntityCraftingGolem(World world) {
        super(world);
    }
    
    public static final GolemStats stats = new GolemStats();
    static {
        stats.maxHealth = 15;
        stats.attackDamageMean = 1;
        stats.attackDamageStdDev = 0;
        stats.name = "Crafting Golem";
        stats.texture = Reference.mobTexture("crafting_golem");
        stats.droppedItems(new ItemStack(Blocks.crafting_table, 1));
    }
    
    public int craftCooldown=0;
    // The center of the 3x3 from which to pull the recipe. Only above, in front, behind, or besides the golem.
    public int gridCenterX, gridCenterY, gridCenterZ;
    public ForgeDirection towardsGrid;
    // The coords of the inventory to insert the crafting result into, can only be directly below the golem
    public int targetX, targetY, targetZ;
    private DummyContainer dummyContainer = new DummyContainer();
    public static int maxCraftCooldown=20;
    
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if(craftCooldown>0){
            craftCooldown--;
        }else{
            checkSurroundings();
            craft();
            craftCooldown=maxCraftCooldown;
        }
    }

    public void craft() {
        if(this.worldObj.isRemote) return;
        IInventory[][] inventories=getCraftingInventories();
        ItemStack[][] items=new ItemStack[3][3];
        int[][] slots=new int[3][3];
        IInventory targetInv=(IInventory)worldObj.getTileEntity(targetX, targetY, targetZ);
        for (int y = 0; y < inventories.length; y++) {
            IInventory[] row = inventories[y];
            for (int x = 0; x < row.length; x++) {
                IInventory inv = row[x];
                if(inv!=null){
                    for(int slot=0; slot < inv.getSizeInventory(); slot++){
                        ItemStack item = inv.getStackInSlot(slot);
                        if(item!=null && item.stackSize>0){
                            items[y][x]=item;
                            slots[y][x]=slot;
                            break;
        }}}}}
        InventoryCrafting craftMatrix=new InventoryCrafting(this.dummyContainer, 3, 3);
        for(int y=0; y<3; y++) for(int x=0; x<3; x++){
            craftMatrix.setInventorySlotContents(x + 3*y, items[y][x]);
        }
        ItemStack result=CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj);
        if(result!=null){
            WorldHelper.shoveStackOrDrop(targetInv, result, -1, this);
            for(int y=0; y<3; y++) for(int x=0; x<3; x++){
                ItemStack stack=items[y][x];
                IInventory inventory=inventories[y][x];
                if(inventory==null) continue;
                if(stack.getItem().hasContainerItem(stack)){
                    ItemStack containerItem=stack.getItem().getContainerItem(stack);
                    inventory.decrStackSize(slots[y][x], 1);
                    WorldHelper.shoveStackOrDrop(inventory, containerItem, -1, this);
                } else {
                    inventory.decrStackSize(slots[y][x], 1);
                }
            }
        }
    }

    public void checkSurroundings() {
        towardsGrid=ForgeDirection.UNKNOWN;
        int mx=MathHelper.floor_double(this.posX);
        int y=MathHelper.floor_double(this.posY);
        int mz=MathHelper.floor_double(this.posZ);
        for(int x=mx; x<mx+2; x++) for(int z=mz; z<mz+2; z++){
            TileEntity maybeInventory=worldObj.getTileEntity(x, y-1, z);
            if(maybeInventory!=null && !maybeInventory.isInvalid() && maybeInventory instanceof IInventory){
                this.targetX=x;
                this.targetY=y;
                this.targetZ=z;
            }
            System.out.println(String.format("Looking for grid centers around %d, %d, %d", x, y, z));
            // Check up
            maybeInventory=worldObj.getTileEntity(x, y+3, z);
            if(maybeInventory!=null && !maybeInventory.isInvalid() && maybeInventory instanceof IInventory){
                this.gridCenterX=x;
                this.gridCenterY=y+3;
                this.gridCenterZ=z;
                this.towardsGrid=ForgeDirection.UP;
                System.out.println(String.format("Found grid center at %d, %d, %d: %s", gridCenterX, gridCenterY, gridCenterZ, towardsGrid.toString()));
                return;
            }
            
            // Check north (-z)
            maybeInventory=worldObj.getTileEntity(x, y+1, z-1);
            if(maybeInventory!=null && !maybeInventory.isInvalid() && maybeInventory instanceof IInventory){
                this.gridCenterX=x;
                this.gridCenterY=y;
                this.gridCenterZ=z-1;
                this.towardsGrid=ForgeDirection.NORTH;
                System.out.println(String.format("Found grid center at %d, %d, %d: %s", gridCenterX, gridCenterY, gridCenterZ, towardsGrid.toString()));
                return;
            }
            
            // Check east (+x)
            maybeInventory=worldObj.getTileEntity(x+1, y+1, z);
            if(maybeInventory!=null && !maybeInventory.isInvalid() && maybeInventory instanceof IInventory){
                this.gridCenterX=x+1;
                this.gridCenterY=y;
                this.gridCenterZ=z;
                this.towardsGrid=ForgeDirection.EAST;
                System.out.println(String.format("Found grid center at %d, %d, %d: %s", gridCenterX, gridCenterY, gridCenterZ, towardsGrid.toString()));
                return;
            }
            
            // Check south (+z)
            maybeInventory=worldObj.getTileEntity(x, y+1, z+1);
            if(maybeInventory!=null && !maybeInventory.isInvalid() && maybeInventory instanceof IInventory){
                this.gridCenterX=x;
                this.gridCenterY=y;
                this.gridCenterZ=z+1;
                this.towardsGrid=ForgeDirection.SOUTH;
                System.out.println(String.format("Found grid center at %d, %d, %d: %s", gridCenterX, gridCenterY, gridCenterZ, towardsGrid.toString()));
                return;
            }
            // Check west (-x)
            maybeInventory=worldObj.getTileEntity(x-1, y+1, z);
            if(maybeInventory!=null && !maybeInventory.isInvalid() && maybeInventory instanceof IInventory){
                this.gridCenterX=x-1;
                this.gridCenterY=y;
                this.gridCenterZ=z;
                this.towardsGrid=ForgeDirection.WEST;
                System.out.println(String.format("Found grid center at %d, %d, %d: %s", gridCenterX, gridCenterY, gridCenterZ, towardsGrid.toString()));
                return;
            }   
        }
    }
    
    /**
     * 
     * @return an {@link IInventory}[][], containing the inventories to pull from for the crafting grid
     */
    public IInventory[][] getCraftingInventories(){
        switch(towardsGrid){
        case UP: // NORTH(-z) will be the top of crafting grid, WEST(-x) the left
            return new IInventory[][]{
                    {WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY, gridCenterZ-1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ-1),
                        WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY, gridCenterZ-1)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY, gridCenterZ)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY, gridCenterZ+1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ+1),
                        WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY, gridCenterZ+1)}
            };
        case NORTH:
            return new IInventory[][]{
                    {WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY+1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY+1, gridCenterZ)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY, gridCenterZ)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY-1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY-1, gridCenterZ)}
            };
        case EAST:
            return new IInventory[][]{
                    {WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ-1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ+1)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ-1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ+1)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ-1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ+1)}
            };
        case SOUTH:
            return new IInventory[][]{
                    {WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY+1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY+1, gridCenterZ)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY, gridCenterZ)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX+1, gridCenterY-1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX-1, gridCenterY-1, gridCenterZ)}
            };
        case WEST:
            return new IInventory[][]{
                    {WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ+1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY+1, gridCenterZ-1)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ+1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY, gridCenterZ-1)},
                    {WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ+1),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ),
                        WorldHelper.getIInventory(worldObj, gridCenterX, gridCenterY-1, gridCenterZ-1)}
            };
        default:
            return new IInventory[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
            };
        }
    }
}
