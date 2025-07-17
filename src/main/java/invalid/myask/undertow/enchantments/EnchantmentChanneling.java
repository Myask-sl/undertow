package invalid.myask.undertow.enchantments;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.UndertowEnchantments;
import invalid.myask.undertow.entities.EntityCalledLightning;
import invalid.myask.undertow.entities.ProjectileTrident;
import invalid.myask.undertow.item.ItemTrident;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class EnchantmentChanneling extends Enchantment {
    public EnchantmentChanneling(int id, int weight) {
        super(id, weight, EnumEnchantmentType.weapon);
        setName("zeustarget");
    }

    public static boolean canStrikeAt(World worldObj, ProjectileTrident projectileTrident) {
        int level = EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.CHANNELING.effectId, projectileTrident.getStack());
        return canStrikeAt(worldObj, (int) projectileTrident.posX, (int) projectileTrident.posY, (int) projectileTrident.posZ, level) ||
            canStrikeAt(worldObj, (int) projectileTrident.lastTickPosX, (int) projectileTrident.lastTickPosY, (int) projectileTrident.prevPosZ, level);
    }
    public static boolean canStrikeAt(World worldObj, Entity target, int level) {
        return canStrikeAt(worldObj, (int) target.posX, (int) target.posY, (int) target.posZ, level);
    }
    public static boolean canStrikeAt(World worldObj, int x, int y, int z, int level) {
        int weather_level = worldObj.isThundering() ? 2 : worldObj.isRaining() ? 1 : 0;
        boolean allowed;
        if (!Config.channeling_weather_scaled)
            allowed = Config.channeling_weather_minimum <= weather_level
                && Config.channeling_weather_maximum >= weather_level;
        else
            allowed = weather_level + level >= 3; //so, III for clear, II for rain, I for thunder
        allowed = allowed && (!Config.channeling_skyvis_required || worldObj.canBlockSeeTheSky(x, y, z))
            && (!Config.channeling_sky_path_required || (worldObj.getPrecipitationHeight(x, z) <= y));
        return allowed;
    }

    public static boolean strikableBlock(Block stuckBlock) {
        return stuckBlock.getUnlocalizedName().equals("lightning_rod") || Config.channeling_triggers_on_allblocks;
    }

    @Override
    public int getMinEnchantability(int level) {
        return 20 + 5 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 40 + 10 * level;
    }

    @Override
    public int getMaxLevel() {
        return Config.channeling_weather_scaled ? 3 : 1;
    }

    /**
     * Apply on-striking effect.
     * @param aggressor The swinger.
     * @param stricken The vic.
     * @param level of enchantment
     */
    @Override
    public void func_151368_a(EntityLivingBase aggressor, Entity stricken, int level) {
        if (!Config.channeling_melee_triggers || !canStrikeAt(stricken.worldObj, stricken, level)) return;
        //else
        stricken.worldObj.addWeatherEffect(new EntityCalledLightning(stricken.worldObj, stricken.posX, stricken.posY,  stricken.posZ, aggressor));
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return (Config.channeling_trident_applicable && stack.getItem() instanceof ItemTrident) ||
            (Config.channeling_sword_applicable && stack.getItem() instanceof ItemSword);
    }
}
