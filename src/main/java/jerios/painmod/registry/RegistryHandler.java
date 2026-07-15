package jerios.painmod.registry;

public class RegistryHandler {

    public static void registerPreInit() {
        ModItemBlocks.register();
        ModMobs.register();
        ModPacketHandler.register();
        registerDispenserBehaviour();
    }

    public static void registerInit() {
       ModAchievements.register();
    }

    private static void registerDispenserBehaviour() {

    }


}
