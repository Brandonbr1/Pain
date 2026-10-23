package jerios.painmod.client.items;

import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.utils.PGL;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class PainBowItemRender implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return item.getItem() == ModItemBlocks.cursedBow && type ==  ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data)
    {
        Item item =  itemstack.getItem();
        if (item == ModItemBlocks.cursedBow && type == ItemRenderType.EQUIPPED)
        {
            final EntityLivingBase entity = (EntityLivingBase) data[1];
            PGL.popMatrix();

            /** BOW STACK
             *                 f2 = 0.625F;
             *                 GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
             *                 GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
             *                 GL11.glScalef(f2, -f2, f2);
             *                 GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
             *                 GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
             */

            /**
             *  f2 = 0.625F;
             *  THE 3D STACK
             GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
             GL11.glScalef(f2, -f2, f2);
             GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
             GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
             */

            // 0.1953125F
            GL11.glTranslatef(-0.1953F, 0.0f, 0.625F);
            GL11.glTranslatef(0.0f, 0.625f, 0.0f);
           // GL11.glRotatef(20, 0.0F, 1.0F, 0.0F);
          //  GL11.glTranslatef(-0.1953F, -0.3125F, 0.625f);
            //GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);

            RenderManager.instance.itemRenderer.renderItem(entity, itemstack, 0, ItemRenderType.ENTITY);

            PGL.pushMatrix();

        }
    }
}
