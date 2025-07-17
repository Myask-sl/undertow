package invalid.myask.undertow.ducks;

import net.minecraft.world.World;

public interface IMapGenStructure_Accessor {
    boolean undertow$canSpawnStructureAtCoords(int x, int y);
    void assureWorld(World w);
}
