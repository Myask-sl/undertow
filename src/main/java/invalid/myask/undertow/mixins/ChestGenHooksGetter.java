package invalid.myask.undertow.mixins;

import net.minecraftforge.common.ChestGenHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ChestGenHooks.class)
public interface ChestGenHooksGetter {
    @Accessor(remap = false)
    String getCategory();
}
