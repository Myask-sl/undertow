package invalid.myask.undertow;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.undertow.recipes.CartographyRegistry;
import invalid.myask.undertow.recipes.anvil.AnvilMapZoomRecipe;
import invalid.myask.undertow.recipes.maps.RecipesDumbMapsExtending;
import invalid.myask.undertow.recipes.maps.RecipesMoreMapsCloning;
import invalid.myask.undertow.recipes.maps.SimpleCartographyRecipe;
import invalid.myask.undertow.recipes.maps.TableAnvilMapCompassAddRecipe;
import invalid.myask.undertow.recipes.maps.TableAnvilMapCloneRecipe;
import invalid.myask.undertow.recipes.maps.TableMapZoomRecipe;

public class UndertowRecipes {
    //public static final Map<Item, Map<Item, IRecipe>> anvilRecipesRegistry = new HashMap<>();

    public static void registerRecipes() {
        OreDictionary.registerOre("paper", Items.paper);
        if (Config.enable_spears) {
            GameRegistry.addRecipe(new ShapedOreRecipe(UndertowItems.SPEAR_FLINT,
                " sF",
                " /s",
                "/  ",
                's', Items.string,
                'F', Items.flint,
                '/', "stickWood"));
        }
        if (Config.enable_shields) {
            GameRegistry.addRecipe(new ShapedOreRecipe(UndertowItems.SHIELD,
                "#-#",
                "###",
                " # ",
                '#', "plankWood",
                '-', "ingotIron"));
        }
        if (Config.enable_cartography) {
            GameRegistry.addRecipe(new ShapedOreRecipe(UndertowBlocks.CARTOGRAPHY_TABLE,
                "--",
                "##",
                "##",
                '#', "plankWood",
                '-', "paper"));

            CartographyRegistry.addRecipe(new TableMapZoomRecipe());
            CartographyRegistry.addRecipe(TableAnvilMapCloneRecipe.instance);
//TODO            CartographyRegistry.addRecipe(new TableMapLockRecipe());
            if (Config.enable_table_mapmaking)
                CartographyRegistry.addRecipe(new SimpleCartographyRecipe(new ItemStack(Items.map), Items.paper, Items.compass));
            if (Config.enable_dumb_maps) {
                GameRegistry.addRecipe(new ShapedOreRecipe(UndertowItems.EMPTY_DUMB_MAP,
                    "###",
                    "###",
                    "###",
                    '#', "paper"));
                GameRegistry.addRecipe(new RecipesDumbMapsExtending());
                CartographyRegistry.addRecipe(TableAnvilMapCompassAddRecipe.instance);
                if (Config.enable_table_mapmaking) {
                    CartographyRegistry.addRecipe(new SimpleCartographyRecipe(new ItemStack(UndertowItems.EMPTY_DUMB_MAP),
                        Items.paper, null));
                    CartographyRegistry.addRecipe(new SimpleCartographyRecipe(new ItemStack(Items.map),
                        UndertowItems.EMPTY_DUMB_MAP, Items.compass));
                }
                if (Config.enable_anvil_mapwork) {
                    CartographyRegistry.registerSimpleAnvilRecipe(new ItemStack(Items.map),
                        new ItemStack(UndertowItems.EMPTY_DUMB_MAP), new ItemStack(Items.compass),1, 0);
                    CartographyRegistry.registerAnvilRecipe(UndertowItems.FILLED_DUMB_MAP, Items.compass,
                        TableAnvilMapCompassAddRecipe.instance);

                    CartographyRegistry.registerAnvilRecipe(UndertowItems.FILLED_DUMB_MAP, Items.paper,
                        AnvilMapZoomRecipe.instance);

                    CartographyRegistry.registerAnvilRecipe(UndertowItems.FILLED_DUMB_MAP, Items.map,
                        TableAnvilMapCloneRecipe.instance);
                    CartographyRegistry.registerAnvilRecipe(UndertowItems.FILLED_DUMB_MAP, UndertowItems.EMPTY_DUMB_MAP,
                        TableAnvilMapCloneRecipe.instance);
                    CartographyRegistry.registerAnvilRecipe(Items.filled_map, UndertowItems.EMPTY_DUMB_MAP,
                        TableAnvilMapCloneRecipe.instance);
                    if (Config.enable_deco_maps) {
                        CartographyRegistry.registerAnvilRecipe(UndertowItems.DECO_MAP, UndertowItems.EMPTY_DUMB_MAP,
                            TableAnvilMapCloneRecipe.instance);
                    }
                }
            }
            if (Config.enable_anvil_mapwork) {
                CartographyRegistry.registerAnvilRecipe(Items.filled_map, Items.map,
                    TableAnvilMapCloneRecipe.instance);
                CartographyRegistry.registerAnvilRecipe(Items.filled_map, Items.paper,
                    AnvilMapZoomRecipe.instance); //TODO: bimap stack, player UUID. No, better: write zoom to tags.
                if (Config.enable_deco_maps) {
                    CartographyRegistry.registerAnvilRecipe(UndertowItems.DECO_MAP, Items.map,
                        TableAnvilMapCloneRecipe.instance);
                }
            }

        }
        if (Config.enable_deco_maps || Config.enable_dumb_maps) {
            GameRegistry.addRecipe(new RecipesMoreMapsCloning());
        }
        if (Config.enable_conduit) {
            GameRegistry.addRecipe(new ShapedOreRecipe(UndertowBlocks.CONDUIT,
                "@@@",
                "@O@",
                "@@@",
                '@', UndertowItems.NAUTILUS,
                'O', UndertowItems.SEAHEART));
        }
    }
}
