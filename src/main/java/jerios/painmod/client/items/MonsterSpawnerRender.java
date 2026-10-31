package jerios.painmod.client.items;

import cpw.mods.fml.client.FMLClientHandler;
import jerios.painmod.items.ItemPainSpawnEgg;
import jerios.painmod.items.spawner.ItemMonsterSpawner;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class MonsterSpawnerRender implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    public double field_98287_c;
    public double field_98284_d;

    Minecraft mc = FMLClientHandler.instance().getClient();


    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    protected final RenderItem itemRenderer = new RenderItem();

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY
        ) {
            RenderBlocks renderBlocksIr = (RenderBlocks) data[0];
            if (item.getItem() == Item.getItemFromBlock(ModItemBlocks.spawner)) {

                ItemMonsterSpawner spawner = (ItemMonsterSpawner) item.getItem();

                EntityLiving living = ItemPainSpawnEgg.createEntityByID(item.getItemDamage(), mc.theWorld);

                if (living != null) {

                    living.setWorld(mc.theWorld);

                    GL11.glPushMatrix();

                    GL11.glTranslatef((float) 0.5F, (float) 0.5f, (float) 0.5F);
                    mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                    renderBlocksIr.renderBlockAsItem(Blocks.mob_spawner, 0, 1.0F);

                    float f1 = 0.4375F;
                    GL11.glTranslatef(0.0F, 0.4F, 0.0F);

                    GL11.glRotatef((float) (field_98284_d + (field_98287_c - field_98284_d) * (double) 0.5F) * 10.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                    GL11.glScalef(f1, f1, f1);


                    living.setLocationAndAngles(0, 0, 0, 0.0F, 0.0F);

                    RenderManager.instance.renderEntityWithPosYaw(living, 0.0D, 0.0D, 0.0D, 0.0F, 0);

                    GL11.glPopMatrix();
                }
            }


        } else if (type == ItemRenderType.INVENTORY) {
            if (item.getItem() == Item.getItemFromBlock(ModItemBlocks.spawner))
            {
                RenderBlocks renderBlocksIr = (RenderBlocks) data[0];



                EntityLiving living = ItemPainSpawnEgg.createEntityByID(item.getItemDamage(), mc.theWorld);

                if (living != null) {
                    living.setWorld(mc.theWorld);

                    float f1 = 0.1375F;
                    float f2 = 0.4375F;

                    GL11.glPushMatrix();

              //      GL11.glRotatef(25.0F, 1.0F, 0.0F, 0.0F);

              //      GL11.glScalef(f1, f1, f1);
                  //  RenderManager.instance.itemRenderer.renderItem(mc.thePlayer, item, 0, ItemRenderType.INVENTORY);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_CULL_FACE);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                   mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                    GL11.glDepthMask(false);
                    renderBlocksIr.renderBlockAsItem(Blocks.mob_spawner, 0, 1.0F);
              //      itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, 0, 0);
                    GL11.glDepthMask(true);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();


                    this.field_98284_d = this.field_98287_c;
                    this.field_98287_c = (this.field_98287_c + (double) (1000.0F / ((float) 20 + 200.0F))) % 360.0D;


                    GL11.glRotatef((float) (field_98284_d + (field_98287_c - field_98284_d) * (double) 0.5D) * 10.0F, 0.0F, 1.0F, 0.0F);



                    living.setLocationAndAngles(0, 0, 0, 0.0F, 0.0F);

                    RenderManager.instance.renderEntityWithPosYaw(living, 0.0D, 0.0D, 0.0D, 0.0F, 0);
                    GL11.glPopMatrix();

                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);


                }





            }


        }


    }


}
