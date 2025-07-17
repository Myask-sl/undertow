package invalid.myask.undertow.world;

import cpw.mods.fml.common.IWorldGenerator;
import invalid.myask.undertow.Config;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class UndertowWorldgen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world instanceof WorldClient)
            return;
        if (Config.generate_buried_treasure)
            BuriedTreasureGen.instance.func_151539_a(chunkGenerator, world, chunkX, chunkZ, null);
    }
}
