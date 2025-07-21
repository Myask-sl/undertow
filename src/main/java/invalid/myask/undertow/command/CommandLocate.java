package invalid.myask.undertow.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.common.collect.ImmutableList;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.*;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.util.UndertowUtils;
import invalid.myask.undertow.ducks.IMapGenStructure_Accessor;
import invalid.myask.undertow.world.BuriedTreasureGen;

public class CommandLocate extends CommandBase {
    public static CommandLocate instance = new CommandLocate();

/*
    static HashMap<String, Class<? extends MapGenStructure>> structMap = new HashMap<>();
    static List<String> structNameList = null;

    CommandLocate() {
        if (structMap.isEmpty()) {
            structMap.put("Stronghold", MapGenStronghold.class);
            structMap.put("Mineshaft", MapGenMineshaft.class);
            structMap.put("Village", MapGenVillage.class);
            structMap.put("Fortress", MapGenNetherBridge.class);
            structMap.put("Temple", MapGenScatteredFeature.class);
            structNameList = new ArrayList<>(structMap.keySet());
        }
    }

 */

    @Override
    public String getCommandName() {
        return "locate";
    }

    @Override
    public List<String> getCommandAliases() {
        return ImmutableList.of("loc", "locs", "locatestructure");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.locate.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        if (args.length > 0) {
            if ("biome".equals(args[0]))
                CommandLocateBiome.instance.processCommand(sender, Arrays.copyOfRange(args, 1, args.length - 1));
            else if ("structure".equals(args[0])) {
                args = Arrays.copyOfRange(args, 1, args.length - 1);
            }
        }
        int radius = Config.locate_default_radius;
        boolean explored = true, unexplored = true;
        try {
            if (args.length == 0) throw new CommandException("commands.err.args.none");
//            if (args.length > 4) throw new CommandException("commands.err.args.surfeit");
            if (!UndertowUtils.structureNamesContain(args[0])) throw new CommandException("commands.err.structure.invalid");
            MapGenStructure target = UndertowUtils.getStructureByName(args[0]);
            ((IMapGenStructure_Accessor)target).undertow$assureWorld(sender.getEntityWorld());
            BiomeGenBase biome = (target instanceof MapGenScatteredFeature) ? UndertowUtils.getTempleBiomeByName(args[0]) : null;
            if (args.length > 1) radius = parseIntWithMin(sender, args[1], 0);
            if (args.length > 2) explored = parseBoolean(sender, UndertowUtils.broadenBoolString(args[2]));
            if (args.length > 3) unexplored = parseBoolean(sender, UndertowUtils.broadenBoolString(args[3]));
            if (!explored && !unexplored) throw new CommandException("commands.err.excludedmiddle");
            ChunkCoordIntPair result = UndertowUtils.locateStructure(target, player.getEntityWorld(), (int) player.posX, (int) player.posZ,
                radius, explored, unexplored, biome);
            if (result == null)
                sender.addChatMessage(new ChatComponentTranslation("commands.locatestructure.notfound", args[0], radius));
            else {
                int resultX = result.chunkXPos << 4,
                    resultZ = result.chunkZPos << 4;
                if (target instanceof BuriedTreasureGen) {
                    resultX += Config.buried_treasure_x;
                    resultZ += Config.buried_treasure_z;
                }
                sender.addChatMessage(new ChatComponentTranslation("commands.locatestructure.found", args[0], resultX, resultZ));
            } //TODO: change addchatmessage to that one function that notifies admin
        }
        catch (CommandException e) {
            sender.addChatMessage(new ChatComponentTranslation(e.getMessage()));
            sender.addChatMessage(new ChatComponentTranslation("commands.locate.usage"));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        String lastarg = args.length == 0 ? "" : args [args.length-1];
        if (args.length == 3 || args.length == 4)
            return "true".startsWith(lastarg) || "true".startsWith(lastarg.toLowerCase()) ? ImmutableList.of("true") :
                "false".startsWith(lastarg) || "false".startsWith(lastarg.toLowerCase()) ? ImmutableList.of("false") : null;
        else if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            UndertowUtils.structureTabCompletes(result, lastarg);
            if (result.isEmpty()) {
                if ("biome".startsWith(lastarg)) result.add("biome");
                else if ("structure".startsWith(lastarg)) result.add("structure");
            }
            return result;
        }
        return null;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return super.canCommandSenderUseCommand(sender);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return Config.locate_permission_level;
    }
}
