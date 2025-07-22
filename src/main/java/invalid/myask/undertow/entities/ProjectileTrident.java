package invalid.myask.undertow.entities;

import java.util.List;
import io.netty.buffer.ByteBuf;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.*;
import net.minecraft.world.World;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.enchantments.EnchantmentChanneling;
import invalid.myask.undertow.UndertowEnchantments;
import invalid.myask.undertow.item.ItemTrident;

public class ProjectileTrident extends EntityArrow implements IEntityAdditionalSpawnData {
    private static final int DESPAWN_MINUTES = 5;
    private static final float GRAVITY_DROP = 0.05F;

    public Entity shootingEntity;
    protected String player_name;

    protected boolean returning = false;
    private int initial_magnitude;

    protected int stuckX;
    protected int stuckY;
    protected int stuckZ;
    protected Block stuckBlock;
    protected int stuckMeta;

    protected int stuckTwangTicks = 0;

    protected boolean inGround;
    protected int ticksStuck = 0;
    protected int ticksFlying = 0;

    protected ItemStack thingy;
    protected int loyal = 0;
    protected float impactDamage = 9.0F;
    protected int punchiness = 0;
    public int canBePickedUp = 0;
    protected int zeustarget = 0;
    protected static Vec3 hitBounceVector = Vec3.createVectorHelper( -0.05, 0.25, -0.05);
    protected boolean willHit;

    public ProjectileTrident(World world) { //called clientside by reflection
        super(world);
        init_helper();
    }

    public ProjectileTrident(World world, double x, double y, double z) {
        super(world);
        this.setPosition(x, y, z);
        init_helper();
    }

