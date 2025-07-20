package invalid.myask.undertow.command;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.biome.BiomeGenBase;

import invalid.myask.undertow.Config;
import invalid.myask.undertow.util.UndertowUtils;

public class CommandLocateBiome extends CommandBase {
    public static CommandLocateBiome instance = new CommandLocateBiome();

    static Map<String, Integer> biomeNameMap = new HashMap<String, Integer>();
    static List<String> biomeNameList = null;

    @Override
    public String getCommandName() {
        return "locatebiome";
    }

    @Override
    public List<String> getCommandAliases() {
        return ImmutableList.of("locb");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.locatebiome.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        int radius = Config.locatebiome_default_radius;
        boolean explored = true, unexplored = true;
        BiomeGenBase target = null;
        try {
            if (args.length == 0) throw new CommandException("commands.err.args.none");
//            if (args.length > 4) throw new CommandException("commands.err.args.surfeit");
            checkBiomesCached();
            if (!biomeNameMap.containsKey(args[0])) {
                int targID;
                try
                {
                    targID = parseIntBounded(sender, args[0], 0, BiomeGenBase.getBiomeGenArray().length - 1);
                }
                catch(NumberInvalidException e) {
                    throw new CommandException("commands.err.biome.invalid");
                }
                if (!biomeNameMap.containsValue(targID))
                    throw new CommandException("commands.err.biome.invalid");
                else target = BiomeGenBase.getBiome(targID);
            }
            else
                target = BiomeGenBase.getBiome(biomeNameMap.get(args[0]));
            if (args.length > 1) radius = parseIntWithMin(sender, args[1], 0);
            if (args.length > 2) explored = parseBoolean(sender, args[2]);
            if (args.length > 3) unexplored = parseBoolean(sender, args[3]);
            if (!explored && !unexplored) throw new CommandException("commands.err.excludedmiddle");
            ChunkCoordIntPair result = UndertowUtils.locateBiome(target, player.getEntityWorld(), (int) player.posX, (int) player.posZ,
                radius, explored, unexplored);
            if (result == null)
                sender.addChatMessage(new ChatComponentTranslation("commands.locatebiome.notfound", args[0], radius));
            else
                sender.addChatMessage(new ChatComponentTranslation("commands.locatebiome.found", args[0], result.chunkXPos << 4, result.chunkZPos << 4));
        }
        catch (CommandException e) {
            sender.addChatMessage(new ChatComponentTranslation(e.getMessage()));
            sender.addChatMessage(new ChatComponentTranslation("commands.locatebiome.usage"));
        }
    }

    private void checkBiomesCached() {
            if (biomeNameMap.isEmpty()) {
            for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
                if (biome != null && !biome.biomeName.isEmpty()) {
                    String underscored = biome.biomeName.replace(' ', '_');
                    biomeNameMap.put(biome.biomeName, biome.biomeID);
                }
            }
            biomeNameList = new ArrayList<>(biomeNameMap.keySet());
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        String lastarg = args.length == 0 ? "" : args [args.length-1];
        ArrayList<String> result = new ArrayList<>();
        if (args.length == 3 || args.length == 4)
            return "true".startsWith(lastarg) ? ImmutableList.of("true") : "false".startsWith(lastarg) ? ImmutableList.of("false") : null;
        else if (args.length == 1) {
            checkBiomesCached();
            for (String s : biomeNameList) {
                if (s.startsWith(lastarg) || s.toLowerCase().startsWith(lastarg.toLowerCase())) result.add(s);
            }
        }
        return result;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return super.canCommandSenderUseCommand(sender);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return Config.locatebiome_permission_level;
    }
}
