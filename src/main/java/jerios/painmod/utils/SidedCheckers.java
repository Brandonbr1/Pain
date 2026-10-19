package jerios.painmod.utils;

import net.minecraft.world.World;

public class SidedCheckers {

    public static boolean isClient(World world) {
        return world.isRemote;
    }
    public static boolean isServer(World world) {
        return !world.isRemote;
    }



}
