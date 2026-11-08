package jerios.painmod.core.registry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jerios.painmod.core.command.CommandPainWeather;
import jerios.painmod.core.command.CommandSoftlock;
import jerios.painmod.forge.PainShare;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.core.effects.registry.ModPotions;
import jerios.painmod.core.entity.registry.ModMobs;
import jerios.painmod.core.block.fluid.registry.ModFluidRegistry;
import jerios.painmod.forge.integerationHandler.baubbles.registry.BaubblesIntegration;
import jerios.painmod.core.items.registry.ModItems;
import jerios.painmod.core.items.spawner.ItemPainSpawnEgg;
import jerios.painmod.core.registry.misc.GameRulesRegistry;
import jerios.painmod.core.networking.registry.PainPacketHandler;
import jerios.painmod.core.achievments.PainAchievements;
import jerios.painmod.core.world.registry.ModBiomes;
import jerios.painmod.core.world.registry.ModGenerator;
import jerios.painmod.core.world.teleport.PainTeleporter;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.ArrayUtils;

public class PainRegistryHandler {

    public static void registerPreInit() {
        ModItems.register();
        ModMobs.register();
        PainPacketHandler.register();
        registerDispenserBehaviour();
        ModGenerator.register();
        ModPotions.register();
        PainAchievements.register();
        ModBiomes.register();
        ModFluidRegistry.register();
    }

    public static void registerInit() {
        if (Loader.isModLoaded("Baubles")) {
            BaubblesIntegration.integrate();
        }
    }

    public static void postInit() {

        ResourceLocation[] resourceLocation = EntityRenderer.shaderResourceLocations;
    //    ResourceLocation[] myPath = new ResourceLocation[]{new ResourceLocation("painmod:shaders/post/painShaders.json")};
        ResourceLocation[] merged =  ArrayUtils.add(resourceLocation, new ResourceLocation("minecraft:shaders/post/painShaders.json"));

        EntityRenderer.shaderResourceLocations = merged;

        EntityRenderer.shaderCount = EntityRenderer.shaderResourceLocations.length;


        for (int i = 0; i < Potion.potionTypes.length; i++) {
            Potion potion = Potion.potionTypes[i];

            if (potion != null && potion.isBadEffect()) {
                PainShare.tempBadPotionEffects.add(potion);
            }

        }

        PainShare.badPotionEffectsA = PainShare.tempBadPotionEffects.toArray(new Potion[]{});

        PainShare.tempBadPotionEffects.clear();

    }

    public static void onServerStart(FMLServerStartingEvent event) {

        GameRulesRegistry.register(event.getServer().worldServers[PainConfig.painDimensionID].getGameRules());

        WorldServer[] server = event.getServer().worldServers;

        event.registerServerCommand(new CommandPainWeather());
        event.registerServerCommand(new CommandSoftlock());


        for (int i = 0; i < server.length; i++) {
            WorldServer server1 = server[i];
            if (server1 != null) {
                server1.customTeleporters.add(new PainTeleporter(server1));
            }
        }



    }

    private static void registerDispenserBehaviour() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.spawnEgg, new BehaviorDefaultDispenseItem() {

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
