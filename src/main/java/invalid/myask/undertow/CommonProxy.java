package invalid.myask.undertow;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

import invalid.myask.undertow.command.CommandGiveMapTo;
import invalid.myask.undertow.command.CommandLocate;
import invalid.myask.undertow.command.CommandLocateBiome;
import invalid.myask.undertow.events.HearTheLand;
import invalid.myask.undertow.events.HearTheOcean;
import invalid.myask.undertow.network.PoseChangeHandler;
import invalid.myask.undertow.network.PoseChangeMessage;
import invalid.myask.undertow.events.HearTheAnvil;
import invalid.myask.undertow.network.RiptideHitHandler;
import invalid.myask.undertow.network.RiptideHitMessage;
import invalid.myask.undertow.world.UndertowWorldgen;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        // Undertow.LOG.info(Config.greeting);
        Undertow.LOG.info("I am " + Undertow.MODNAME + " at version " + Tags.VERSION);
        UndertowBlocks.register();
        UndertowItems.register();
        UndertowEntities.register();
        UndertowStructures.register();
        GameRegistry.registerWorldGenerator(new UndertowWorldgen(), 0);

        HearTheOcean.registerEvents();
        HearTheLand.registerEvents();
        HearTheAnvil.registerEvents();

        Undertow.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Undertow.MODID);
        Undertow.networkWrapper.registerMessage(PoseChangeHandler.class, PoseChangeMessage.class, 0, Side.SERVER);
        Undertow.networkWrapper.registerMessage(RiptideHitHandler.class, RiptideHitMessage.class, 1, Side.CLIENT);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        UndertowRecipes.registerRecipes();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(CommandLocate.instance);
        event.registerServerCommand(CommandLocateBiome.instance);
        event.registerServerCommand(CommandGiveMapTo.instance);
    }
}
