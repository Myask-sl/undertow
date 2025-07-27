package invalid.myask.undertow.mixins.late;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.world.World;

import invalid.myask.undertow.enchantments.EnchantmentExtendedDamage;

/* import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;

@Mixin(EntityGuardian.class) */
public class MixinEntityGuardian_Impaling extends EntityCreature {
    public MixinEntityGuardian_Impaling(World w) {
        super(w);
    }

//    @ModifyReturnValue(method = "getCreatureAttribute", at = @At("RETURN"))
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnchantmentExtendedDamage.AQUATIC;
    }
}
