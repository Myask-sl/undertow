package invalid.myask.undertow.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class AnvilContents extends Container {

    public AnvilContents() {

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return false;
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_) {}
}
