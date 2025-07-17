package invalid.myask.undertow.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import org.spongepowered.libraries.com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

public class ItemDecoMap extends ItemMap implements IBackportedMap {
    public static Set<String> ocean_monument_blocks = ImmutableSet.of("prismarine", "prismarine_bricks", "dark_prismarine",
        "sea_lantern", "blockGold", "sponge", "wet_sponge");
    public static Set<String> ocean_monument_entity_classes = ImmutableSet.of("EntityGuardian", "EntityElderGuardian");

    public static Set<String> woodland_mansion_blocks = ImmutableSet.of("wood", "stairsBirch", "stairsWoodDarkOak", "bookshelf",
        "woolCarpet", "blockLapis", "blockDiamond", "stonebrick"/* actually cobblestone */, "stoneSlab");
    public static Set<String> woodland_mansion_entities = ImmutableSet.of("EntityVindicator", "EntityEvoker");

    public static Set<String> village_entities = ImmutableSet.of("EntityVillager", "EntityIronGolem", "EntityZombieVillager");

    public ItemDecoMap() {
        this.setUnlocalizedName("decorated_map");
        this.setTextureName("map_filled");
    }
    @Override
    public void addInformation(ItemStack theMap, EntityPlayer player, List<String> tooltipLines, boolean showAdvancedTooltips) {
        super.addInformation(theMap, player, tooltipLines, showAdvancedTooltips);
        if (showAdvancedTooltips) {
            if (theMap != null && theMap.getTagCompound() != null && !theMap.getTagCompound().getString("").isEmpty()) {
                tooltipLines.add("Marked with: ");
            }
        }
    }

    @Override
    public MapData getMapData(ItemStack p_77873_1_, World p_77873_2_) {
        return super.getMapData(p_77873_1_, p_77873_2_);
    }

    @Override
    public boolean renderLikeVanillaMapInFrame() {
        return true;
    }

    @Override
    public boolean renderMoreStuff() {
        return true;
    }
}
