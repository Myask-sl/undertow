package invalid.myask.undertow;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityList;

import invalid.myask.undertow.entities.EntityDrowned;
import invalid.myask.undertow.entities.ProjectileTrident;
import invalid.myask.undertow.entities.fish.EntityGiantClownfish;
import invalid.myask.undertow.entities.fish.EntityGiantClownfishNoir;

public class UndertowEntities {
    public static void register(){
        EntityRegistry.registerModEntity(ProjectileTrident.class, "projectile.trident", 0, Undertow.instance, 112, 20, true);
        EntityRegistry.registerModEntity(EntityDrowned.class, "drowned", 1, Undertow.instance, 128, 5, false);
        EntityRegistry.registerModEntity(EntityGiantClownfish.class, "giantClownfish", 2, Undertow.instance, 64, 5, false);
        EntityRegistry.registerModEntity(EntityGiantClownfishNoir.class, "clownfishNoir", 3, Undertow.instance, 64, 5, false);

        EntityList.addMapping(EntityDrowned.class, "drowned", 255,  0x00FFFF, 0x005500);
        EntityList.addMapping(EntityGiantClownfish.class, "giantClownfish", 254,  0xF0A000, 0xE0E0E0);
        EntityList.addMapping(EntityGiantClownfishNoir.class, "clownfishNoir", 253,  0xF0A000, 0x202020);

        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(ProjectileTrident.class, false);
        er.setCustomSpawning(null, false);
    }
}
