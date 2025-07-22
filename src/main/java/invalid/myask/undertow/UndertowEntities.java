package invalid.myask.undertow;

import cpw.mods.fml.common.registry.EntityRegistry;
import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.entities.ProjectileTrident;
import net.minecraft.entity.EntityList;

public class UndertowEntities {
    public static void register(){
        EntityRegistry.registerModEntity(ProjectileTrident.class, "projectile.trident", 0, Undertow.instance, 112, 20, true);

        EntityRegistry.registerModEntity(EntityDrowned.class, "drowned", 1, Undertow.instance, 128, 5, false);

        EntityList.addMapping(EntityDrowned.class, "drowned", 255,  0x00FFFF, 0x005500);

        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(ProjectileTrident.class, false);
        er.setCustomSpawning(null, false);
    }
}
