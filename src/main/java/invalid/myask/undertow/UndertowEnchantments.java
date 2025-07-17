package invalid.myask.undertow;

import invalid.myask.undertow.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class UndertowEnchantments {
    public static Enchantment CURSE_BREAKING; //TODOne: mixin ItemStack line 296 to insert loop.

    public static Enchantment CURSE_WEAKNESS;
    public static Enchantment CURSE_UNDEADSBOON;
    public static Enchantment CURSE_BUGSBOON;
    public static Enchantment CURSE_UNIMPALE;

    public static Enchantment DEPTH_STRIDER;
    public static Enchantment MAGMA_STRIDER;
    public static Enchantment SWIFT_SWIM;

    public static Enchantment CHANNELING; // by Zeus!
    public static Enchantment IMPALING;
    public static Enchantment LOYALTY;
    public static Enchantment RIPTIDE;
    public static Enchantment UNDERTOW; //nonvanilla

    public static Enchantment CLEAVING;

    public static void init(){
        if (Config.enable_curse_breaking)
            CURSE_BREAKING = new EnchantmentCurseBreaking(
                Config.enchant_curse_breaking_id, 1);

        if (Config.enable_depth_strider)
            DEPTH_STRIDER = new EnchantmentDepthStrider(Config.enchant_depthstrider_id, 2, EnumEnchantmentType.armor_feet, false);
        if (Config.enable_magma_strider)
            MAGMA_STRIDER = new EnchantmentDepthStrider(Config.enchant_magmastrider_id, 1, EnumEnchantmentType.armor_feet, true);
        if (Config.enable_swift_swim)
            SWIFT_SWIM = new EnchantmentSwiftSwim(Config.enchant_swiftswim_id, 1, EnumEnchantmentType.armor_legs);

        if (Config.enable_curses_damage) {
            CURSE_WEAKNESS = new EnchantmentCurseWeakness(Config.enchant_curse_weakness_id, 1, 0);
            CURSE_UNDEADSBOON = new EnchantmentCurseWeakness(Config.enchant_curse_undeadsboon_id, 2, 1);
            CURSE_BUGSBOON = new EnchantmentCurseWeakness(Config.enchant_curse_bugsboon_id, 2, 2);
            if (Config.enable_enchants_trident)
                CURSE_UNIMPALE = new EnchantmentCurseWeakness(Config.enchant_curse_unimpale_id, 1, Config.bane_kind_aquatic);
        }

        if (Config.enable_enchants_trident) {
            CHANNELING = new EnchantmentChanneling(Config.enchant_channeling_id, 1); // by Zeus!
            IMPALING = new EnchantmentExtendedDamage(Config.enchant_impaling_id, 2, Config.bane_kind_aquatic);
            LOYALTY = new EnchantmentLoyalty(Config.enchant_loyalty_id, 5);
            RIPTIDE = new EnchantmentRiptide(Config.enchant_riptide_id, 2);
        }
        if (Config.enable_undertow_trident) {
            UNDERTOW = new EnchantmentUndertow(Config.enchant_undertow_id, 2, EnumEnchantmentType.weapon);
        }
        if (Config.enable_cleaving_axe) {
            CLEAVING = new EnchantmentCleaving(Config.enchant_cleaving_id, 2);
        }
    }
}
