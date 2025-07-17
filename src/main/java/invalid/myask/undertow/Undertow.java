package invalid.myask.undertow;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import invalid.myask.undertow.Tags;

@Mod(
    modid = Undertow.MODID,
    version = Tags.VERSION,
    name = Undertow.MODNAME,
    acceptedMinecraftVersions = "[" + Tags.MINECRAFT_VERSION + "]")
public class Undertow {
    @Mod.Instance(Tags.MODID)
    public static Undertow instance;

    public static final String MODID = Tags.MODID;
    public static final String MODNAME = Tags.MODNAME;
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = Tags.MODGROUP + ".ClientProxy", serverSide = Tags.MODGROUP + ".CommonProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper networkWrapper;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
