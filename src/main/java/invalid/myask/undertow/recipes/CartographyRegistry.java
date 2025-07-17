package invalid.myask.undertow.recipes;

import invalid.myask.undertow.inventory.AnvilContents;
import invalid.myask.undertow.recipes.anvil.SimpleAnvilRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartographyRegistry {
    public static final List<IRecipe> recipes = new ArrayList<>(7);
    public static final Map<Item, Map<Item, IRecipe>> anvilRecipesRegistry = new HashMap<>();

    public static void addRecipe(IRecipe recipe) {
        recipes.add(recipe);
    }

    public static void registerAnvilRecipe(Item inLeft, Item inRight, IRecipe recipe) {
        Map<Item, IRecipe> recipesForL = anvilRecipesRegistry.computeIfAbsent(inLeft, k -> new HashMap<>());
        recipesForL.put(inRight, recipe);
    }

    public static void registerSimpleAnvilRecipe(ItemStack itemStackOut, ItemStack itemStackL, ItemStack itemStackR, int materialCost, int levelCost) {
        registerAnvilRecipe(itemStackL.getItem(), itemStackR.getItem(), new SimpleAnvilRecipe(itemStackL, itemStackR, itemStackOut, materialCost, levelCost));
    }

    /**
     * Returns from the registry "the" anvil recipe matching.
     * @param itemStackLeft self-described
     * @param itemStackRight self-described
     * @return the one anvil matching recipe
     */
    public static IRecipe anvilOption(ItemStack itemStackLeft, ItemStack itemStackRight) {
        if (anvilRecipesRegistry.containsKey(itemStackLeft.getItem())) {
            Map<Item, IRecipe> leftMap = anvilRecipesRegistry.get(itemStackLeft.getItem());
            return leftMap.get(itemStackRight.getItem());
        }
        return null;
    }

    public static boolean anvilMatch(ItemStack left, ItemStack right) {
        IRecipe recipe = anvilOption(left, right);
        InventoryCrafting anvilContents = new InventoryCrafting(new AnvilContents(), 2, 1);
        anvilContents.setInventorySlotContents(0, left);
        anvilContents.setInventorySlotContents(1, right);
        return recipe != null && recipe.matches(anvilContents,null);
    }
}
