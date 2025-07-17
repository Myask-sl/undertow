package invalid.myask.undertow.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class PotionConduitPower extends Potion {
    public PotionConduitPower(int id, int color) {
        super(id, false, color); //not a bad effect
        setPotionName("conduit_power");
    }

    @Override
    public void performEffect(EntityLivingBase vic, int amplifier) {

    }
}
