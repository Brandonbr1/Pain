package jerios.painmod.core.command;

import jerios.painmod.core.networking.packets.serverToClient.STCEscapedDim;
import jerios.painmod.core.networking.registry.PainPacketHandler;
import jerios.painmod.core.world.teleport.PainTeleporter;
import jerios.painmod.core.world.teleport.TeleporterHandler;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.forge.props.PainProps;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;

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
        MinecraftServer server =  MinecraftServer.getServer();
        sender.addChatMessage(new ChatComponentText("You have softlocked yourself, running to try and teleport you back to overworld"));

       WorldServer ws = server.worldServerForDimension(PainConfig.painDimensionID);

       List<EntityPlayer> players = ws.playerEntities;

        for (int i = 0; i < players.size(); i++) {

            EntityPlayer player = players.get(i);

            if (!player.worldObj.isRemote && !sender.getEntityWorld().isRemote) {


                if (player.getCommandSenderName().equals(sender.getCommandSenderName())) {

                    PainProps props = PainProps.get(player);
                    if (props != null) {

                        if (player instanceof EntityPlayerMP) {
                            props.escapedDim = true;
                            EntityPlayerMP playerMP = (EntityPlayerMP) player;

                            PainPacketHandler.INSTANCE.sendTo(new STCEscapedDim(true), playerMP);

                            TeleporterHandler.travelToDimension(playerMP, 0, new PainTeleporter(ws, false));


                        }

                    } else {
                        sender.addChatMessage(new ChatComponentText("Pain Player Props is null..... This is bad... Unable to escape you. Report this bug to an admin or the mod dev(Jerios)"));
                    }



                }

            }















        }



    }
}
