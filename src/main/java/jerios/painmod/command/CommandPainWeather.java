package jerios.painmod.command;

import jerios.painmod.config.PainConfig;
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
    public void processCommand(ICommandSender sender, String[] args) {

        World world =  sender.getEntityWorld();

       if (world != null )
       {

           if (!world.isRemote) {

               WorldInfo worldinfo = world.getWorldInfo();

               worldinfo.setRainTime(9000);
               worldinfo.setRaining(true);
               worldinfo.setThundering(false);

           }

       }


    }




}
