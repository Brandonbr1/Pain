package jerios.painmod.forge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jerios.painmod.Tags;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.forge.events.PainEventsClient;
import jerios.painmod.forge.events.PainEventsServer;
import jerios.painmod.forge.proxy.IProxy;
import jerios.painmod.core.registry.PainRegistryHandler;
import jerios.painmod.utils.fakePlayer.PainFakePlayerFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.MOD_VERSION,
        acceptedMinecraftVersions = "[1.7.10]")
public class PainMod {
    @SidedProxy(clientSide = Tags.ROOT_PKG + ".forge.proxy.ClientProxy", serverSide = Tags.ROOT_PKG + ".forge.proxy.CommonProxy")
    public static IProxy proxy;

    @Mod.Instance(Tags.MOD_ID)
    public static PainMod INSTANCE;

    public static final String MODID = Tags.MOD_ID;


    // jealously makes your pets mad at you
    // pride purposely makes you act overconfident(throws away your stuff and replaces with worse armor caue 'you are too good)

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PainConfig.syncConfig(new Configuration(event.getSuggestedConfigurationFile()));
        PainRegistryHandler.registerPreInit();
        proxy.clientOnly();
        proxy.serverOnly();
        registerForgeEvent(new PainFakePlayerFactory());
        registerBothEvents(new PainEventsServer());
   //     registerBothEvents(new PainEventsClient());
    }

    public void registerBothEvents(Object o){
        registerForgeEvent(o);
        registerFMLEvent(o);
    }

    public void registerForgeEvent(Object object) {
        MinecraftForge.EVENT_BUS.register(object);
    }
    public void registerFMLEvent(Object object) {
        FMLCommonHandler.instance().bus().register(object);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PainRegistryHandler.registerInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PainRegistryHandler.postInit();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        PainRegistryHandler.onServerStart(event);

    }
}