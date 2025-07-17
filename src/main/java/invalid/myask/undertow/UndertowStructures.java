package invalid.myask.undertow;

import invalid.myask.undertow.util.UndertowUtils;
import invalid.myask.undertow.world.BuriedTreasureGen;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class UndertowStructures {
    public static void register() {
        MapGenStructureIO.registerStructure(BuriedTreasureGen.Start.class, "undertow:buried_treasure");
        BuriedTreasureGen.registerPiece();
        UndertowUtils.noteStructGen(BuriedTreasureGen.instance);
    }
}
