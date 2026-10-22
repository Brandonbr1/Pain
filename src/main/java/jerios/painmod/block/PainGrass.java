package jerios.painmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.registry.ModSounds;
import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class PainGrass extends Block {


    private static final String VANILLA_TEXTURE = "minecraft:grass";

    public PainGrass(String name) {
        super(Material.grass);
        setBlockName( name);
        setBlockTextureName("painmod:" + name);
        setCreativeTab(TabsRegistry.BLOCKS_TAB);
        setHardness(0.6F);
        setStepSound(ModSounds.dirtSound);
    }

    @SideOnly(Side.CLIENT)
    private IIcon field_149991_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_149993_M;
    @SideOnly(Side.CLIENT)
    private IIcon field_149994_N;

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.field_149991_b : (side == 0 ? Blocks.dirt.getBlockTextureFromSide(side) : this.blockIcon);
    }


    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.blockIcon = reg.registerIcon(this.getTextureName() + "_side");
        this.field_149991_b = reg.registerIcon(this.getTextureName() + "_top3");
        this.field_149993_M = reg.registerIcon(VANILLA_TEXTURE + "_side_snowed");
        this.field_149994_N = reg.registerIcon(VANILLA_TEXTURE + "_side_overlay");
    }


    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side)
    {
        if (side == 1)
        {
            return this.field_149991_b;
        }
        else if (side == 0)
        {
            return ModItemBlocks.painDirt.getBlockTextureFromSide(side);
        }
        else
        {
            Material material = worldIn.getBlock(x, y + 1, z).getMaterial();
            return material != Material.snow && material != Material.craftedSnow ? this.blockIcon : this.field_149993_M;
        }
    }


}
