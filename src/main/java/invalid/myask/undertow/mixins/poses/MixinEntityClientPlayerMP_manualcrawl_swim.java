package invalid.myask.undertow.mixins.poses;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Session;
import net.minecraft.world.World;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.client.settings.UndertowKeybinds;
import invalid.myask.undertow.ducks.IUndertowPosableEntity;

@Mixin(EntityClientPlayerMP.class)
public class MixinEntityClientPlayerMP_manualcrawl_swim extends EntityPlayerSP {
    @Unique
    private int undertow$sneakDoublingTimer = 0;
    @Unique
    private boolean undertow$manuallyCrawled = false;
    @Unique
    private boolean undertow$lastManuallyCrawled = false;

    public MixinEntityClientPlayerMP_manualcrawl_swim(Minecraft mc, World w, Session sesh, int dimension) {
        super(mc, w, sesh, dimension);
    }

    @Shadow
    private boolean wasSneaking;

    @Override
    public void onLivingUpdate() {
        //head
        super.onLivingUpdate();
        //tail
        if (Config.crawl_player_manual) {
            if (Config.crawl_player_manual_doublesneak_window > 0) {
                if (undertow$sneakDoublingTimer > 0) undertow$sneakDoublingTimer--;
                if (movementInput.sneak && !wasSneaking) {
                    if (undertow$sneakDoublingTimer <= 0) { // && Config.enable_doublesneak_crawl) { //removed in favor of -1 window disable
                        undertow$sneakDoublingTimer = Config.crawl_player_manual_doublesneak_window;
                    } else {//in double-tap window
                        undertow$manuallyCrawled = !undertow$manuallyCrawled;
                        undertow$sneakDoublingTimer = 0; //do this ONCE.
                    }
                }
            }
            if (Config.enable_crawl_keybind) {
                if (Config.key_toggles_crawl) {
                    if (UndertowKeybinds.crawl.isPressed())
                        undertow$manuallyCrawled = !undertow$manuallyCrawled;
                }
                else undertow$manuallyCrawled = UndertowKeybinds.crawl.getIsKeyPressed();
            }
            if (undertow$manuallyCrawled) setSprinting(false);
            //TODO: soft_toggle_crawl--disable when in lava/water?
            if (undertow$manuallyCrawled != undertow$lastManuallyCrawled)
                ((IUndertowPosableEntity) this).undertow$setCrawling(undertow$manuallyCrawled);
            undertow$lastManuallyCrawled = undertow$manuallyCrawled;
        }
        if (Config.enable_swim) { //FIXME: investigate swimstate stuttering??
            ((IUndertowPosableEntity) this).undertow$setSwimming(isSprinting() && isInsideOfMaterial(Material.water));
        }
    }
}
