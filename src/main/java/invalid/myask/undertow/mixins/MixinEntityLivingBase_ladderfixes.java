package invalid.myask.undertow.mixins;

import invalid.myask.undertow.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase_ladderfixes extends Entity {
    public MixinEntityLivingBase_ladderfixes(World worldIn) {super(worldIn);}

    @ModifyConstant(method = "moveEntityWithHeading", constant = @Constant(doubleValue = -0.15D))
    private double undertow$fastfall (double original) {
        if (((Object) this instanceof EntityPlayer) && this.rotationPitch > Config.ladder_fastfall_pitch) {
            return original * 2;
        }
        return original;
    }

    @ModifyVariable(method = "moveEntityWithHeading",
        at = @At("LOAD"), name = "flag")
    private boolean undertow$stopGUI (boolean original) {
        if (((Object) this instanceof EntityPlayerSP) && Minecraft.getMinecraft().currentScreen != null)
            return true;
        return original;
    }
}
