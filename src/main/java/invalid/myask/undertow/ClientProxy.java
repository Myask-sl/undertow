package invalid.myask.undertow;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraftforge.client.MinecraftForgeClient;

import invalid.myask.undertow.client.entity.ModelDrowned;
import invalid.myask.undertow.client.entity.RenderDrowned;
import invalid.myask.undertow.client.item.IItemEntityRendered;
import invalid.myask.undertow.client.item.RenderDumbMap;
import invalid.myask.undertow.client.item.RenderShield;
import invalid.myask.undertow.client.item.RenderTrident;
import invalid.myask.undertow.client.settings.UndertowKeybinds;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.entities.ProjectileTrident;
import invalid.myask.undertow.client.entity.ModelClownfishNoir;
import invalid.myask.undertow.client.entity.RenderClownfishNoir;
import invalid.myask.undertow.entities.fish.EntityClownfishNoir;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    static final RenderArrow TEMP_RENDER = new RenderArrow();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForgeClient.registerItemRenderer(UndertowItems.TRIDENT, RenderTrident.instance);
        MinecraftForgeClient.registerItemRenderer(UndertowItems.SPEAR_FLINT, RenderTrident.instance);
        ((IItemEntityRendered)UndertowItems.TRIDENT).setResLoc(RenderTrident.TRITEX);
        ((IItemEntityRendered)UndertowItems.SPEAR_FLINT).setResLoc(RenderTrident.SPEARTEX);

        MinecraftForgeClient.registerItemRenderer(UndertowItems.SHIELD, RenderShield.instance);
        MinecraftForgeClient.registerItemRenderer(UndertowItems.FILLED_DUMB_MAP, RenderDumbMap.instance);
        RenderingRegistry.registerEntityRenderingHandler(ProjectileTrident.class, RenderTrident.instance);
        //        RenderingRegistry.registerEntityRenderingHandler(ProjectileRiptide.class, RenderRiptide.instance);
        //we do that one in a mixin call to keep it rotated with the biped.

        RenderingRegistry.registerEntityRenderingHandler(EntityDrowned.class, new RenderDrowned(new ModelDrowned(), 0.5F, 1));
        RenderingRegistry.registerEntityRenderingHandler(EntityClownfishNoir.class, new RenderClownfishNoir(new ModelClownfishNoir(), 0.5F));

        FMLCommonHandler.instance().bus().register(RenderTrident.instance); //getting timer

        if (Config.enable_crawl_keybind)
            ClientRegistry.registerKeyBinding(UndertowKeybinds.crawl);
    }
}
