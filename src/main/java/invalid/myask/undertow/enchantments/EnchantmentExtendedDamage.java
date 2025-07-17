package invalid.myask.undertow.enchantments;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.item.ItemTrident;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import static invalid.myask.undertow.util.UndertowUtils.rainCheck;

public class EnchantmentExtendedDamage extends EnchantmentDamage {
    protected static final String[] extended_bane_names = {"aquatic"};
    protected static final float[] extended_bane_multipliers = {2.5F};
    public static final EnumCreatureAttribute AQUATIC = EnumHelper.addCreatureAttribute("aquatic");

    public EnchantmentExtendedDamage(int iD, int weight, int targetedKind) {
        super(iD, weight, targetedKind);
    }

    @Override
    public int getMinEnchantability(int level) {
        return 1 + 8 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return this.getMinEnchantability(level) + 20;
    }

    @Override
    public String getName() {
        if (damageType >= 3 + extended_bane_names.length) return "enchantment.damage.invalid";
        return (damageType < 3) ? super.getName() : "enchantment.damage." + extended_bane_names[damageType - 3];
    }


    /**
     * Damage modifier functions.
     * @param enchantment_amplitude I, II, III...how enchanted are we?
     * @param targetKind The kind of creature this hit.
     * @return damage bonus to what's hit.
     */
    @Override
    public float func_152376_a(int enchantment_amplitude, EnumCreatureAttribute targetKind) {
        return switch (damageType) {
            case 0, 1, 2 -> super.func_152376_a(enchantment_amplitude, targetKind);
            case 3 -> targetKind == AQUATIC ? extended_bane_multipliers[0] * enchantment_amplitude : 0;
            default -> 0; //handled below, or OOB
        };
    }

    /**
     * Damage modifier functions.
     * @param enchantment_amplitude I, II, III...how enchanted are we?
     * @param stricken The creature this hit.
     * @return damage bonus (or penalty) to what's hit.
     */
    public float damageBonusTo(int enchantment_amplitude, EntityLivingBase stricken) { //TODO: mixin next to above: EnchantmentHelper.getEnchantmentModifierLiving()
        if (damageType == 3) {
            int triggers_hit =  0; //(Config.impale_by_creaturetype && stricken instanceof EntityWaterMob) ? 1 :
            int hitX = (int)stricken.posX, hitY = (int)stricken.posY, hitZ = (int)stricken.posZ;
            triggers_hit += (Config.impale_in_rain && rainCheck(stricken)) ? 1 : 0;
            triggers_hit += (Config.impale_in_water && stricken.isInWater()) ? 1 : 0;
            triggers_hit += (Config.impale_drowned && stricken instanceof EntityDrowned) ? 1 : 0;
            if (triggers_hit > 1 && !Config.impale_stack_triggers) triggers_hit = 1; //cap at 1 if not stacking
            return extended_bane_multipliers[0] * enchantment_amplitude * triggers_hit;
        }
        //if (damageType < 3)
        return 0; //handled in above, or OOB
    }

    /**
     * Applies effects to the stricken if applicable.
     * @param aggressor
     * @param stricken
     * @param baneKind The kind of creature this is good against.
     */
    @Override
    public void func_151368_a(EntityLivingBase aggressor, Entity stricken, int baneKind) {
        super.func_151368_a(aggressor, stricken, baneKind);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return damageType < 3 ? super.canApply(stack) : stack.getItem() instanceof ItemTrident || (!Config.impale_trident_only && super.canApply(stack));
    }
}
