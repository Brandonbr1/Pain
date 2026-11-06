package jerios.painmod.core.networking.registry;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import jerios.painmod.Tags;
import jerios.painmod.core.networking.packets.clientToServer.CTSPacketMusic;
import jerios.painmod.core.networking.packets.serverToClient.STCEscapedDim;

public class PainPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);


    public static void register() {
        registerMessage(STCEscapedDim.EscapedDimMessageHandler.class, STCEscapedDim.class, Side.CLIENT);
        registerMessage(CTSPacketMusic.PacketMusicHandler.class, CTSPacketMusic.class, Side.SERVER);
    }

    static int discriminatorID = 1;

    public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        INSTANCE.registerMessage(messageHandler,requestMessageType, discriminatorID++, side);
    }


}
