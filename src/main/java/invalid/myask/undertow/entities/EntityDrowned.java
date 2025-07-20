package invalid.myask.undertow.entities;

import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.enchantments.EnchantmentRiptide;
import invalid.myask.undertow.UndertowEnchantments;
import invalid.myask.undertow.item.ItemTrident;
import invalid.myask.undertow.UndertowItems;

import static invalid.myask.undertow.util.UndertowUtils.rainCheck;

public class EntityDrowned extends EntityZombie implements IRangedAttackMob {
    protected static Class[] added_targets = { EntityPlayer.class, EntityVillager.class, //these two added by Zombie
         EntityIronGolem.class }; //, EntityTurtle.class, EntityAxolotl.class};
    protected static final int TARGET_COUNT = added_targets.length;
    protected static int[] attackPriorities = { 9, 10, 11 };
    protected EntityAIBase[] whack = new EntityAIBase[added_targets.length];
    protected EntityAIBase shoot = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    protected static int[] targetPriorities = { 2, 2, 2 };

    protected boolean yearnsToShoot = false;

    protected boolean hasNautilus = false;
    protected boolean converted = false;

    protected static final float[] P_OF_TRIDENT = {.0625F, .15F, 0}; //Java, bedrock, bedrock convert
    protected static final float[] P_OF_ROD = {.0375F, .0085F, .01F}; //converted don't re-gen items on Java mode
    protected static final float[] P_OF_SHELL = {.03F, .08F, .08F};
    protected boolean dirtyHand = false;

