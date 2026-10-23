package jerios.painmod.command;

import jerios.painmod.config.PainConfig;
import net.minecraft.command.CommandWeather;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;

import java.util.Random;

public class CommandPainWeather extends CommandWeather {

    public String getCommandName()
    {
        return "painWeather";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length >= 1 && args.length <= 2)
        {
            int i = (300 + (new Random()).nextInt(600)) * 20;

            if (args.length >= 2)
            {
                i = parseIntBounded(sender, args[1], 1, 1000000) * 20;
            }

            WorldServer worldserver = MinecraftServer.getServer().worldServers[PainConfig.painDimensionID];
            if (worldserver instanceof WorldServerMulti) {
                WorldServerMulti multi = (WorldServerMulti) worldserver;

                DerivedWorldInfo worldinfo = (DerivedWorldInfo) multi.getWorldInfo();

                if ("clear".equalsIgnoreCase(args[0]))
                {
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



    }

}
