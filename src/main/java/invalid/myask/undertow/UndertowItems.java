package invalid.myask.undertow;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import invalid.myask.undertow.entities.fish.EntityGiantClownfish;
import invalid.myask.undertow.item.*;
import invalid.myask.undertow.Config;

public class UndertowItems {
    //traditional Minecraft drowned-borne trident
    public static final Item TRIDENT = new ItemTrident(9).setTextureName("trident").setUnlocalizedName("trident");
    //new-make trident, but doesn't hit all the mystical notes
    public static final Item BRONZE_TRIDENT = new ItemTrident(7).setTextureName("trident_bronze").setUnlocalizedName("trident_bronze").setMaxDamage(320);
    public static final Item SPEAR_FLINT = new ItemSpear(6).setTextureName("spear_flint").setUnlocalizedName("spear_flint").setMaxDamage(131);

    public static final CreativeTabs TAB_UNDERTOW = new CreativeTabs("undertow") {
        @Override
        public Item getTabIconItem() {
            return BRONZE_TRIDENT;
        }
    };

    public static final Item NAUTILUS = new ItemUC().setUnlocalizedName("nautilus").setTextureName(Undertow.MODID + ":nautilus");
    public static final Item SEAHEART = new ItemUC().setUnlocalizedName("seaheart").setTextureName(Undertow.MODID + ":seaheart");

    public static final Item BUCKET_OF_GIANT_CLOWNFISH = new ItemBucketOfMob(Blocks.flowing_water, EntityGiantClownfish.class)
        .setUnlocalizedName("bucket.mob").setTextureName(Undertow.MODID + ":bucket_of_giant_clownfish");

    public static final Item SHIELD = new ItemShield().setTextureName("shield").setUnlocalizedName("shield");

    public static final Item BOTTLED_LIGHTNING = new ItemBottledLightning().setUnlocalizedName("bottled_lightning")
        .setTextureName(Undertow.MODID + ":bottled_lightning");

    public static final ItemDumbMap FILLED_DUMB_MAP = new ItemDumbMap();
    public static final ItemEmptyDumbMap EMPTY_DUMB_MAP = new ItemEmptyDumbMap();
    public static final ItemDecoMap DECO_MAP = new ItemDecoMap();
    public static final ItemExplorerMap EXPLORER_MAP = new ItemExplorerMap();

    public static void register() {
        registerOne(TRIDENT, "trident", CreativeTabs.tabCombat);
        ((ItemTrident) TRIDENT).registerDispensation();
        registerOne(SPEAR_FLINT, "spear_flint", CreativeTabs.tabCombat);
        ((ItemTrident) SPEAR_FLINT).registerDispensation();

        registerOne(NAUTILUS, "nautilus", CreativeTabs.tabMaterials);
        registerOne(SEAHEART, "seaheart", CreativeTabs.tabMaterials);

        registerOne(BUCKET_OF_GIANT_CLOWNFISH, "bucket_of_giant_clownfish", CreativeTabs.tabMisc);

        registerOne(SHIELD, "shield", CreativeTabs.tabCombat);

        registerOne(BOTTLED_LIGHTNING, "bottled_lightning", CreativeTabs.tabMisc);

        registerOne(EMPTY_DUMB_MAP, "dumb_map", CreativeTabs.tabMisc);
        registerNoTab(FILLED_DUMB_MAP, "dumb_filled_map");
        registerNoTab(DECO_MAP, "decorated_map");
    }

    private static void registerOne(Item item, String name, CreativeTabs otherTab) {
        registerOne(item, name, otherTab, true);
    }
    private static void registerNoTab(Item item, String name) {
        registerOne(item, name, null, false);
    }
    private static void registerOne(Item item, String name, CreativeTabs otherTab, boolean tab) {
        GameRegistry.registerItem(item, name, Undertow.MODID);
        if (tab) {
            if (Config.use_vanilla_tabs)
                item.setCreativeTab(otherTab);
            else item.setCreativeTab(TAB_UNDERTOW);
        }
    }
}
