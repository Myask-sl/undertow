package invalid.myask.undertow.mixins;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.item.ItemTrident;
import invalid.myask.undertow.network.PoseChangeMessage;
import invalid.myask.undertow.util.UndertowUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.util.MathHelper.abs;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase_Poses extends Entity implements IUndertowPosableEntity {
    @Unique
    protected int undertow$tilt = 0; //for swimming, enter/exit crawl anim
    @Unique
    protected int undertow$riptideFlyTicks = 1;
    @Unique
    protected float undertow$prevFracTick = 5;
    @Unique
    protected int undertow$maxRiptideTicks = 25;
    @Unique
    protected double undertow$riptideThrust = 0.1;
    @Unique
    protected boolean undertow$spearPosing = false;
    @Unique
    protected boolean undertow$swimming = false;
    @Unique
    protected boolean undertow$crawling = false;
    @Unique
    protected boolean undertow$sneakEnforced = false;
    @Unique
    protected boolean undertow$rippingTide = false;


    @Unique
    protected float initWidth = 0.6F, initHeight = 1.8F, initEyeHeight = 0.9F, initYOffset = 1.62F;
    @Unique
    protected float oldWidth = 0.6F, oldHeight = 1.8F, oldEyeHeight = 0.9F, oldYOffset = 1.62F;
    @Unique
    protected float crawlWidth = 0.6F, crawlHeight = 0.6F, crawlEyeHeight = 0.12F, crawlYOffset = 0.4F;
    @Unique
    protected float sneakWidth = 0.6F, sneakEyeHeight = 0.12F, sneakYOffset = 1.2F;
    @Unique
    private boolean undertow$init = true;
    @Unique
    protected boolean wasSwimmingOrTiding = false;
    @Unique
    protected double undertow$moveCheckOffset = 0;

    @Unique
    private int wasSmall = 0;

    @Unique
    protected boolean undertow$forceMovePacket = false;

    @Unique
    protected boolean undertow$handRenderFlag = false;

    public MixinEntityLivingBase_Poses(World worldIn) {
        super(worldIn);
    }

    @Override
    public int undertow$getTilt() {
        return undertow$tilt;
    }

    @Override
    public int undertow$incTilt(int max) {
        undertow$tilt++;
        if (undertow$rippingTide()) undertow$tilt += 2;
        if (undertow$tilt >= max) undertow$tilt = max;
        return undertow$tilt;
    }

    @Override
    public int undertow$decTilt(int min) {
        if (undertow$tilt <= min) undertow$tilt = min;
        else undertow$tilt--;
        return undertow$tilt;
    }

    @Override
    public int undertow$getFlyingTicks() {
        return undertow$riptideFlyTicks;
    }

    @Override
    public int undertow$resetFlyingTicks() {
        undertow$riptideFlyTicks = 0;
        return undertow$riptideFlyTicks;
    }

    @Override
    public int undertow$incFlyingTicks() {
        return ++undertow$riptideFlyTicks;
    }

    @Override
    public int undertow$getMaxFlyTicks() {
        return undertow$maxRiptideTicks;
    }

    @Override
    public void undertow$setMaxFlyTicks(int newz) {
        undertow$maxRiptideTicks = newz;
    }

    @Override
    public double undertow$getFlyThrust() {
        return undertow$riptideThrust;
    }

    @Override
    public void undertow$setFlyThrust(double newDV) {
        undertow$riptideThrust = newDV;
    }

    @Override
    public float undertow$getPrevFracTick() {
        return undertow$prevFracTick;
    }

    @Override
    public void undertow$setPrevFracTick(float newz) {
        undertow$prevFracTick = newz;
    }

    @Override
    public boolean undertow$isSpearPosing() {
        if (((EntityLivingBase) (Entity) this) instanceof EntityDrowned mermo) {
            undertow$spearPosing = mermo.spearPosing();
        } else if (((EntityLivingBase) (Entity) this) instanceof EntityPlayer groucho) {
            undertow$spearPosing = groucho.getItemInUse() != null && groucho.getItemInUse().getItem() instanceof ItemTrident;
        }
        else undertow$spearPosing = false;
        return undertow$spearPosing;
    }

    @Override
    public boolean undertow$isSwimming() {
        return undertow$swimming;
    }

    @Override
    public void undertow$setSwimming(boolean art) {
        undertow$swimming = art;
    }

    @Override
    public boolean undertow$rippingTide() {
        return undertow$rippingTide;
    }

    @Override
    public void undertow$setTideRipping(boolean art) {
        if (!Config.drowned_fly_riptide_tridents && ((EntityLivingBase) (Object) this) instanceof EntityDrowned) art = false;
        undertow$rippingTide = art;
    }

    @Override
    public boolean undertow$isCrawling() {
        return undertow$crawling;
    }

    public void undertow$setCrawling(boolean art) {
        if ((EntityLivingBase) (Object) this instanceof EntityPlayer) { if (!Config.crawl_player_enable) art = false; }
        else if ((EntityLivingBase) (Object) this instanceof EntityCreature) {
            if (!Config.crawl_biped_mobs_enable) art = false;
            else if (!(UndertowUtils.isBiped((EntityLivingBase) (Object) this)))
                    art = false;
        }
        this.undertow$crawling = art;
    }

    @Override
    public boolean undertow$forcingSneak() {
        return undertow$sneakEnforced;
    }

    @Override
    public void undertow$forceToSneak(boolean art) {
        undertow$sneakEnforced = art;
    }

    @Override
    public void undertow$setSmallPoseSizeEtC(float h, float w, float eH, float yOff) {
        crawlHeight = h; crawlWidth = w; crawlEyeHeight = eH; crawlYOffset = yOff;
    }

    public float undertow$getSmallWidth() { return crawlWidth; }
    public float undertow$getSmallHeight() { return crawlHeight; }
    public float undertow$getSmallEyeHeight() { return crawlEyeHeight; }
    public float undertow$getSmallYOffset() {
        if ((Entity) this instanceof EntityPlayerMP || (Entity)this instanceof EntityOtherPlayerMP) return 0;// crawlYOffset - 1.62F;
        return crawlYOffset;
    }

    public float undertow$getSneakYOffset() {
        if ((Entity) this instanceof EntityPlayerMP || (Entity)this instanceof EntityOtherPlayerMP) return 0;// sneakYOffset - 1.62F;
        return sneakYOffset;
    }

    public double undertow$getCurrentYDifference() {/*
        if (undertow$isCrawling()) return crawlYOffset - 1.62D;
        else if (isSneaking()) return sneakYOffset - 1.62D + 0.25D;
        else return 0;*/
        return switch (undertow$smallPosed()) {
            case 2 -> crawlYOffset - 1.62D;
            case 1 -> sneakYOffset - 1.62D + 0.25D;
            default -> 0;
        };
    }

    public float undertow$getOldWidth() { return oldWidth; }
    public float undertow$getOldHeight() { return oldHeight; }
    public float undertow$getOldEyeHeight() { return oldEyeHeight; }
    public float undertow$getOldYOffset() { return oldYOffset; }

    public boolean undertow$forcingMovePacket() {return undertow$forceMovePacket;}

    @Override
    public void undertow$setHandRendering() {
        undertow$handRenderFlag = true;
    }

    @Override
    public boolean undertow$clearHandRendering() {
        boolean temp = undertow$handRenderFlag;
        undertow$handRenderFlag = false;
        return temp;
    }

    @Override
    public int undertow$smallPosed() {
        return (undertow$crawling || undertow$rippingTide || undertow$swimming) ? 2 : isSneaking() ? 1 : 0;
    }

    private void updateBox(EntityLivingBase entLi) {
        int shallSmall = undertow$smallPosed();
//        boolean otherSmall = oldHeight == 0.6F;
        if (entLi instanceof EntityPlayer eP) {//TODO: elytra for shallSmall
            if (eP.isPlayerSleeping()) return; ///does this version even make player small then?
        }

 /*       if ((oldHeight != height || oldWidth != width) && !wasSmall && !otherSmall) {
            oldHeight = height; oldWidth = width; oldEyeHeight = getEyeHeight(); oldYOffset = yOffset;
        }
        if (shallSmall && !wasSmall) {
            setSize(crawlWidth, crawlHeight);
            maybeSetEyeHeight(crawlEyeHeight);
            yOffset = crawlYOffset;
        }
        else if (wasSmall && !shallSmall) {
            setSize(oldWidth, oldHeight);
            maybeSetEyeHeight(oldEyeHeight);
            yOffset = oldYOffset;
        } */
        undertow$moveCheckOffset = 0;
        if (shallSmall != wasSmall) {
            float newHeight = switch(shallSmall) {
                case 1 -> undertow$sneakHeight();
                case 2 -> crawlHeight;
                default -> initHeight;//0,1
            };
            float newWidth = switch(shallSmall) {
                case 1 -> sneakWidth;
                case 2 -> crawlWidth;
                default -> initWidth;//0, 1
            };
            setSize(newWidth, newHeight);
            oldYOffset = yOffset;
            yOffset = switch(shallSmall) {
                case 1 -> undertow$getSneakYOffset();
                case 2 -> undertow$getSmallYOffset();
                default -> initYOffset; //0, 1
            };

            maybeSetEyeHeight(switch(shallSmall) {
                case 1 -> sneakEyeHeight;
                case 2 -> crawlEyeHeight;
                default -> initEyeHeight; //0,1
            });
            double dY = Config.prevent_swim_riptide_posemove && (wasSwimmingOrTiding || undertow$swimming || undertow$rippingTide)
                ? 0 : (yOffset - oldYOffset);
            undertow$moveCheckOffset = (dY > 0.0625) ? dY : 0;
            undertow$forceMovePacket = true;
            if (abs((float) dY) > 0.01) dY += 0.01;
            this.setPosition(posX, posY + dY, posZ); //incidentally fixes up BB
        }
        else undertow$forceMovePacket = false;
        wasSmall = shallSmall; //||otherSmall
        wasSwimmingOrTiding = undertow$swimming || undertow$rippingTide;
    }

    private void maybeSetEyeHeight(float newEyeHeight) {
        if ((Object) this instanceof EntityPlayer eP) {
            eP.eyeHeight = newEyeHeight;
        }
    }
    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void undertow$reBox(CallbackInfo starsixnine) {
        if (undertow$init) { //first tick's value persists. May bug.
            initEyeHeight = oldEyeHeight = getEyeHeight();
            initHeight = oldHeight = height;
            initWidth = oldWidth = width;
            initYOffset = oldYOffset = yOffset;
            undertow$init = false;
        }
        undertow$heightPoseCheck(((EntityLivingBase) (Object)this));
        updateBox((EntityLivingBase) (Object)this);
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V",
        ordinal = 0, shift = At.Shift.AFTER))
    private void undertow$onUpdate(CallbackInfo starsixnine) {
        if (undertow$smallPosed() == 2) {
            if (undertow$getTilt() < FULL_TILT) undertow$incTilt(FULL_TILT);
        } else if (undertow$getTilt() > 0) undertow$decTilt(0);
        if (undertow$getTilt() == 0)
            undertow$resetFlyingTicks(); //post-riptide, so.
        else if (undertow$rippingTide()) {
            undertow$incFlyingTicks();
        }
        if (undertow$rippingTide) {
            Vec3 lookVec = this.getLookVec();
            if (lookVec == null) return;
            lookVec.normalize();
            lookVec = UndertowUtils.scalarMult(lookVec, undertow$getFlyThrust());
            if (!Config.riptide_impulse_instant || undertow$getFlyingTicks() < 2)
                this.addVelocity(lookVec.xCoord, lookVec.yCoord * Config.riptide_y_mult, lookVec.zCoord);
            if (undertow$riptideFlyTicks++ > undertow$maxRiptideTicks) {
                undertow$rippingTide = false;
            }
        }
    }

