package jerios.painmod.core.networking.packets.serverToClient;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class STCStarRandom implements IMessage {

    public STCStarRandom(){}

    long random;
    public STCStarRandom(long random) {
        this.random = random;
    }


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }
}
