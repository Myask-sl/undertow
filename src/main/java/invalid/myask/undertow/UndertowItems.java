package invalid.myask.undertow;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import invalid.myask.undertow.entities.fish.EntityGiantClownfish;
import invalid.myask.undertow.item.*;

public class UndertowItems {
    //traditional Minecraft drowned-borne trident
    public static final Item TRIDENT = new ItemTrident(9).setTextureName("trident").setUnlocalizedName("trident");
    //new-make trident, but doesn't hit all the mystical notes
    public static final Item BRONZE_TRIDENT = new ItemTrident(7).setTextureName("trident_bronze").setUnlocalizedName("trident_bronze").setMaxDamage(320);
    public static final Item SPEAR_FLINT = new ItemSpear(6).setTextureName("spear_flint").setUnlocalizedName("spear_flint").setMaxDamage(131);

    public static final Item NAUTILUS = new Item().setUnlocalizedName("nautilus").setTextureName(Undertow.MODID + ":nautilus");
    public static final Item SEAHEART = new Item().setUnlocalizedName("seaheart").setTextureName(Undertow.MODID + ":seaheart");

    public static final Item SHIELD = new ItemShield().setTextureName("shield").setUnlocalizedName("shield");

    public static final Item BOTTLED_LIGHTNING = new ItemBottledLightning().setUnlocalizedName("bottled_lightning")
        .setTextureName(Undertow.MODID + ":bottled_lightning");

    public static final Item BUCKET_OF_GIANT_CLOWNFISH = new ItemBucketOfMob(Blocks.flowing_water, EntityGiantClownfish.class)
        .setUnlocalizedName("bucket.mob").setTextureName(Undertow.MODID + ":bucket_of_giantclownfish");

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

        GameRegistry.registerItem(BOTTLED_LIGHTNING, "bottled_lightning", Undertow.MODID);

        GameRegistry.registerItem(EMPTY_DUMB_MAP, "dumb_map", Undertow.MODID);
        GameRegistry.registerItem(FILLED_DUMB_MAP, "dumb_filled_map", Undertow.MODID);
        GameRegistry.registerItem(DECO_MAP, "decorated_map", Undertow.MODID);
    }
}
