package jerios.painmod.core.networking.packets.clientToServer;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import jerios.painmod.core.networking.packets.serverToClient.STCEscapedDim;
import jerios.painmod.forge.props.PainProps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class CTSPacketMusic implements IMessage {

    public CTSPacketMusic() {

    }

    boolean musicPlayed1;
    boolean musicPlayed2;
    public CTSPacketMusic(boolean musicPlayed1, boolean musicPlayed2) {
        this.musicPlayed1 = musicPlayed1;
        this.musicPlayed2 = musicPlayed2;
    }



    @Override
    public void fromBytes(ByteBuf buf) {
        this.musicPlayed1 = buf.readBoolean();
        this.musicPlayed2 = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.musicPlayed1);
        buf.writeBoolean(this.musicPlayed2);

    }

    public static class PacketMusicHandler implements IMessageHandler<CTSPacketMusic, IMessage> {

        @Override
        public IMessage onMessage(CTSPacketMusic message, MessageContext ctx) {

            if (ctx.side == Side.SERVER) {
                EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;

                PainProps props = PainProps.get(playerEntity);

                if (props != null) {
                    props.hasPlayedMusic1 = message.musicPlayed1;
                    props.hasPlayedMusic2 = message.musicPlayed2;
                }
            }


            return null;
        }
    }
}
