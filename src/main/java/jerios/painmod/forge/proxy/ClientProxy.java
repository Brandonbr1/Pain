package jerios.painmod.forge.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import jerios.painmod.client.items.MonsterSpawnerRender;
import jerios.painmod.core.items.registry.ModItems;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.EnumHelper;

public class ClientProxy extends CommonProxy implements IProxy{

   static Class[][] c = new Class[][] {{MusicTicker.MusicType.class, ResourceLocation.class, int.class, int.class}};

    public static final MusicTicker.MusicType PAIN_MUSIC = EnumHelper.addEnum(c, MusicTicker.MusicType.class,  "PAIN_MOD_GAME", new ResourceLocation("painmod:music.game"), 300, 900);
    public static final MusicTicker.MusicType ESCAPED_MUSIC = EnumHelper.addEnum(c, MusicTicker.MusicType.class,  "PAIN_MOD_ESCAPED", new ResourceLocation("painmod:music.escaped"), 2, 3);


    @Override
    public void clientOnly() {
        MinecraftForgeClient.registerItemRenderer( Item.getItemFromBlock(ModItems.spawner), new MonsterSpawnerRender());
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
