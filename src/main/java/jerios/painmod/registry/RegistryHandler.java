package jerios.painmod.registry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jerios.painmod.PainMod;
import jerios.painmod.integerationHandler.baubbles.registry.BaubblesIntegration;
import jerios.painmod.world.teleport.PainTeleporter;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Arrays;

public class RegistryHandler {

    public static void registerPreInit() {
        ModItemBlocks.register();
        ModMobs.register();
        ModPacketHandler.register();
        registerDispenserBehaviour();
        ModGenerator.register();
        ModPotions.register();
        ModAchievements.register();
        ModBiomes.registerBiomes();
    }

    public static void registerInit() {
        if (Loader.isModLoaded("Baubles")) {
            BaubblesIntegration.integrate();
        }
    }

    public static void postInit() {
        for (int i = 0; i < Potion.potionTypes.length; i++) {
            Potion potion = Potion.potionTypes[i];

            if (potion != null && potion.isBadEffect()) {
                PainMod.tempBadPotionEffects.add(potion);
            }

        }

        PainMod.badPotionEffectsA = PainMod.tempBadPotionEffects.toArray(new Potion[]{});

        PainMod.tempBadPotionEffects.clear();

    }

    public static void onServerStart(FMLServerStartingEvent event) {

        GameRulesRegistry.register(event.getServer().getEntityWorld().getGameRules());

        WorldServer[] server = event.getServer().worldServers;


        for (int i = 0; i < server.length; i++) {
            WorldServer server1 = server[i];
            server1.customTeleporters.add(new PainTeleporter(server1));

        }



    }

    private static void registerDispenserBehaviour() {

    }


}
