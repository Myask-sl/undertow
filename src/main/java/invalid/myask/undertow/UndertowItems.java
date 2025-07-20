package invalid.myask.undertow;


import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.undertow.item.*;
import net.minecraft.item.Item;

public class UndertowItems {
    public static final Item TRIDENT = new ItemTrident(9).setTextureName("trident").setUnlocalizedName("trident");
    public static final Item SPEAR_FLINT = new ItemSpear(6).setTextureName("spear_flint").setUnlocalizedName("spear_flint").setMaxDamage(131);

    public static final Item NAUTILUS = new Item().setUnlocalizedName("nautilus").setTextureName(Undertow.MODID + ":nautilus");
    public static final Item SEAHEART = new Item().setUnlocalizedName("seaheart").setTextureName(Undertow.MODID + ":seaheart");

    public static final Item SHIELD = new ItemShield().setTextureName("shield").setUnlocalizedName("shield");

    public static final ItemDumbMap FILLED_DUMB_MAP = new ItemDumbMap();
    public static final ItemEmptyDumbMap EMPTY_DUMB_MAP = new ItemEmptyDumbMap();
    public static final ItemDecoMap DECO_MAP = new ItemDecoMap();
    public static final ItemExplorerMap EXPLORER_MAP = new ItemExplorerMap();

    public static void register(){
        GameRegistry.registerItem(TRIDENT, "trident", Undertow.MODID);
        ((ItemTrident) TRIDENT).registerDispensation();
        GameRegistry.registerItem(SPEAR_FLINT, "spear_flint", Undertow.MODID);
        ((ItemTrident) SPEAR_FLINT).registerDispensation();

        GameRegistry.registerItem(NAUTILUS, "nautilus", Undertow.MODID);
        GameRegistry.registerItem(SEAHEART, "seaheart", Undertow.MODID);

        GameRegistry.registerItem(SHIELD, "shield", Undertow.MODID);

        GameRegistry.registerItem(EMPTY_DUMB_MAP, "dumb_map");
        GameRegistry.registerItem(FILLED_DUMB_MAP, "dumb_filled_map");
        GameRegistry.registerItem(DECO_MAP, "decorated_map");
    }
}
