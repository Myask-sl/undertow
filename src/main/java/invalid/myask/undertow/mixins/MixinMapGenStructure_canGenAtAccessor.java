package invalid.myask.undertow.mixins;

import invalid.myask.undertow.ducks.IMapGenStructure_Accessor;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MapGenStructure.class)
public abstract class MixinMapGenStructure_canGenAtAccessor extends MapGenBase implements IMapGenStructure_Accessor {
    @Shadow
    protected abstract boolean canSpawnStructureAtCoords(int x, int y);

    public boolean undertow$canSpawnStructureAtCoords(int x, int y) {
        return canSpawnStructureAtCoords(x, y);
    }

    @Override
    public void assureWorld(World w) {
        if (worldObj == null) worldObj = w;
    }
}
