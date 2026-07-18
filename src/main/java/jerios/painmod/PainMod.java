package jerios.painmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jerios.painmod.config.PainConfig;
import jerios.painmod.events.PainEvents;
import jerios.painmod.proxy.CommonProxy;
import jerios.painmod.proxy.IProxy;
import jerios.painmod.registry.GameRulesRegistry;
import jerios.painmod.registry.RegistryHandler;
import jerios.painmod.utils.PainFakePlayerFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.MOD_VERSION,
        acceptedMinecraftVersions = "[1.7.10]")
public class PainMod {
    @SidedProxy(clientSide = Tags.ROOT_PKG + ".proxy.ClientProxy", serverSide = Tags.ROOT_PKG + ".proxy.CommonProxy")
    public static IProxy proxy;

    @Mod.Instance(Tags.MOD_ID)
    public static PainMod INSTANCE;

    public static final String MODID = Tags.MOD_ID;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PainConfig.syncConfig(new Configuration(event.getSuggestedConfigurationFile()));
        RegistryHandler.registerPreInit();
        proxy.clientOnly();
        proxy.serverOnly();
        MinecraftForge.EVENT_BUS.register(new PainFakePlayerFactory());
        MinecraftForge.EVENT_BUS.register(new PainEvents());
        FMLCommonHandler.instance().bus().register(new PainEvents());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        RegistryHandler.registerInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        GameRulesRegistry.register(event.getServer().getEntityWorld().getGameRules());
    }
}