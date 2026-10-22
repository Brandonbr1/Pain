package jerios.painmod.block;

import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BlockCorruptedDarknessBlock extends Block {
    public BlockCorruptedDarknessBlock() {
        super(Material.iron);
        setBlockName("corruptedDarknessBlock");
        setBlockTextureName("painmod:corruptedDarknessBlock");
        setCreativeTab(TabsRegistry.BLOCKS_TAB);
    }


    @Override
    public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, x, y, z, entityIn);
        if (entityIn instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase) entityIn;

            base.addPotionEffect(new PotionEffect(Potion.blindness.id, 120, 1));
        }
    }
}
