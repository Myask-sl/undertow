package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import invalid.myask.undertow.Config;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(ItemStack.class)
public class MixinItemStack_curseBreaking {
    @ModifyVariable(
        method = "attemptDamageItem",
        at = @At(value = "HEAD"),
        argsOnly = true)
    private int breakMore(int original, @Local(argsOnly = true) Random rand) {
        if (Config.enable_curse_breaking) {
            int breaking = EnchantmentHelper.getEnchantmentLevel(Config.enchant_curse_breaking_id, (ItemStack) (Object) this);
            for (int i = 0; i < breaking; ++i) {
                if (rand.nextInt(5) < 3) {
                    ++original;
                }
            }
        }
        return original;
    }
}
