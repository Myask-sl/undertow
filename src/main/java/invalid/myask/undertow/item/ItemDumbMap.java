package invalid.myask.undertow.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;

public class ItemDumbMap extends ItemMap implements IBackportedMap {

    public ItemDumbMap() {
        this.setUnlocalizedName("dumb_filled_map");
        this.setTextureName("map_filled");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltipLines, boolean isAdvancedTooltip) {
        super.addInformation(stack, player, tooltipLines, isAdvancedTooltip);
        if (isAdvancedTooltip)
            tooltipLines.add(I18n.format("item.tooltip.map.blind"));
    }

    @Override
    public boolean renderLikeVanillaMapInFrame() {
        return true;
    }

    @Override
    public boolean hidePlayers() {
        return true;
    }
}
