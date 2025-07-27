package invalid.myask.undertow.mixins.maplocate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;

import invalid.myask.undertow.ducks.IMapGenStructure_Accessor;
import invalid.myask.undertow.util.UndertowUtils;

@Mixin(MapGenStructure.class)
public abstract class MixinMapGenStructure_canGenAtAccessor extends MapGenBase implements IMapGenStructure_Accessor {
    @Shadow
    protected abstract boolean canSpawnStructureAtCoords(int x, int y);

    @Unique
    String undertow$capturedModID;
    @Inject(method = "<init>",
        at = @At("TAIL"))
    private void undertow$noteMod(CallbackInfo ci) {
        ModContainer mc = Loader.instance().activeModContainer();
        undertow$capturedModID = mc == null ? "minecraft" : mc.getModId();
        UndertowUtils.noteStructGen((MapGenStructure) (Object) this);
    }

    public String undertow$getModId() {
        return undertow$capturedModID;
    }

    public boolean undertow$canSpawnStructureAtCoords(int x, int y) {
        return canSpawnStructureAtCoords(x, y);
    }

    @Override
    public void undertow$assureWorld(World w) {
        if (worldObj == null) worldObj = w;
    }
}
