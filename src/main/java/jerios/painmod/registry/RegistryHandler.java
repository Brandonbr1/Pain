package jerios.painmod.registry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jerios.painmod.PainMod;
import jerios.painmod.integerationHandler.baubbles.registry.BaubblesIntegration;
import jerios.painmod.items.ItemPainSpawnEgg;
import jerios.painmod.world.teleport.PainTeleporter;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
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
            if (server1 != null) {
                server1.customTeleporters.add(new PainTeleporter(server1));
            }
        }



    }

    private static void registerDispenserBehaviour() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItemBlocks.spawnEgg, new BehaviorDefaultDispenseItem() {

            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
                double d0 = source.getX() + (double) enumfacing.getFrontOffsetX();
                double d1 = (float) source.getYInt() + 0.2F;
                double d2 = source.getZ() + (double) enumfacing.getFrontOffsetZ();
                Entity entity = ItemPainSpawnEgg.spawnCreature(source.getWorld(), stack.getItemDamage(), d0, d1, d2);

                if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
                }

                stack.splitStack(1);
                return stack;
            }
        });


    }


}
