package invalid.myask.undertow.client.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.entities.ProjectileTrident;
import invalid.myask.undertow.item.ItemTrident;
import invalid.myask.undertow.util.UndertowUtils;

public class RenderTrident extends Render implements IItemRenderer {
    public static final RenderTrident instance = new RenderTrident();
    public static final ResourceLocation TRITEX = new ResourceLocation(Undertow.MODID, "textures/entity/trident.png");
    public static final ResourceLocation SPEARTEX = new ResourceLocation(Undertow.MODID, "textures/entity/spear_flint.png");

    private static final Vec3 EQUIP_FP_POS = Vec3.createVectorHelper(0.0, -.3 ,0.85);
    private static final float EQUIP_FP_YAW = 75;
    private static final float EQUIP_FP_PITCH = -5;
    private static final float EQUIP_FP_ROLL = -10;
    private static final float EQUIP_FP_SCALE = 2;

    private static final Vec3 USING_FP_POS = Vec3.createVectorHelper(-0.8, 2.2, 1.0);
    private static final Vec3 USING_FP_TICK_POS_DELTA = Vec3.createVectorHelper(-0.07, 0, 0.015);

    private static final float USING_FP_UNYAW = -60;
    private static final float USING_FP_YAW = 0;
    private static final float USING_FP_PITCH = -90;
    private static final float USING_FP_ROLL = -90;

    private static final Vec3 RIPTIDE_FP_POS = Vec3.createVectorHelper(-.8, 1.2, .9);
    private static final float RIPTIDE_FP_YAW = -60;
    private static final float RIPTIDE_FP_PITCH = -90;
    private static final float RIPTIDE_FP_ROLL = -90;

    private static final Vec3 USING_3P_POS = Vec3.createVectorHelper(-0.2, 0.5, 0.9);
    private static final float USING_3P_YAW = 0;
    private static final float USING_3P_PITCH = 180;
    private static final float USING_3P_ROLL = 40;

    private static final Vec3 RIPTIDE_3P_POS = Vec3.createVectorHelper(0.5, 0.0, 1.5);
    private static final float RIPTIDE_3P_YAW = 15;
    private static final float RIPTIDE_3P_PITCH = 120;
    private static final float RIPTIDE_3P_ROLL = -30;

    private static final Vec3 EQUIP_3P_POS = Vec3.createVectorHelper(-0.2, 1.0, 0.875);
    private static final float EQUIP_3P_YAW = 45;
    private static final float EQUIP_3P_PITCH = 18;
    private static final float EQUIP_3P_ROLL = -10;
    private static final float EQUIP_3P_SCALE = 2.5F; //1.25F;

    private static final float ENTITY_SCALE = 0.8F;
    private static final double BACKUP_FACTOR = -0.75;

