package jerios.painmod.core.networking.packets.serverToClient;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import jerios.painmod.utils.props.PainProps;
import net.minecraft.entity.player.EntityPlayer;

public class STC01EscapedDim implements IMessage {


    public STC01EscapedDim() {

    }

    boolean hasEscaped = false;
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

            if (ctx.side == Side.CLIENT)
            {
               EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;

                PainProps props = PainProps.get(player);


                props.setEscapedDim(message.hasEscaped);


            }



            return null;
        }
    }



}
