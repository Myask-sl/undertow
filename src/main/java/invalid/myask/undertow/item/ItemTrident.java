package invalid.myask.undertow.item;

import com.google.common.collect.Multimap;
import invalid.myask.undertow.Config;
import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.enchantments.EnchantmentRiptide;
import invalid.myask.undertow.entities.ProjectileRiptide;
import invalid.myask.undertow.entities.ProjectileTrident;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import invalid.myask.undertow.UndertowEnchantments;

import static invalid.myask.undertow.util.UndertowUtils.rainCheck;

//import xonin.backhand.api.core.IOffhandInventory;

public class ItemTrident extends Item {
    protected float trident_damage;

    public ItemTrident() {
        this(9);}
    public ItemTrident(float damage_value){
        maxStackSize = 1;
        trident_damage = damage_value;
        setMaxDamage(250);
    }

    @SuppressWarnings("deprecation")
    public Multimap<String, AttributeModifier> getItemAttributeModifiers()
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
            new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.trident_damage, 0));
        return multimap;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world,
                                     EntityPlayer player, int useTime) {
        if (stack.getMaxDamage() - stack.getItemDamage() == 1)
            return;
        int timeLeft = this.getMaxItemUseDuration(stack) - useTime;
        if (timeLeft < 10)
            return;
        int riptideLevel = EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.RIPTIDE.effectId, stack);
        boolean thrownAtAll = riptideLevel == 0;
        boolean thrownAway = !player.capabilities.isCreativeMode
            && (!Config.infinity_applicable_trident || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) == 0);
        if (thrownAtAll){
            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F + itemRand.nextFloat() * 0.4F );
            ProjectileTrident trident = new ProjectileTrident(world, player, 1.6F);
            trident.setStack(stack); //lets it remember enchants, or damage for picking up
            if (thrownAway){
                trident.canBePickedUp = 1;
                if (player.getCurrentEquippedItem() == stack)
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                // else if (player.inventory instanceof IOffhandInventory backhander && backhander.getOffhandItem() == stack) //TODO: backhand compatible
                //  backhander.setOffhandItem(null);
            }
            else trident.canBePickedUp = 2;
            if (!world.isRemote)
                world.spawnEntityInWorld(trident); //kinda important, that.
        }
        else if (EnchantmentRiptide.canFly(stack, player)) {
            IUndertowPosableEntity aquAlex = (IUndertowPosableEntity) player;
            aquAlex.undertow$setTideRipping(true);
            int flyTime = ((EnchantmentRiptide) UndertowEnchantments.RIPTIDE).getMaxFlightTime(riptideLevel);
            double flyThrust = ((EnchantmentRiptide) UndertowEnchantments.RIPTIDE).getThrust(riptideLevel);
            aquAlex.undertow$setMaxFlyTicks(flyTime);
            aquAlex.undertow$setFlyThrust(flyThrust);
            aquAlex.undertow$resetFlyingTicks();
            ProjectileRiptide swirlies = new ProjectileRiptide(player, stack);

            world.spawnEntityInWorld(swirlies);
            stack.damageItem(1, player);
        }
        else {
            // be sad, for Riptide prevents throw but you're disabled.
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!(Config.enable_enchants_trident && EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.RIPTIDE.effectId, stack) > 0
            && !(EnchantmentRiptide.canFly(stack, player))))
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return super.onItemRightClick(stack, worldIn, player);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && Config.trident_disable_creative_block_break) return true;
        return super.onBlockStartBreak(itemstack, X, Y, Z, player);
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, int x, int y, int z, EntityLivingBase elb)
    {
        if ((double)blockIn.getBlockHardness(worldIn, x, y, z) != 0.0D)
        {
            stack.damageItem(2, elb);
        }

        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return super.isBookEnchantable(stack, book) || (Config.infinity_applicable_trident &&
            EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 0xFFFF;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(Undertow.MODID + ":" + iconString);
    }

    public void registerDispensation() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new IBehaviorDispenseItem() {
            private final BehaviorDefaultDispenseItem disabled = new BehaviorDefaultDispenseItem();

            @Override
            public ItemStack dispense(IBlockSource source, ItemStack stack) {
                if (!Config.dispenser_throws_tridents) return disabled.dispense(source, stack);
                World world = source.getWorld();
                IPosition iposition = BlockDispenser.func_149939_a(source);
                EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
                ProjectileTrident trid = this.getProjectileEntity(world, iposition);
                trid.setThrowableHeading((double)enumfacing.getFrontOffsetX(), (double)((float)enumfacing.getFrontOffsetY() + 0.1F), (double)enumfacing.getFrontOffsetZ(), 1.1F,6.0F);
                trid.setStack(stack.splitStack(1));
                world.spawnEntityInWorld(trid);
                return stack;
            }

            private ProjectileTrident getProjectileEntity(World world, IPosition pos) {
                return new ProjectileTrident(world, pos.getX(), pos.getY(), pos.getZ());
            }
        });
    }

    public float getTridentDamage() {
        return trident_damage;
    }
}
