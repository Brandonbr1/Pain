package jerios.painmod.core.command;

import jerios.painmod.core.world.teleport.PainTeleporter;
import jerios.painmod.core.world.teleport.TeleporterHandler;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.utils.props.PainProps;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;

import java.util.List;

public class CommandSoftlock extends CommandBase {
    @Override
    public String getCommandName() {
        return "painmodSoftlock";
    }


    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }


    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.painmod.softlock.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        final int dim = PainConfig.painDimensionID;
        MinecraftServer server =  MinecraftServer.getServer();
        WorldServer worldServer = server.worldServers[dim];
        sender.addChatMessage(new ChatComponentText("You have softlocked yourself, running to try and teleport you back to overworld"));


       List<EntityPlayer> p = sender.getEntityWorld().playerEntities;

        for (int i = 0; i < p.size(); i++) {

            EntityPlayer player = p.get(i);
            PainProps props = PainProps.get(player);


            if (player.getCommandSenderName().equals(sender.getCommandSenderName())) {

                if (props != null) {
                    props.escapedDim = true;

                    if (player instanceof EntityPlayerMP) {
                        EntityPlayerMP playerMP = (EntityPlayerMP) player;
                        sender.addChatMessage(new ChatComponentText("Found you... Now trying to unsoftlock you."));



                        WorldServer ws = worldServer.func_73046_m().worldServerForDimension(dim);
                        if (ws != null)
                        {
                            TeleporterHandler.travelToDimension(playerMP, 0, new PainTeleporter(ws, false));

                        }


                    }


                    break;

                } else {
                    sender.addChatMessage(new ChatComponentText("Pain Player Props is null..... This is bad... Unable to escape you. Report this bug to an admin or the mod dev(Jerios)"));


                }



            }














        }



    }
}
