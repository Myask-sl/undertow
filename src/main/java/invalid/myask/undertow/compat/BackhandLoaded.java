package invalid.myask.undertow.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import xonin.backhand.api.core.IOffhandInventory;
import xonin.backhand.api.core.IBackhandPlayer;

public class BackhandLoaded extends Backhand {
    @Override
    public ItemStack getOffHandItem(EntityPlayer alex) {
        return ((IOffhandInventory)alex.inventory).backhand$getOffhandItem();
    }

    @Override
    public boolean isOffhandItemInUse(EntityPlayer alex) {
        return ((IBackhandPlayer)alex).isOffhandItemInUse();
    }

    @Override
    public boolean isUsingOffhand(EntityPlayer alex) {
        return ((IBackhandPlayer)alex).isUsingOffhand();
    }
}
