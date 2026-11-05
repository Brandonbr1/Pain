package jerios.painmod.client.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.core.entity.PainZombie;
import jerios.painmod.utils.locations.ResourceLocationMaker;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderPainZombie extends RenderZombie {

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((PainZombie)entity);
    }


    protected ResourceLocation getEntityTexture(PainZombie entity)
    {
        return ResourceLocationMaker.makeResourceLocationEntityHostile("pain_zombie");
    }

}
