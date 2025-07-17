package invalid.myask.undertow.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import invalid.myask.undertow.Undertow;
import invalid.myask.undertow.item.IBackportedMap;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RenderItemFrame.class)
public abstract class MixinRenderItemFrame_newMaps extends Render {
    @WrapOperation(method = "doRender",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    Item fakeBeingAFilledMap(ItemStack framed, Operation<Item> original) {
        Undertow.LOG.debug("mixin hit");
        Item realDeal = original.call(framed);
        if (realDeal == Items.filled_map) return realDeal;
        if (realDeal instanceof IBackportedMap newMap && newMap.renderLikeVanillaMapInFrame()) {
            return Items.filled_map;
        }
        return realDeal;
    }
}
