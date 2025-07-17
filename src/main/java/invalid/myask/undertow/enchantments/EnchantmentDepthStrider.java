package invalid.myask.undertow.enchantments;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.ducks.IDLC;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;


public class EnchantmentDepthStrider extends Enchantment {
    public final boolean forLava;
    public EnchantmentDepthStrider(int id, int weight, EnumEnchantmentType type, boolean isForLava) {
        super(id, weight, type);
        forLava = isForLava;
        if (isForLava)
            setName("magma_strider");
        else
            setName("depth_strider");

    }

    /**
     * Returns how much flow should affect based on entity's enchanted stuff. 1 at DS 0, 2/3 at I, 1/3 at II, 0 at III.
     * For Magma strider, decrease by fifths instead of thirds.
     * @param e entity to check
     * @return 0-1 how much
     */
    public static double getFlowMult(Entity e, boolean forLava) {
        double mult = 1;
        if (e instanceof EntityLivingBase elb) {
            if (Config.enable_depth_strider && elb.isInWater() && !forLava) {
                int clamped = MathHelper.clamp_int(getStriderLevel(elb, forLava), 0, 3);
                mult = (double) (3 - clamped) / 3;
            }
            if (Config.enable_magma_strider && elb.handleLavaMovement() && forLava) {
                int clamped = MathHelper.clamp_int(getStriderLevel(elb, forLava), 0, 5);
                mult = (double) (5 - clamped) / 5;
            }
            if (isSwimmingPlayer(elb))
                mult *= Config.swim_friction_multiplier;
        }
        return mult;
    }

    public static int getStriderLevel (EntityLivingBase elb, boolean forLava) {
        ItemStack striderEquipment = elb.getEquipmentInSlot(1);
        int id = forLava ? Config.enchant_magmastrider_id : Config.enchant_depthstrider_id;
        if (Config.depth_strider_horses && elb instanceof EntityHorse horse && horse.func_110241_cb() > 0)
            striderEquipment = ((IDLC) horse).getArmor();
        int striderLevel = EnchantmentHelper.getEnchantmentLevel(id, striderEquipment);

        if (Config.depth_strider_nonboots) {
            int otherLevel;
            for (int i = 2; i < 5; i++) {
                 otherLevel = EnchantmentHelper.getEnchantmentLevel(id, elb.getEquipmentInSlot(i));
                 if (otherLevel > striderLevel) striderLevel = otherLevel;
            }
        }
        return striderLevel;
    }

    private static boolean isSwimmingPlayer(EntityLivingBase elb) {
        return (Config.enable_swim && elb instanceof EntityPlayer && ((IUndertowPosableEntity)elb).undertow$isSwimming());
    }

    @Override
    public int getMinEnchantability(int level) {
        return 10 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return getMinEnchantability(level) + 15;
    }

    @Override
    public int getMaxLevel() {
        return forLava ? 5 : 3;
    }

    public static void counterFluidFriction(EntityLivingBase elb, boolean forLava) {
        double frictionMult = 0.91;
        if (elb.onGround && !isSwimmingPlayer(elb)) {
            frictionMult *= elb.worldObj.getBlock(MathHelper.floor_double(elb.posX),
                MathHelper.floor_double(elb.boundingBox.minY) - 1, MathHelper.floor_double(elb.posZ)).slipperiness;
        }
        frictionMult *= !forLava ? 1.25 : 2; //1.25 to counter regular water friction
        frictionMult = ((frictionMult - 1) * (1 - getFlowMult(elb, forLava))) + 1;
//        frictionMult = oldWaterFric + (1 - getFlowMult(elb)) * (frictionMult - oldWaterFric);
        elb.motionX *= frictionMult;
        elb.motionZ *= frictionMult;
    }

    public static double getAdjustedFluidFriction(EntityLivingBase elb, float oldWaterFric, boolean forLava) {
        double multAdj = 1 - getFlowMult(elb, forLava);
        double frictionMult = 0.91;
        if (elb.onGround && !isSwimmingPlayer(elb)) {
            frictionMult *= elb.worldObj.getBlock(MathHelper.floor_double(elb.posX),
                MathHelper.floor_double(elb.boundingBox.minY) - 1, MathHelper.floor_double(elb.posZ)).slipperiness;
        }
        frictionMult = 0.16277136F / ( frictionMult * frictionMult * frictionMult);
        double baseFric = elb.getAIMoveSpeed() * frictionMult;
        return oldWaterFric + multAdj * (baseFric - oldWaterFric);
        //return oldWaterFric;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return super.canApply(stack);
    }

    @Override
    public boolean canApplyTogether(Enchantment theOtherOne) {
        return !theOtherOne.getName().equals("enchantment.frost_walker") //EFR
            && !theOtherOne.getName().equals("enchantment.basalt_walker") //Netherlicious
            && super.canApplyTogether(theOtherOne);
    }
}
