package invalid.myask.undertow.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraftforge.common.ChestGenHooks;

@Mixin(value = ChestGenHooks.class)
public interface ChestGenHooksGetter {
    @Accessor(remap = false)
    String getCategory();
}
