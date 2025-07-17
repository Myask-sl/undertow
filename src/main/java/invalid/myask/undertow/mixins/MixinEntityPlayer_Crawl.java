package invalid.myask.undertow.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer_Crawl extends EntityLivingBase { //TODO: delete

    @Unique
    private boolean manuallyStand = false;

    public MixinEntityPlayer_Crawl(World p_i1594_1_) { super(p_i1594_1_); }

//    @Inject(method = "setSneaking", at = @At("TAIL")) //sneakEndFlag
    @Override
    public void setSneaking(boolean shallSneak) { //@Local
        manuallyStand = !shallSneak;
        super.setSneaking(shallSneak);
    }

    /*
    @ModifyReturnValue(method = "isEntityInsideOpaqueBlock",
        at = @At("RETURN") )
    private boolean suffocateOrCrawl(boolean previousReturn) { //TODO:  config mob crawls
        if (!Config.crawl_player_enable) return previousReturn;
        boolean newReturn = previousReturn;
        //previous return is !sleeping and checking all four corners...0.05 up and down from eyeheight.

        IUndertowPosableEntity mcClane = (IUndertowPosableEntity) this;
        if (this.isEntityInsideOpaqueBlock() && !mcClane.undertow$smallPosed()) {
            //Only check bottom block if top brick occupied.
            boolean shallCrawl = true;
            for (int i = 0; i < 4; ++i) {
                float fx = ((float) ((i >> 0) % 2) - 0.5F) * mcClane.undertow$getSmallWidth() * 0.8F;
                float fzed = ((float) ((i >> 1) % 2) - 0.5F) *  mcClane.undertow$getSmallWidth() * 0.8F;
                int x = MathHelper.floor_double(this.posX + (double) fx);
                int wy = MathHelper.floor_double(this.posY - yOffset + mcClane.undertow$getSmallYOffset() + (double) mcClane.undertow$getSmallEyeHeight());
                int zed = MathHelper.floor_double(this.posZ + (double) fzed);

                if (this.worldObj.getBlock(x, wy, zed).isNormalCube()) {
                    shallCrawl = false; break;
                }
            }
            ((IUndertowPosableEntity)this).undertow$setCrawling(shallCrawl);
            newReturn = !shallCrawl;
        }
        else if (!previousReturn && !this.isPlayerSleeping()
            && (Config.crawl_player_autostand || manuallyStand)) {
            manuallyStand = false;
            boolean shallStand = true;
            for (int i = 0; i < 4; ++i) {
                float fx = ((float) ((i >> 0) % 2) - 0.5F) * mcClane.undertow$getOldWidth() * 0.8F;
                float fzed = ((float) ((i >> 1) % 2) - 0.5F) * mcClane.undertow$getOldWidth() * 0.8F;

                int x = MathHelper.floor_double(this.posX + (double) fx);
                int wy = MathHelper.floor_double(this.posY - yOffset + mcClane.undertow$getOldYOffset() + (double) mcClane.undertow$getOldEyeHeight() );
                int zed = MathHelper.floor_double(this.posZ + (double) fzed);

                if (this.worldObj.getBlock(x, wy, zed).isNormalCube()) {
                    shallStand = false; break;
                }
            }
            mcClane.undertow$setCrawling(!shallStand);
        }
        return newReturn;
    } */
}
