package invalid.myask.undertow.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class PotionDolphinsGrace extends Potion {
    public PotionDolphinsGrace(int id, int color) {
        super(id, false, color); //not a bad effect
        setPotionName("dolphins_grace");
    }

    @Override
    public void performEffect(EntityLivingBase vic, int amplifier) {

    }
}
