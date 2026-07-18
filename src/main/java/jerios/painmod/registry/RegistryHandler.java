package jerios.painmod.registry;

public class RegistryHandler {

    public static void registerPreInit() {
        ModItemBlocks.register();
        ModMobs.register();
        ModPacketHandler.register();
        registerDispenserBehaviour();
        ModGenerator.register();
        ModPotions.register();
        ModAchievements.register();
    }

    public static void registerInit() {

    }

    private static void registerDispenserBehaviour() {

    }


}
