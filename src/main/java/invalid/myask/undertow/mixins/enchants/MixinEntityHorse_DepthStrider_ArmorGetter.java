package invalid.myask.undertow.mixins.enchants;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.ItemStack;

import invalid.myask.undertow.ducks.IDLC;

@Mixin(EntityHorse.class)
public abstract class MixinEntityHorse_DepthStrider_ArmorGetter implements IDLC {
    @Shadow
    private AnimalChest horseChest;
    @Shadow
    public abstract int getHorseType();

    @Override
    public ItemStack getArmor() {
        if (horseChest == null || getHorseType() == 1 || getHorseType() == 2) return null;
        else return horseChest.getStackInSlot(1);
    }
}
