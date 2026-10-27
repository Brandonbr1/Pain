package jerios.painmod.command;

import jerios.painmod.config.PainConfig;
import jerios.painmod.world.teleport.TeleporterHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServerMulti;

public class CommandSoftlock extends CommandBase {
    @Override
    public String getCommandName() {
        return "painmodSoftlock";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.painmod.softlock.usage";
    }

    //TODO: PREVENT SOFTLOCK
    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        WorldServerMulti worldserver = (WorldServerMulti) MinecraftServer.getServer().worldServers[PainConfig.painDimensionID];

        if (sender.getEntityWorld().provider.dimensionId == PainConfig.painDimensionID) {

            sender.addChatMessage(new ChatComponentText("You have softlocked yourself, running to try and teleport you back to overworld"));
        } else {
            sender.addChatMessage(new ChatComponentText("You are not in the dim..... Did you softlock yourself that bad."));
        }


    }
}
