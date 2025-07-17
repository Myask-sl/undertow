package invalid.myask.undertow.mixins;

import invalid.myask.undertow.enchantments.EnchantmentExtendedDamage;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityWaterMob.class)
public class MixinEntityWaterMob_Impaling extends EntityCreature {
    public MixinEntityWaterMob_Impaling(World w) {
        super(w);
    }

//    @ModifyReturnValue(method = "getCreatureAttribute", at = @At("RETURN"))
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnchantmentExtendedDamage.AQUATIC;
    }
}
