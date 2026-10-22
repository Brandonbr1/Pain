package jerios.painmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockPainLogs extends BlockLog {

    public BlockPainLogs()
    {
        super();
        this.setCreativeTab(TabsRegistry.BLOCKS_TAB);
        this.setBlockName("log");
        this.setBlockTextureName("painmod:log");
    }

    public static final String[] LOGS = new String[] {"acacia", "big_oak"};

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }



    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.field_150167_a = new IIcon[LOGS.length];
        this.field_150166_b = new IIcon[LOGS.length];

        for (int i = 0; i < this.field_150167_a.length; ++i)
        {
            this.field_150167_a[i] = reg.registerIcon(this.getTextureName() + "_" + LOGS[i]);
            this.field_150166_b[i] = reg.registerIcon(this.getTextureName() + "_" + LOGS[i] + "_top");
        }
    }


}
