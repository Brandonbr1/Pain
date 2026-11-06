package jerios.painmod.core.networking.packets.serverToClient;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import jerios.painmod.core.world.data.PainWorldData;

public class STCStarRandom implements IMessage {

    public STCStarRandom(){}

    long random;
    public STCStarRandom(long random) {
        this.random = random;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        random = buf.readLong();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(random);
    }


    public static class HandlerRandom implements IMessageHandler<STCStarRandom, IMessage> {

        @Override
        public IMessage onMessage(STCStarRandom message, MessageContext ctx) {

            if (ctx.side == Side.CLIENT) {

                PainWorldData worldData = PainWorldData.get(FMLClientHandler.instance().getWorldClient());

                worldData.setSkySeed(message.random);


            }


            return null;
        }
    }


}
