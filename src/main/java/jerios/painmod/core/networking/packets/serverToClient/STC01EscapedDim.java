package jerios.painmod.core.networking.packets.serverToClient;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jerios.painmod.forge.props.PainProps;

public class STC01EscapedDim implements IMessage {


    public STC01EscapedDim() {

    }

    boolean hasEscaped;
    public STC01EscapedDim(boolean hasEscaped) {
        this.hasEscaped = hasEscaped;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.hasEscaped = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.hasEscaped);
    }


    public static class EscapedDimMessageHandler implements IMessageHandler<STC01EscapedDim, IMessage> {

        @Override
        public IMessage onMessage(STC01EscapedDim message, MessageContext ctx) {

            //if (ctx.side == Side.CLIENT)
           // {

                PainProps props = PainProps.get(FMLClientHandler.instance().getClient().thePlayer);
                props.setEscapedDim(message.hasEscaped);


        //    }



            return null;
        }
    }



}
