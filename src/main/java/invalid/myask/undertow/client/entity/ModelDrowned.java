package invalid.myask.undertow.client.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.item.ItemTrident;

public class ModelDrowned extends ModelZombie {
    public ModelDrowned(float inflate, boolean half64tex) {
        super(inflate, half64tex);
    }
    public ModelDrowned() {
        this(0, false);
    }

    @Override
    public void setRotationAngles(float armSwingT, float swingMax, float p_78087_3_, float yaw, float pitch, float rollQ, Entity entity) {
        super.setRotationAngles(armSwingT, swingMax, p_78087_3_, yaw, pitch, rollQ, entity);
        if (!(entity instanceof EntityDrowned drowned)) return;
        ModelRenderer mainArm = this.bipedRightArm, offArm = this.bipedLeftArm;
//        if (drowned instanceof IHandedBiped lefty && !lefty.iAmNotNotLeftHanded) {
//            mainArm = offArm; offArm = this.bipedRightArm;
//        }   //TODO: lefties
        if (drowned.hasNautilus()) {
            offArm.rotateAngleX = MathHelper.cos(armSwingT * 0.6662F) * swingMax * 0.5F - ((float)Math.PI / 10F);
        }
        if (drowned.getEquipmentInSlot(0) != null && drowned.getEquipmentInSlot(0).getItem() instanceof ItemTrident) {
            mainArm.rotateAngleX = MathHelper.cos(armSwingT * 0.6662F + (float)Math.PI) * swingMax * 0.5F;
            if (drowned.spearPosing()) mainArm.rotateAngleX += (float)Math.PI;
        }
    }
}
