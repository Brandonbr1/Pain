package jerios.painmod.command;

import jerios.painmod.config.PainConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandWeather;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;
import java.util.Random;

public class CommandPainWeather extends CommandBase {

    public String getCommandName()
    {
        return "painweather";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.weather.usage";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length >= 1 && args.length <= 2)
        {
            int i = (300 + (new Random()).nextInt(600)) * 20;

            if (args.length >= 2)
            {
                i = parseIntBounded(sender, args[1], 1, 1000000) * 20;
            }

            WorldServerMulti worldserver = (WorldServerMulti) MinecraftServer.getServer().worldServers[PainConfig.painDimensionID];
            DerivedWorldInfo worldinfo = (DerivedWorldInfo) worldserver.getWorldInfo();

            worldinfo.setRainTime(1900);
            worldinfo.setRaining(true);
            worldinfo.setThundering(false);

            if ("clear".equalsIgnoreCase(args[0]))
            {
                worldserver.provider.resetRainAndThunder();
                worldinfo.setRainTime(0);
                worldinfo.setThunderTime(0);
                worldinfo.setRaining(false);
                worldinfo.setThundering(false);
                func_152373_a(sender, this, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(args[0]))
            {
                worldinfo.setRainTime(i);
                worldinfo.setRaining(true);
                worldinfo.setThundering(false);
                func_152373_a(sender, this, "commands.weather.rain", new Object[0]);
            }
            else
            {
                if (!"thunder".equalsIgnoreCase(args[0]))
                {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }

                worldinfo.setRainTime(i);
                worldinfo.setThunderTime(i);
                worldinfo.setRaining(true);
                worldinfo.setThundering(true);
                func_152373_a(sender, this, "commands.weather.thunder", new Object[0]);
            }
        }
        else
        {
            throw new WrongUsageException("commands.weather.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"clear", "rain", "thunder"}): null;
    }



}