    public EntityDrowned(World world) {this(world, false);}
    public EntityDrowned(World world, boolean converted) {
        super(world);
/*        for (int i = 0; i < added_targets.length; i++){
            this.tasks.addTask(attackPriorities[i] , new EntityAIAttackOnCollide(this, added_targets[i], 1.0, false));
            this.targetTasks.addTask(targetPriorities[i], new EntityAINearestAttackableTarget(this, (Class<? extends Entity>)added_targets[i], 0, false));
        }*/
        whack[2] = new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, false);
        this.targetTasks.addTask( 2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, 0, false));
        //TODO: neutrality toward dry player?
        this.converted = converted;

        PathNavigate pN = this.getNavigator();
        if (pN != null) {
            pN.setCanSwim(true);
            pN.setAvoidsWater(false);
            pN.setAvoidSun(true);
        }
        EntityAIBase oldSwim = null;
        for (EntityAITasks.EntityAITaskEntry parseTask : this.tasks.taskEntries ) {
            if (parseTask.action instanceof EntityAIAttackOnCollide pt) {
                if (parseTask.priority == 2) {
                    whack[0] = pt;
                }
                if (parseTask.priority == 4) {
                    whack[1] = pt;
                }
            } else if (parseTask.action instanceof EntityAISwimming swam) {
                oldSwim = swam;
            } //TODO: replace with more appropriate swimming-toward-target
        }
        if (oldSwim != null) tasks.removeTask(oldSwim);
        dirtyHand = true; //gotta init 'em after all
    }

    @Override
    protected void addRandomArmor() {
        int i = !Config.drowned_equipment_odds_bedrock ? 0 : converted ? 2 : 1;
        if (i == 0 && converted) return;
        if (this.rand.nextFloat() < P_OF_TRIDENT[i]) {
            setCurrentItemOrArmor(0, new ItemStack(UndertowItems.TRIDENT));
        }
        else if (this.rand.nextFloat() < P_OF_ROD[i]) {
            setCurrentItemOrArmor(0, new ItemStack(Items.fishing_rod));
        }
        if (this.rand.nextFloat() < P_OF_SHELL[i]) {
            hasNautilus = true;
        }
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
        boolean flag = (!forSpawnCount) && (type == EnumCreatureType.waterCreature);
        return flag || super.isCreatureType(type, forSpawnCount);
    }

    @Override
    public boolean isEntityUndead() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected int decreaseAirSupply(int beforeSupply) {
        return beforeSupply; //drowned, can't.
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float force) {
        ItemStack dent = this.getHeldItem();
        if (dent == null) {
            dirtyHand = true;
            return;
        }
        int ench = EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.RIPTIDE.effectId, dent);
        boolean canFly = EnchantmentRiptide.canFly(ench, this), dFly = Config.drowned_fly_riptide_tridents;
        if ((Config.drowned_throw_riptide_tridents && (!dFly || !canFly)) || ench == 0)
        {
            ProjectileTrident toFling = new ProjectileTrident(this.worldObj, this, target, force * 2, 14 - 4 * this.worldObj.difficultySetting.getDifficultyId());
            toFling.setStack(dent);
            this.worldObj.spawnEntityInWorld(toFling);
            if (Config.drowned_throw_away_tridents && (!Config.infinity_applicable_trident ||
                EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, dent) == 0)) {
                if (!toFling.isLoyal()) toFling.canBePickedUp = 1;
                setCurrentItemOrArmor(0, null);
                //AItasks being iterated when this is called, can't remove task yet
            }
        }
        else if (dFly && ench > 1 && canFly) {
            ((IUndertowPosableEntity)this).undertow$setMaxFlyTicks(((EnchantmentRiptide)UndertowEnchantments.RIPTIDE).getMaxFlightTime(ench));
            ((IUndertowPosableEntity)this).undertow$setTideRipping(true);
/*            ProjectileRiptide swirl = new ProjectileRiptide(this, dent);
            if (!this.isClientWorld())
                this.worldObj.spawnEntityInWorld(swirl); */
        }
    }

    @Override
    protected void updateAITasks() {
        if (dirtyHand) {
            refreshCombatTask();
            dirtyHand = false;
        }
        super.updateAITasks();
    }

    @Override
    public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn) {
        super.setCurrentItemOrArmor(slotIn, itemStackIn);
        if ( slotIn == 0 ) dirtyHand = true;
    }

    @Override
    public boolean attackEntityAsMob(Entity vic) {
        boolean hit = super.attackEntityAsMob(vic);
        if (hit && vic instanceof EntityLivingBase vicLB && vicLB.isInWater()) {
            // pull victims down to watery death
            vicLB.motionY -= 0.8F * (1 - vicLB.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
        }
        return hit;
    }

    public void refreshCombatTask() {
        ItemStack stack = getHeldItem();
        boolean flag = stack != null && stack.getItem() instanceof ItemTrident
            && (EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.RIPTIDE.effectId, stack) <= 0
            || (Config.drowned_throw_riptide_tridents) || (Config.drowned_fly_riptide_tridents));
        toggleShootAI(flag);
    }

    private void toggleShootAI(boolean shallShoot) {
        tasks.removeTask(shoot);

        for (int i = 0; i < added_targets.length; i++) {
            if (shallShoot)
                tasks.removeTask(whack[i]); //means riptiders need to use projectile like players
            else tasks.addTask(attackPriorities[i], whack[i]);
        }

        if (shallShoot) {
            tasks.addTask(2, shoot);
        }

        yearnsToShoot = shallShoot;
    }

    @Override
    protected void dropRareDrop(int p_70600_1_) {
        if (OreDictionary.doesOreNameExist("ingotCopper")) {
            ArrayList<ItemStack> ingots = OreDictionary.getOres("ingotCopper");
            this.dropItem(ingots.get(rand.nextInt(ingots.size())).getItem(), 1);
        }
        else
            super.dropRareDrop(p_70600_1_);
    }

    @Override
    protected void dropEquipment(boolean playerAlignedKill, int lootingLevel) {
        super.dropEquipment(playerAlignedKill, lootingLevel);
        if (hasNautilus) {
            this.entityDropItem(new ItemStack(UndertowItems.NAUTILUS), 0.0F);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        hasNautilus = tagCompound.getBoolean("has_nautilus");
        refreshCombatTask();
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("has_nautilus", hasNautilus);
    }

    @Override
    public boolean isVillager() {
        return false;
    }

    public boolean spearPosing() {
        boolean b = yearnsToShoot;
        ItemStack mainHandItem = getHeldItem();
        if (EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.RIPTIDE.effectId, mainHandItem) > 0)
            b = yearnsToShoot && (EnchantmentRiptide.canFly(mainHandItem, this) || Config.drowned_throw_riptide_tridents);
        return b;
    }

    public boolean hasNautilus() {
        return hasNautilus;
    }
}
