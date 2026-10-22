package jerios.painmod.client.items;

import cpw.mods.fml.common.Mod;
import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.utils.PGL;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
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

        if (itemstack.getItem() == ModItemBlocks.cursedBow && type == ItemRenderType.EQUIPPED)
        {
            PGL.popMatrix();


            final EntityLivingBase entity = (EntityLivingBase) data[1];
            float f2 = 0.625F;
            //0.1875F + 0.3125F = 0.5f
            //0.125 -0.1875 = 0.6225
            //0.3125F + 0.1875F = -.5
         //   0.3125 + 0.125 = 0.4375 // 0.437
            //0.625 + 0.437 = 1.062f scale
            //0.625 + 0.5 = 1.125f scale
            // try 0.1875F next?
            // 0.1250

        /**    PGL.translate(0.0f, -0.3125F, 0.3125F);
            PGL.rotate(10.0F, 0.0F, 1.0F, 0.0F);
            PGL.scale(1,1,1);
         **/

            f2 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);


            RenderManager.instance.itemRenderer.renderItem(entity, itemstack, 0, ItemRenderType.ENTITY);

         // x - 0.1875 = 0.125

          /** bow  f2 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(f2, -f2, f2);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            **/

            /**
             * 3d
            f2 = 0.625F;
            GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
            GL11.glScalef(f2, -f2, f2);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            **/

            PGL.pushMatrix();
        }
    }
}