    /**
     * Monster-facing shot constructor. Force should be 1.28(80% of 1.6 skeleton arrow) to match vanilla? noise 14 - 3*difficulty
     */
    public ProjectileTrident(World world, EntityLivingBase shooter, EntityLivingBase target, float force, float noise) {
        super(world);
        this.shootingEntity = shooter;
        init_helper();
        this.setPosition(shooter.posX, shooter.posY, shooter.posZ);
        posY += (double)shooter.getEyeHeight() - 0.1D;

        double dX = target.posX - shooter.posX;
        double dY = target.boundingBox.minY + (double)(target.height / 3.0F) - this.posY;
        double dZ = target.posZ - shooter.posZ;
        double flatDist = MathHelper.sqrt_double(dX * dX + dZ * dZ);

        if (flatDist >= 1.0E-7D)
        {
            float yaw = (float)(Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
            float pitch = (float)(-(Math.atan2(dY, flatDist) * 180.0D / Math.PI));
            double dXNormalized = 0; // dX / flatDist;
            double dZNormalized = 0; // dZ / flatDist;
            this.setLocationAndAngles(shooter.posX + dXNormalized, this.posY, shooter.posZ + dZNormalized, yaw, pitch);
            this.setThrowableHeading(dX, dY + flatDist * 0.2F, dZ, force, noise);
        }
    }

    public ProjectileTrident(World world, EntityLivingBase shooter, float force) {
        super(world);
        this.shootingEntity = shooter;
        init_helper();
        this.setPosition(shooter.posX, shooter.posY, shooter.posZ);
        posY += shooter.yOffset + (double)shooter.getEyeHeight() - 0.05D;
        if (shooter instanceof EntityPlayer player)
            player_name = player.getCommandSenderName();
        Vec3 v = shooter.getLookVec().normalize();
        this.setThrowableHeading(v.xCoord, v.yCoord, v.zCoord, force, 0.1F);
    }

    private void init_helper(){
        canBePickedUp = (this.shootingEntity instanceof EntityPlayer) ? 1 : 0;
        renderDistanceWeight = 1F;
        this.setSize(0.5F, 0.5F);
        this.yOffset = 0F;
        this.willHit = true;
    }

    /**
     * Player-facing shot constructor. Force should be 1.6 to match vanilla.
     */
    public ProjectileTrident(World world, EntityLivingBase shooter, float force, ItemStack stack) {
        this(world, shooter, force);
        thingy = stack.copy();
        populate_fields_from_stack(thingy);
    }

    private void populate_fields_from_stack(ItemStack stack) {
        if (stack == null) return;
        loyal = EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.LOYALTY.effectId, stack);
        impactDamage = ((ItemTrident) stack.getItem()).getTridentDamage();
        punchiness = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
        zeustarget = EnchantmentHelper.getEnchantmentLevel(UndertowEnchantments.CHANNELING.effectId, stack);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        super.setVelocity(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(x * x + z * z);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, f) * 180.0D / Math.PI);
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksStuck = 0;
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        if (nbt == null) nbt = new NBTTagCompound();
        super.writeEntityToNBT(nbt);
        nbt.setShort("xTile", (short)this.stuckX);
        nbt.setShort("yTile", (short)this.stuckY);
        nbt.setShort("zTile", (short)this.stuckZ);
        nbt.setShort("life", (short)this.ticksStuck);
        nbt.setByte("inTile", (byte)Block.getIdFromBlock(this.stuckBlock));
        nbt.setByte("inData", (byte)this.stuckMeta);
        nbt.setByte("shake", (byte)this.stuckTwangTicks);
        nbt.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbt.setBoolean("returning", returning);
        nbt.setByte("pickup", (byte)this.canBePickedUp);
        if (player_name != null)
            nbt.setString("player_owner", player_name);
        NBTTagCompound shootNBTs = nbt.getCompoundTag("tridentshooter"),
            stackNBTs = nbt.getCompoundTag("tridentstack");
        if (shootingEntity != null)
            shootingEntity.writeToNBT(shootNBTs);
        if (thingy != null) thingy.writeToNBT(stackNBTs);
        nbt.setTag("tridentshooter", shootNBTs);
        nbt.setTag("tridentstack", stackNBTs);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.stuckX = nbt.getShort("xTile");
        this.stuckY = nbt.getShort("yTile");
        this.stuckZ = nbt.getShort("zTile");
        this.ticksStuck = nbt.getShort("life");
        byte b = nbt.getByte("inTile");
        this.stuckBlock = b == 0 ? null : Block.getBlockById(b);
        this.stuckMeta = nbt.getByte("inData");
        this.stuckTwangTicks = nbt.getByte("shake");
        this.inGround = nbt.getByte("inGround") == 1;
        this.returning = nbt.getBoolean("returning");

        if (nbt.hasKey("pickup", 99))
        {
            this.canBePickedUp = nbt.getByte("pickup");
        }
        else if (nbt.hasKey("player", 99))
        {
            this.canBePickedUp = nbt.getBoolean("player") ? 1 : 0;
        }

        if (nbt.hasKey("player_owner")) {
            player_name = nbt.getString("player_owner");
            shootingEntity = worldObj.getPlayerEntityByName(player_name);
        }
        else if (nbt.hasKey("tridentshooter")) {
            shootingEntity.readFromNBT(nbt.getCompoundTag("tridentshooter"));
        }
        if (nbt.hasKey("tridentstack")) {
            thingy = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("tridentstack"));
            populate_fields_from_stack(thingy);
        }
    }

