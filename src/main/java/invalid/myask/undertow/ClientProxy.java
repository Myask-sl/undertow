package invalid.myask.undertow;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import invalid.myask.undertow.client.settings.UndertowKeybinds;
import invalid.myask.undertow.client.*;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.entities.ProjectileTrident;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    static final RenderArrow TEMP_RENDER = new RenderArrow();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForgeClient.registerItemRenderer(UndertowItems.TRIDENT, RenderTrident.instance);
        MinecraftForgeClient.registerItemRenderer(UndertowItems.SHIELD, RenderShield.instance);
        MinecraftForgeClient.registerItemRenderer(UndertowItems.FILLED_DUMB_MAP, RenderDumbMap.instance);
        //RenderingRegistry.registerEntityRenderingHandler(ProjectileTrident.class, RenderTrident.instance);
        RenderingRegistry.registerEntityRenderingHandler(ProjectileTrident.class, RenderTrident.instance);
        RenderingRegistry.registerEntityRenderingHandler(EntityDrowned.class, new RenderDrowned(new ModelDrowned(), 0.5F, 1));

        //        RenderingRegistry.registerEntityRenderingHandler(ProjectileRiptide.class, RenderRiptide.instance);
        //we do that one in a mixin call to keep it rotated with the biped.

        FMLCommonHandler.instance().bus().register(RenderTrident.instance);

        if (Config.enable_crawl_keybind)
            ClientRegistry.registerKeyBinding(UndertowKeybinds.crawl);
    }
}
