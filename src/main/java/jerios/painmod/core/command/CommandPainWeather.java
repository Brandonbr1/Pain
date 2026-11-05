package jerios.painmod.core.command;

import jerios.painmod.core.world.data.PainWorldData;
import net.minecraft.command.CommandWeather;
import net.minecraft.command.ICommandSender;
import net.minecraft.world.World;

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
