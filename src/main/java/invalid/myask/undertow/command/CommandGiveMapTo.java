package invalid.myask.undertow.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.item.ItemExplorerMap;
import invalid.myask.undertow.util.UndertowUtils;

public class CommandGiveMapTo extends CommandBase {
    public static CommandGiveMapTo instance = new CommandGiveMapTo();
    @Override
    public String getCommandName() {
        return "givemapto";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.givemapto.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) throw new CommandException("commands.err.noargs");
        if (!UndertowUtils.structureNamesContain(args[0])) throw new CommandException("commands.err.structure.invalid");
        World w = sender.getEntityWorld();
        MapGenStructure target = UndertowUtils.getStructureByName(args[0]);
        ItemStack s = ItemExplorerMap.mapTo(target, args[0], w, sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posZ);
        EntityItem ei = new EntityItem(w, sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ,
            s);
        ei.delayBeforeCanPickup = 0;
        w.spawnEntityInWorld(ei);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return Config.givemapto_permission_level;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        String lastarg = args.length == 0 ? "" : args [args.length-1];
        ArrayList<String> result = new ArrayList<>();
        UndertowUtils.structureTabCompletes(result, lastarg);
        return result;
    }
}
