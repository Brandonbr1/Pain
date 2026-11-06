package jerios.painmod.core.networking.registry;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import jerios.painmod.Tags;
import jerios.painmod.core.networking.packets.clientToServer.CTSPacketMusic;
import jerios.painmod.core.networking.packets.serverToClient.STCEscapedDim;
import jerios.painmod.core.networking.packets.serverToClient.STCPacketMusic;
import jerios.painmod.core.networking.packets.serverToClient.STCStarRandom;

public class PainPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);


    public static void register() {
        registerMessage(STCEscapedDim.EscapedDimMessageHandler.class, STCEscapedDim.class, Side.CLIENT);
        registerMessage(CTSPacketMusic.PacketMusicHandler.class, CTSPacketMusic.class, Side.SERVER);
        registerMessage(STCStarRandom.HandlerRandom.class, STCStarRandom.class, Side.CLIENT);
        registerMessage(STCPacketMusic.PacketMusicHandler.class, STCPacketMusic.class, Side.CLIENT);
    }

    static int discriminatorID = 1;

    public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        INSTANCE.registerMessage(messageHandler,requestMessageType, discriminatorID++, side);
    }


}