@Shadow
    public float renderYawOffset, prevRenderYawOffset, rotationYawHead, prevRotationYawHead;

    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void undertow$deYawCrawlPoseBody(CallbackInfo starsixnine) {
        if (!Config.de_yaw_crawl_pose) return;
        this.prevRenderYawOffset += (this.prevRotationYawHead - this.prevRenderYawOffset) * undertow$getTilt() / FULL_TILT; //Finally, fix the backup/strafe bit.
        this.renderYawOffset += (this.rotationYawHead - this.renderYawOffset) * undertow$getTilt() / FULL_TILT;
    }

    private void undertow$heightPoseCheck(EntityLivingBase entityLivingBase) {
        if (!Config.crawl_biped_mobs_enable && Config.sneak_biped_ratio == 1.0
            && entityLivingBase instanceof EntityCreature) return;
        if (!Config.crawl_player_enable && Config.sneak_player_height == initHeight) return;
        if (!(entityLivingBase instanceof EntityPlayer) && !UndertowUtils.isBiped(entityLivingBase)) return;
        float[] hChk = {0, crawlHeight, 1.01F, undertow$sneakHeight(), initHeight};
        float[] wChk = {crawlWidth, initWidth, initWidth, initWidth};
        float[] yChk = {this.undertow$getSmallYOffset() + this.undertow$getSmallEyeHeight(), initYOffset + initEyeHeight,
            initYOffset + initEyeHeight, initYOffset + initEyeHeight};
        int hInd = 0; //shouts out to all those hWnd fans

        breakout:
        for (; hInd < 4; hInd++) {
//            for (int i = 0; i < 4; ++i) {
//                double fx = (float) (this.posX + (((float) ((i >> 0) % 2) - 0.5F) * wChk[hInd] * 0.8F));
                double w = wChk[hInd] * 0.4F;
//                double fzed = (float) (this.posZ + (((float) ((i >> 1) % 2) - 0.5F) *  wChk[hInd] * 0.8F));
                AxisAlignedBB thisHighToUnpose = AxisAlignedBB.getBoundingBox(
                    this.posX - w, this.boundingBox.minY + hChk[hInd], this.posZ - w,
                    this.posX + w, this.boundingBox.minY + hChk[hInd + 1], this.posZ + w);
                if (!worldObj.func_147461_a(thisHighToUnpose).isEmpty()) //block collision boxes
                    break breakout;
        }
        switch (hInd) {
            case 1, 2:
                this.undertow$setCrawling(true); break;
            case 3:
                this.undertow$forceToSneak(true);
                if (Config.crawl_player_autostand || !((Object) this instanceof EntityPlayer))
                    this.undertow$setCrawling(false);
                break;
            case 4:
                if (Config.crawl_player_autostand || !((Object) this instanceof EntityPlayer))
                    this.undertow$setCrawling(false); //TODO: swap out to forceToCrawl
            default:
                this.undertow$forceToSneak(false);
                //Suffocating or wholly clear
        }
    }

    private float undertow$sneakHeight() {
        if ((Object)this instanceof EntityPlayer) return Config.sneak_player_height;
        else if (UndertowUtils.isBiped((EntityLivingBase) (Object)this)) return initHeight * Config.sneak_biped_ratio;
        return initHeight;
    }

    @Override
    public void undertow$eatPacket(PoseChangeMessage message) {
        undertow$crawling = message.crawl;
        undertow$swimming = message.swim;
        undertow$rippingTide = message.ripping;
        undertow$riptideFlyTicks = message.ripticks;
        undertow$maxRiptideTicks = message.max_ripticks;
    }

