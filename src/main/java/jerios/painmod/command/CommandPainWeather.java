package jerios.painmod.command;

import jerios.painmod.config.PainConfig;
import jerios.painmod.world.data.PainWorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandWeather;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;
import java.util.Random;

public class CommandPainWeather extends CommandWeather {

    @Override
    public String getCommandName()
    {
        return "painweather";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        World world = sender.getEntityWorld();
        if (!world.isRemote) {
            PainWorldData data = PainWorldData.get(world);
            data.setRaining(true);
            data.setRainTime(Integer.MAX_VALUE);
            data.setRaining(true);
            data.setRainTime(Integer.MAX_VALUE);
            data.setRaining(true);

            data.setThundering(true);
            data.setThunderTime(Integer.MAX_VALUE);
            data.setThundering(true);
            data.setThunderTime(Integer.MAX_VALUE);
            data.setThundering(true);


        }
    }




}
