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


            GL11.glTranslatef(0.0F, -0.0925f, 0.3125F);
            GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);


            RenderManager.instance.itemRenderer.renderItem(entity, itemstack, 0, ItemRenderType.ENTITY);



            PGL.pushMatrix();

        }
    }
}
