package invalid.myask.undertow.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import invalid.myask.undertow.Config;
import invalid.myask.undertow.inventory.AnvilContents;
import invalid.myask.undertow.recipes.CartographyRegistry;
import invalid.myask.undertow.recipes.anvil.IAnvilCosts;
import invalid.myask.undertow.recipes.anvil.SimpleAnvilRecipe;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

import java.util.HashMap;
import java.util.Map;

public class HearTheAnvil {
    public static final HearTheAnvil instance = new HearTheAnvil();

    public static Map<ItemStack, World> recipeWorld = new HashMap<>();

    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(instance);
    }

    @SubscribeEvent
    public void onAnvilPut(AnvilUpdateEvent event) {
        IRecipe recipe = CartographyRegistry.anvilOption(event.left, event.right);
        if (recipe == null) return;
        ItemStack result = null;
        int levCost = 0;
        if (recipe instanceof IAnvilCosts anvRec) {
            levCost = anvRec.getLevelCost();
            event.materialCost = anvRec.getMaterialCost();
        } else event.materialCost = 1;

        if (recipe instanceof SimpleAnvilRecipe sar) {
            if (sar.matches(event.left, event.right))
                result = sar.getCraftingResult(null);
        } else {
            InventoryCrafting anvilContents = new InventoryCrafting(new AnvilContents(), 2, 1);
            anvilContents.setInventorySlotContents(0, event.left);
            anvilContents.setInventorySlotContents(1, event.right);
            if (recipe.matches(anvilContents, recipeWorld.get(event.left)))
                result = recipe.getCraftingResult(anvilContents);
        }

        event.output = result;

        if (!event.name.isEmpty() && (event.left.getItem() != null && !event.name.equals(event.left.getItem().getItemStackDisplayName(result)))
            && (event.output.getItem() != null && !event.name.equals(event.output.getItem().getItemStackDisplayName(result)))) {
            if (!event.name.equals(event.left.getItem().getItemStackDisplayName(event.left)))
                levCost++;
            event.output.setStackDisplayName(event.name);
        }
        event.cost = levCost;
    }

    /**
     * AnvilRepairEvent slots are shuffled from what they should be.
     * Event's "output" is left, "left" is right, and "right" is output.
     * @param event the AnvilRepairEvent.
     */
    @SubscribeEvent
    public void onAnvilTake(AnvilRepairEvent event) {
        if (Config.guard_anvil_left && CartographyRegistry.anvilMatch(event.output, event.left)) {
            ItemStack remainder = event.output.copy();
            if (--remainder.stackSize > 0) {
                EntityItem popoff = event.entityPlayer.dropPlayerItemWithRandomChoice(remainder, false);
                popoff.delayBeforeCanPickup = 0;
            }
        }
        recipeWorld.remove(event.left);
    }
}
