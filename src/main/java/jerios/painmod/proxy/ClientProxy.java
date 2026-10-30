package jerios.painmod.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import jerios.painmod.client.RenderPainZombie;
import jerios.painmod.client.items.PainBowItemRender;
import jerios.painmod.entity.PainZombie;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy implements IProxy{


    @Override
    public void clientOnly() {
     //   renderEntity(PainZombie.class, new RenderPainZombie());
    }




    private void renderEntity(Class<? extends Entity> clazz, Render render) {
        RenderingRegistry.registerEntityRenderingHandler(clazz, render);
    }

    private void render2D(Class<? extends Entity> clazz, Item item) {
        renderEntity(clazz, new RenderSnowball(item));
    }

















    @Override
    public void serverOnly() {

    }
}
