package invalid.myask.undertow.entities;

import java.util.UUID;

import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.item.ItemTrident;
import invalid.myask.undertow.network.RiptideHitMessage;

public class ProjectileRiptide extends Entity {
    protected EntityLivingBase followed;
    protected String followedUUID;
    protected float impactDamage = 9.0F;
    public ProjectileRiptide(World worldIn) {
        super(worldIn);

        this.setSize(1F, 1F);
    }
    public ProjectileRiptide(EntityLivingBase chosenOne, ItemStack trident) {
        super(chosenOne.worldObj);
        followed = chosenOne;

        this.setSize(1F, 1F);
        follow();
        if (trident != null && trident.getItem() != null)
            impactDamage = ((ItemTrident)trident.getItem()).getTridentDamage();
    }

    public void setFollowed(EntityLivingBase followed) {
        this.followed = followed;
        follow();
    }

    @Override
    protected void entityInit() {
        //renderDistanceWeight = 10;
    }

    @Override
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return false; //followed.isInRangeToRender3d(x, y, z);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompound) {
        posX = tagCompound.getDouble("x");
        posY = tagCompound.getDouble("y");
        posZ = tagCompound.getDouble("z");
        ticksExisted = tagCompound.getInteger("ticksExtant");
        if (tagCompound.hasKey("followed")) {
            followedUUID = tagCompound.getString("followed");
        } else this.kill();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setDouble("x", posX);
        tagCompound.setDouble("y", posY);
        tagCompound.setDouble("z", posZ);
        tagCompound.setInteger("ticksExtant", ticksExisted);
        tagCompound.setString("followed", followed.getPersistentID().toString());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (followed == null && followedUUID != null) {
            UUID folloUUID = UUID.fromString(followedUUID);
            for (Entity e : worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(10,10,10))) {
                if (e instanceof EntityLivingBase elb && elb.getPersistentID().equals(folloUUID)) {
                    followed = elb;
                    break;
                }
            }
        }

        if (followed == null || !((IUndertowPosableEntity)followed).undertow$rippingTide()) {
            this.kill();
            return;
        }
        follow();

        if (ticksExisted <= 1) this.playSound("undertow:trident.riptide", 0.8F, 2);

        boolean hit = false;
        for (Entity entity : worldObj.getEntitiesWithinAABBExcludingEntity(followed, this.boundingBox.addCoord(followed.motionX, followed.motionY, followed.motionZ).expand(1,1,1), new IEntitySelector() {
            @Override
            public boolean isEntityApplicable(Entity vic) {
                return vic instanceof EntityLivingBase; //hurtable. This excludes ProjectileRiptide.this, note.
            }
        })) {
            if (entity instanceof EntityLivingBase vic) {
                DamageSource source = ProjectileTrident.causeTridentDamage(this, followed == null ? this : followed);
                if (vic.attackEntityFrom(source, impactDamage)) {
                    hit = true;
                    if (followed != null) {
                        EnchantmentHelper.func_151384_a(vic, this.followed); //apply on-striking
                        EnchantmentHelper.func_151385_b(this.followed, vic); //apply on-stricken
                        if (followed instanceof EntityPlayerMP epMP && vic instanceof EntityPlayer) {
                            epMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                        }
                        this.posX = vic.posX - followed.motionX;
                        this.posY = vic.posY - followed.motionY;
                        this.posZ = vic.posZ - followed.motionZ;
                    }
                }
            }
        }

        if (hit) {
            followed.motionX *= -1; followed.motionZ *= -1;
            followed.motionY = (followed.motionY * -0.5) + 0.4;
            ((IUndertowPosableEntity)followed).undertow$setTideRipping(false);
            if (followed instanceof EntityPlayerMP eMP) {
                Undertow.networkWrapper.sendTo(new RiptideHitMessage(this.posX, this.posY, this.posZ, followed.motionX, followed.motionY, followed.motionZ), eMP);
            }
            this.playSound("undertow:trident.hit", 0.8F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        }

    }

    private void follow() {
        this.setPosition(followed.posX, followed.posY, followed.posZ);
        this.rotationPitch = followed.rotationPitch;
        this.rotationYaw = followed.rotationYaw;
        this.prevPosX = followed.prevPosX;
        this.prevPosY = followed.prevPosY;
        this.prevPosZ = followed.prevPosZ;
        this.prevRotationPitch = followed.prevRotationPitch;
        this.prevRotationYaw = followed.prevRotationYaw;
    }
}
