package jerios.painmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jerios.painmod.config.PainConfig;
import jerios.painmod.damageSource.RagePainSource;
import jerios.painmod.events.PainEvents;
import jerios.painmod.proxy.IProxy;
import jerios.painmod.registry.RegistryHandler;
import jerios.painmod.utils.PainFakePlayerFactory;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

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

    public static final DamageSource RAGE = new RagePainSource();

    public static final List<Potion> tempBadPotionEffects = new ArrayList<>();
    public static Potion[] badPotionEffectsA;


    // jealously makes your pets mad at you
    // pride purposely makes you act overconfident(throws away your stuff and replaces with worse armor caue 'you are too good)

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PainConfig.syncConfig(new Configuration(event.getSuggestedConfigurationFile()));
        RegistryHandler.registerPreInit();
        proxy.clientOnly();
        proxy.serverOnly();
        registerForgeEvent(new PainFakePlayerFactory());
        registerBothEvents(new PainEvents());
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
        RegistryHandler.registerInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RegistryHandler.postInit();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        RegistryHandler.onServerStart(event);

    }
}