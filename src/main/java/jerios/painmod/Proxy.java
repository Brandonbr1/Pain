package jerios.painmod;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface Proxy {

    class Common implements Proxy {
        @Override
        public void clientProxy() {

        }

        @Override
        public void preInit(FMLPreInitializationEvent event) {
            Share.LOG.info("I am " + Tags.MOD_NAME + " at version " + Tags.MOD_VERSION + " and root package " + Tags.ROOT_PKG);
        }

        @Override
        public void init(FMLInitializationEvent event) {

        }

        @Override
        public void postInit(FMLPostInitializationEvent event) {

        }
    }

    @SuppressWarnings("unused")
    class Client extends Common {

        @Override
        public void clientProxy() {

        }
    }

    @SuppressWarnings("unused")
    class Server extends Common {

    }

     void clientProxy();

    void preInit(FMLPreInitializationEvent event);
    void init(FMLInitializationEvent event);
    void postInit(FMLPostInitializationEvent event);
}
