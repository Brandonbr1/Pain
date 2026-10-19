package jerios.painmod.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

public class PainEventsClient {

    Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void onTickClient(TickEvent.ClientTickEvent event) {
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active
    }

}
