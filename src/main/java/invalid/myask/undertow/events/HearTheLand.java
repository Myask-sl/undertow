package invalid.myask.undertow.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import invalid.myask.undertow.util.UndertowUtils;

public class HearTheLand {
    public static final HearTheLand instance = new HearTheLand();

    public static void registerEvents() {
        MinecraftForge.TERRAIN_GEN_BUS.register(instance);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void captureStructureGenerators(InitMapGenEvent event) {
        if (event.newGen instanceof MapGenStructure mapGenStructure)
            UndertowUtils.noteStructGen(mapGenStructure);
    }

    @SubscribeEvent
    public void generateStructures(PopulateChunkEvent event) {

    }
}
