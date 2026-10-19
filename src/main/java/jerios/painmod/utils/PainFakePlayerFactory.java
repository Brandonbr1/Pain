package jerios.painmod.utils;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;

import java.util.Iterator;
import java.util.Map;

public class PainFakePlayerFactory {

    private static final Map<GameProfile, PainFakePlayer> fakePlayers = Maps.newHashMap();


    public static PainFakePlayer get(EntityLiving wrappedEntity, WorldServer world, GameProfile username)
    {
        if (!fakePlayers.containsKey(username))
        {
            PainFakePlayer fakePlayer = new PainFakePlayer(world, username, wrappedEntity);
            fakePlayers.put(username, fakePlayer);
        }
        return fakePlayers.get(username);
    }



    @SubscribeEvent
    public void unloadWorld(WorldEvent.Unload event)
    {
        if (event.world instanceof WorldServer) {
               WorldServer world = (WorldServer) event.world;
              Iterator<Map.Entry<GameProfile, PainFakePlayer>> itr = fakePlayers.entrySet().iterator();
              while (itr.hasNext())
              {
               Map.Entry<GameProfile, PainFakePlayer> entry = itr.next();
              if (entry.getValue().worldObj == world)
               {
             itr.remove();

            }
             }
        }

    }
}