/*    public boolean getHackyInGround(){
        NBTTagCompound nbtc = new NBTTagCompound();
        writeEntityToNBT(nbtc);
        return nbtc.getByte("inGround") == 1;
    }*/

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (this.loyal > 0 && entityIn != this.shootingEntity)
            return;
        if (!this.worldObj.isRemote && ((this.inGround && this.stuckTwangTicks <= 0) || this.returning) )
        {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityIn.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(this.thingy))
            {
                flag = false;
            }

            if (flag || thingy == null)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityIn.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    public boolean pickedUpBy(Entity entityIn) {
        if (!this.worldObj.isRemote && ((this.inGround && this.stuckTwangTicks <= 0) || this.returning)) {
            if (entityIn instanceof EntityPlayer efe) {
                onCollideWithPlayer(efe);
            } else if (entityIn instanceof EntityDrowned drOwned && (this.shootingEntity == drOwned || drOwned.canPickUpLoot())) {
                if (drOwned.getHeldItem() == null) {
                    drOwned.setCurrentItemOrArmor(0, thingy);
                    drOwned.onItemPickup(this, 1);
                    this.setDead();
                }
            }
        }
        return isDead;
    }

    @Override
    protected void kill() { //only voided, normally
        if (posY < -64 && loyal > 0 && Config.loyal_cannot_void)
            lanyardYank(); //don't void out if loyal
        else
            super.kill();
    }

    protected void lanyardYank() { //return to shooter
        impactDamage = 0;
        ticksFlying = 0;
        initial_magnitude = -1;
        returning = true;
        inGround = false;
        this.rotationPitch = 45.0F; this.rotationYaw = 45.0F;
        if (posY < -64) posY = -63.9;
        //setSize(1, 1);
    }

    public boolean isReturning() {
        return returning;
    }

    public static DamageSource causeTridentDamage(Entity trident, Entity shooter){
        return (new EntityDamageSourceIndirect("trident", trident, shooter)).setProjectile();
    }

    @Override
    public void onUpdate() { //substantially Arrow but modified
        onEntityUpdate();

        if (this.stuckTwangTicks > 0)
        {
            --this.stuckTwangTicks;
        }
        else if (this.returning && shootingEntity != null) {
            double dX = shootingEntity.posX - this.posX;
            double dY = shootingEntity.posY + shootingEntity.yOffset + shootingEntity.getEyeHeight() - this.posY;
            double dZ = shootingEntity.posZ - this.posZ;
            double magnitude = MathHelper.sqrt_double(dX*dX + dY*dY + dZ*dZ);
            if (initial_magnitude <= 0) initial_magnitude = (int)magnitude;
            if (magnitude < 1 && shootingEntity instanceof EntityPlayer p) {
                onCollideWithPlayer(p);
            }
            int ticksForReturnArc = ticksFlying;
            int ticksForFlyspeed = ticksForReturnArc = Math.max(0,Math.min(ticksForReturnArc, 40));
            this.motionX = 0.02 * loyal * ticksForFlyspeed * dX / magnitude;
            this.motionY = 0.02 * loyal * ticksForFlyspeed * dY / magnitude;
            if (ticksFlying < 10) this.motionY += .0125 * (40 - ticksFlying * 4);
            this.motionZ = 0.02 * loyal * ticksForFlyspeed * dZ / magnitude;
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI) + 135F;
        }

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / Math.PI);
        }

        Block blockHere = this.worldObj.getBlock(this.stuckX, this.stuckY, this.stuckZ);

        if (!this.returning && blockHere.getMaterial() != Material.air && blockHere.getMaterial() != Material.water) //tridents don't get stuck in water
        {
            blockHere.setBlockBoundsBasedOnState(this.worldObj, this.stuckX, this.stuckY, this.stuckZ);
            AxisAlignedBB axisalignedbb = blockHere.getCollisionBoundingBoxFromPool(this.worldObj, this.stuckX, this.stuckY, this.stuckZ);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }


        if (this.inGround)
        {
            int j = this.worldObj.getBlockMetadata(this.stuckX, this.stuckY, this.stuckZ);

            if (blockHere == this.stuckBlock && j == this.stuckMeta)
            {
                ++this.ticksStuck;
                if (loyal > 0 && this.ticksStuck > 10 - 3 * loyal){
                    lanyardYank();
                }
                else {
                    List<EntityLivingBase> nearbies = this.worldObj.getEntitiesWithinAABB (EntityLivingBase.class, this.boundingBox.expand(1, 0.5, 1));
                    for (EntityLivingBase elb: nearbies){
                        if (pickedUpBy(elb)) //that also
                            return;
                    }
                }

                if (this.ticksStuck >= 20 * (60 * DESPAWN_MINUTES))
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.ticksStuck = 0;
                this.ticksFlying = 0;
                if (loyal > 0)
                    lanyardYank();
            }
        }
        else
        {
            ++this.ticksFlying;
            Vec3 yogiBerra = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 whitherYouGo = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(yogiBerra, whitherYouGo, false, true, false);
            yogiBerra = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            whitherYouGo = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                whitherYouGo = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity frontHit = null;
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double shortestDist = 0.0D;
            int i;
            float pointy_end_size;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entityMaybeHit = list.get(i);

                if (this.returning){
                    if (entityMaybeHit == shootingEntity)
                        frontHit = entityMaybeHit;
                }
                else if (entityMaybeHit.canBeCollidedWith() && (entityMaybeHit != this.shootingEntity || (this.ticksFlying >= 5 && this.loyal == 0)))
                {
                    pointy_end_size = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entityMaybeHit.boundingBox.expand(pointy_end_size, pointy_end_size, pointy_end_size);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(yogiBerra, whitherYouGo);

                    if (movingobjectposition1 != null)
                    {
                        double curDist = yogiBerra.distanceTo(movingobjectposition1.hitVec);

                        if (curDist < shortestDist || shortestDist == 0.0D)
                        {
                            frontHit = entityMaybeHit;
                            shortestDist = curDist;
                        }
                    }
                }
            }

            if (frontHit != null)
            {
                movingobjectposition = new MovingObjectPosition(frontHit);
            }

            if (movingobjectposition != null) {
                if (movingobjectposition.entityHit instanceof EntityPlayer entityplayer) {
                    if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
                        movingobjectposition = null;
                    }
                    if (this.returning && entityplayer == this.shootingEntity) {
                        this.onCollideWithPlayer(entityplayer);
                    }
                } else if (pickedUpBy(movingobjectposition.entityHit)) {
                    return; //don't collide further
                }
            }

            float vector_magnitude;
            float lateral_magnitude;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null && willHit)
                {
                    vector_magnitude = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);

                    DamageSource damagesource = (this.shootingEntity == null) ?  causeTridentDamage(this, this)
                            : causeTridentDamage(this, this.shootingEntity);

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                        movingobjectposition.entityHit.setFire(5);

                    boolean blocked = true, shielded = true;
                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, impactDamage))
                    {
                        blocked = shielded = false;
                        this.willHit = Config.trident_allow_entity_multihits;
                        if (movingobjectposition.entityHit instanceof EntityLivingBase entitylivingbase)
                        {
                            if (entitylivingbase instanceof EntityPlayer ep && ep.isBlocking()) blocked = true;
                            if (this.punchiness > 0)
                            {
                                lateral_magnitude = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (lateral_magnitude > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.punchiness * 0.6D / (double)lateral_magnitude,
                                        0.1D, this.motionZ * (double)this.punchiness * 0.6D / (double)lateral_magnitude);
                                }
                            }

                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
                            {
                                EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity); //apply on-striking
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, entitylivingbase); //apply on-stricken
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity
                                && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP epMP)
                            {
                                epMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                                // sound of being hit
                            }
                        }
                        if (!blocked) //TODO: sounds sword.trident.blocked
                            this.playSound("undertow:trident.hit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
//                        else if (!shielded)
//                            this.playSound("sword.blocked", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
                        {
                            this.motionX *= hitBounceVector.xCoord;
                            this.motionY *= hitBounceVector.yCoord * (this.motionY > 0 ? 1 : -1);
                            this.motionZ *= hitBounceVector.zCoord;
                            this.rotationYaw += 180.0F;
                            this.prevRotationYaw += 180.0F;
                            this.ticksFlying = 0;
                            if (loyal > 0) lanyardYank();
                        }
                    }
                    else
                    {
                        this.motionX *= -0.1D;
                        this.motionY *= -0.1D;
                        this.motionZ *= -0.1D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksFlying = 0;
                        if (loyal > 0) lanyardYank();
                    }
                }
                else if (!this.returning)
                {
                    this.stuckX = movingobjectposition.blockX;
                    this.stuckY = movingobjectposition.blockY;
                    this.stuckZ = movingobjectposition.blockZ;
                    this.stuckBlock = this.worldObj.getBlock(this.stuckX, this.stuckY, this.stuckZ);
                    this.stuckMeta = this.worldObj.getBlockMetadata(this.stuckX, this.stuckY, this.stuckZ);
                    this.motionX = movingobjectposition.hitVec.xCoord - this.posX;
                    this.motionY = movingobjectposition.hitVec.yCoord - this.posY;
                    this.motionZ = movingobjectposition.hitVec.zCoord - this.posZ;
                    vector_magnitude = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)vector_magnitude * 0.05D;
                    this.posY -= this.motionY / (double)vector_magnitude * 0.05D;
                    this.posZ -= this.motionZ / (double)vector_magnitude * 0.05D;
                    this.playSound("random.bowhit", 1.0F, .8F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.stuckTwangTicks = 7;

                    if (this.stuckBlock.getMaterial() != Material.air)
                    {
                        this.stuckBlock.onEntityCollidedWithBlock(this.worldObj, this.stuckX, this.stuckY, this.stuckZ, this);
                    }
                }
                if (this.zeustarget > 0 && !returning && !this.worldObj.isRemote
                    && !(movingobjectposition.entityHit != null && movingobjectposition.entityHit != shootingEntity) ) {
                    if ((!(movingobjectposition.entityHit == null && Config.channeling_triggers_on_allblocks) || EnchantmentChanneling.strikableBlock(stuckBlock))
                        && EnchantmentChanneling.canStrikeAt(worldObj, this)) {
                        if (Config.drowned_make_channeling_strikes || !(this.shootingEntity instanceof EntityDrowned))
                            this.worldObj.addWeatherEffect(new EntityCalledLightning(this.worldObj, posX, posY,  posZ, (EntityLivingBase) shootingEntity));
                    }
                }
                if (this.inGround) this.willHit = Config.trident_allow_bedrock_multihits;
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            vector_magnitude = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, vector_magnitude) * 180.0D / Math.PI);
                 this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {}

            if (this.returning) {
                this.rotationYaw += 225;
                this.rotationPitch += 30;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f3 = 0.99F;

            if (this.isInWater())
            {
                for (int l = 0; l < 4; ++l)
                {
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)0.25F,
                        this.posY - this.motionY * (double)0.25F,
                        this.posZ - this.motionZ * (double)0.25F, this.motionX, this.motionY, this.motionZ);
                }

 //               f3 = 0.8F; //goes cleanly through water
            }

            if (this.isWet())
            {
                this.extinguish();
            }

            this.motionX *= f3;
            this.motionY *= f3;
            this.motionZ *= f3;
            this.motionY -= GRAVITY_DROP;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I(); //handle block hit
        }
    }

    public void setStack(ItemStack stack) {
        thingy = stack.copy();
        populate_fields_from_stack(thingy);
    }

    public ItemStack getStack(){
        return thingy;
    }

    @Override
    public Vec3 getLookVec() {
        float yawCos, yawSin, pitchCos, pitchSin;

        yawCos = MathHelper.cos(this.rotationYaw * 0.017453292F);
        yawSin = MathHelper.sin(this.rotationYaw * 0.017453292F);
        pitchCos = MathHelper.cos(this.rotationPitch * 0.017453292F);
        pitchSin = MathHelper.sin(this.rotationPitch * 0.017453292F);
        return Vec3.createVectorHelper(yawSin * pitchCos, pitchSin, yawCos * pitchCos);

    }

    public boolean isLoyal() { return loyal > 0; }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(Item.getIdFromItem(thingy.getItem()));
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        thingy = new ItemStack(Item.getItemById(additionalData.readInt()));
    }
}
