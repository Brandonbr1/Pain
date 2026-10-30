package jerios.painmod.client.items;

import jerios.painmod.items.ItemPainSpawnEgg;
import jerios.painmod.items.spawner.ItemMonsterSpawner;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class MonsterSpawnerRender implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {

        if (type == ItemRenderType.EQUIPPED || type ==  ItemRenderType.EQUIPPED_FIRST_PERSON)
        {


            if (item.getItem() == Item.getItemFromBlock(ModItemBlocks.spawner)) {

                ItemMonsterSpawner spawner = (ItemMonsterSpawner) item.getItem();

                EntityLiving living = ItemPainSpawnEgg.createEntityByID(item.getItemDamage(), Minecraft.getMinecraft().theWorld);

                if (living != null) {
                    float f1 = 0.4375F;

                    GL11.glPushMatrix();

                    GL11.glTranslatef((float) 0 + 0.5F, (float) 0, (float) 0 + 0.5F);

                    GL11.glTranslatef(0.0F, 0.4F, 0.0F);

                    // GL11.glRotatef((float)(mobSpawnerLogic.field_98284_d + (mobSpawnerLogic.field_98287_c - mobSpawnerLogic.field_98284_d) * (double)partialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);

                    GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);

                    GL11.glTranslatef(0.0F, -0.4F, 0.0F);

                    GL11.glScalef(f1, f1, f1);

                    living.setLocationAndAngles(0, 0, 0, 0.0F, 0.0F);

                    RenderManager.instance.renderEntityWithPosYaw(living, 0.0D, 0.0D, 0.0D, 0.0F, 0);

                    GL11.glPopMatrix();
                }
            }



        }


    }







}