    public static TridentModel blat = new TridentModel();
    static float fracTick;

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float fracTick) {
        doRender((ProjectileTrident) entity, x, y, z, yaw, fracTick);
    }

    protected void doRender(ProjectileTrident dent, double x, double y, double z, float yaw, float fracTick) {
        this.bindEntityTexture(dent);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        Vec3 reverse = dent.getLookVec();
        reverse.normalize();
        reverse = UndertowUtils.scalarMult(reverse, BACKUP_FACTOR);
        if (!dent.isReturning())
            GL11.glTranslated(reverse.xCoord, reverse.yCoord, reverse.zCoord);
        GL11.glRotatef(yaw - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(dent.prevRotationPitch + (dent.rotationPitch - dent.prevRotationPitch) * fracTick - 90F, 0.0F, 0.0F, 1.0F);
  /*      float wiggle = (float)dent.arrowShake - fracTick;
        if(wiggle > 0.0F) {
            float f12 = -MathHelper.sin(wiggle * 3.0F) * wiggle;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
        }
  */
        GL11.glScalef(ENTITY_SCALE, ENTITY_SCALE, ENTITY_SCALE);
        blat.render(dent, 0, 0, 0, 0, fracTick,0.0625F);

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity e) {
        return getEntityTexture((ProjectileTrident) e);
    }

    protected ResourceLocation getEntityTexture(ProjectileTrident dent) {
        return getStackTexture(dent.getStack());
    }
    protected ResourceLocation getStackTexture(ItemStack dentStack) {
        ResourceLocation result = null;
        if (dentStack != null && dentStack.getItem() instanceof ItemTrident theRealDent)
            result = theRealDent.getResLoc();
        if (result == null) result = TRITEX;
        return result;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return switch (type) {
            case INVENTORY, FIRST_PERSON_MAP, ENTITY -> false;
            default -> true;
        };
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return switch (helper) {
            case BLOCK_3D, ENTITY_ROTATION, ENTITY_BOBBING -> false;
            default -> true;
        };
    }

    @SubscribeEvent
    public void grabRenderPartialTick(TickEvent.RenderTickEvent event) {
        fracTick = event.renderTickTime;
    }

    @Override
    protected void bindTexture(ResourceLocation texRL) {
        if (renderManager == null)
            setRenderManager(RenderManager.instance);
        if (renderManager == null || renderManager.renderEngine == null) {
            if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().getTextureManager() != null)
                Minecraft.getMinecraft().getTextureManager().bindTexture(texRL);
        }
        else
            super.bindTexture(texRL);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        switch (type) {
 /*           case ENTITY -> {
                if (data != null && data[0] instanceof ItemTrident trident){
                    blat.bb_main.render(0);
                }
            }*/
            case EQUIPPED_FIRST_PERSON -> {
                this.bindTexture(getStackTexture(item));
                if (data.length >= 2 && data[1] instanceof EntityPlayer alex && alex.isUsingItem()) {
                    float ticks = alex.getItemInUseDuration() + fracTick;
                    if (ticks > 10) ticks = 10;
                    GL11.glTranslated(USING_FP_POS.xCoord + (USING_FP_TICK_POS_DELTA.xCoord * ticks),
                        USING_FP_POS.yCoord+ (USING_FP_TICK_POS_DELTA.yCoord * ticks),
                        USING_FP_POS.zCoord + (USING_FP_TICK_POS_DELTA.zCoord * ticks));
                    GL11.glRotatef(USING_FP_UNYAW, 0, 1, 0);
                    GL11.glRotatef(USING_FP_ROLL, 0, 0,1);
                    GL11.glRotatef(USING_FP_YAW, 0, 1, 0);
                    GL11.glRotatef(USING_FP_PITCH, 1, 0, 0);
                }
                else if (data[1] instanceof EntityLivingBase elb && ((IUndertowPosableEntity)elb).undertow$rippingTide()) {
                    GL11.glTranslated(RIPTIDE_FP_POS.xCoord, RIPTIDE_FP_POS.yCoord, RIPTIDE_FP_POS.zCoord);
                    GL11.glRotatef(RIPTIDE_FP_YAW, 0, 1, 0);
                    GL11.glRotatef(RIPTIDE_FP_ROLL, 0, 0,1);
                    GL11.glRotatef(RIPTIDE_FP_PITCH, 1, 0, 0);
                }
                else {
                    GL11.glTranslated(EQUIP_FP_POS.xCoord, EQUIP_FP_POS.yCoord, EQUIP_FP_POS.zCoord);
                    GL11.glRotatef(EQUIP_FP_YAW, 0, 1, 0);
                    GL11.glRotatef(EQUIP_FP_PITCH, 1, 0, 0);
                    GL11.glRotatef(EQUIP_FP_ROLL, 0, 0,1);
                }
                GL11.glScalef(EQUIP_FP_SCALE, EQUIP_FP_SCALE, EQUIP_FP_SCALE);
                blat.bb_main.render(0.0625F);
            }
            case EQUIPPED -> {
                this.bindTexture(getStackTexture(item));
                if (data.length >= 2 && ((data[1] instanceof EntityPlayer alex && alex.isUsingItem())
                    || data[1] instanceof EntityDrowned eddy && eddy.spearPosing())) {
                    IUndertowPosableEntity undertowPosable = (IUndertowPosableEntity) data[1];
                   // if (undertowPosable.undertow$rippingTide())
                  //  else GL11.glRotatef(180, 1, 0, 0);
                    GL11.glTranslated(USING_3P_POS.xCoord, USING_3P_POS.yCoord, USING_3P_POS.zCoord);
                    GL11.glRotatef(USING_3P_YAW, 0, 1, 0);
                    GL11.glRotatef(USING_3P_PITCH, 1, 0, 0);
                    GL11.glRotatef(USING_3P_ROLL, 0, 0,1);
                }
                else if (data[1] instanceof EntityLivingBase elb && ((IUndertowPosableEntity)elb).undertow$rippingTide()) {
                    //instanceof IUndertowPosableEntity iUTO && iUTO.undertow$rippingTide()) {
                    GL11.glTranslated(RIPTIDE_3P_POS.xCoord, RIPTIDE_3P_POS.yCoord, RIPTIDE_3P_POS.zCoord);
                    GL11.glRotatef(RIPTIDE_3P_YAW, 0, 1, 0);
                    GL11.glRotatef(RIPTIDE_3P_PITCH, 1, 0, 0);
                    GL11.glRotatef(RIPTIDE_3P_ROLL, 0, 0,1);
                }
                else {
                    GL11.glTranslated(EQUIP_3P_POS.xCoord, EQUIP_3P_POS.yCoord, EQUIP_3P_POS.zCoord);
                    GL11.glRotatef(EQUIP_3P_YAW, 0, 1, 0);
                    GL11.glRotatef(EQUIP_3P_PITCH, 1, 0, 0);
                    GL11.glRotatef(EQUIP_3P_ROLL, 0, 0,1);
                }
                GL11.glScalef(EQUIP_3P_SCALE, EQUIP_3P_SCALE, EQUIP_3P_SCALE);
                blat.bb_main.render(0.0625F);
            }
            case FIRST_PERSON_MAP, INVENTORY, ENTITY -> {
            }
        }
        GL11.glPopMatrix();
    }
}