/*    @ModifyReturnValue(method = "isSneaking()Z",
        at = @At("RETURN"))
    private boolean forceSneakOn (boolean original) {
 */
    @Override
    public boolean isSneaking() {
        return undertow$sneakEnforced || super.isSneaking();
    }

//    @Inject(method="writeToNBT(Lnet/minecraft/nbt/NBTTagCompound;)V", at = @At("TAIL"))
//    private void undertow$writeToNBT(CallbackInfo cba, @Local (argsOnly = true, ordinal = 0)NBTTagCompound compound) {
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("undertow$crawling", undertow$crawling);
        compound.setBoolean("undertow$swimming", undertow$swimming);

        compound.setBoolean("undertow$rippingtide", undertow$rippingTide);
        compound.setInteger("undertow$ripticks", undertow$riptideFlyTicks);
        compound.setInteger("undertow$maxripticks", undertow$maxRiptideTicks);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        undertow$crawling = compound.getBoolean("undertow$crawling");
        undertow$swimming = compound.getBoolean("undertow$swimming");

        undertow$rippingTide = compound.getBoolean("undertow$rippingtide");
        wasSmall = undertow$smallPosed();
        wasSwimmingOrTiding = undertow$rippingTide || undertow$swimming;
        undertow$riptideFlyTicks = compound.getInteger("undertow$ripticks");
        int i = compound.getInteger("undertow$maxripticks");
        undertow$maxRiptideTicks = (i == 0) ? undertow$maxRiptideTicks : i; //default set at declaration
    }
}
