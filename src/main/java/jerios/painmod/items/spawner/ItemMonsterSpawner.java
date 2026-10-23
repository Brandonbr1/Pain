package jerios.painmod.items.spawner;

import java.util.List;

import jerios.painmod.items.ItemPainSpawnEgg;
import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMonsterSpawner extends ItemBlock {

    public ItemMonsterSpawner(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setCreativeTab(TabsRegistry.SPAWN_EGG_TAB);
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        String s = (StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String s1 = ItemPainSpawnEgg.INTEGER_STRING_MAP.get(p_77653_1_.getItemDamage());

        if (s1 != null) {
            s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
        }

        return s;
    }

    public String getEntityNameOnly(ItemStack p_77653_1_) {
        String s1 = ItemPainSpawnEgg.INTEGER_STRING_MAP.get(p_77653_1_.getItemDamage());

        if (s1 != null) {
            return StatCollector.translateToLocal("entity." + s1 + ".name");
        }

        return null;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing,
                             float unknown, float unkown2, float unkown3) {

        if (!world.isRemote) {


            x += Facing.offsetsXForSide[facing];
            y += Facing.offsetsYForSide[facing];
            z += Facing.offsetsZForSide[facing];


            world.setBlock(x, y, z, Blocks.mob_spawner, 0, 3);
            world.markBlockForUpdate(x,y,z);

            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityMobSpawner) {
                TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner) tileEntity;
                String name = ItemPainSpawnEgg.STRING_ID_MAP.get(stack.getItemDamage());
                if (name != null) {
                    tileEntityMobSpawner.func_145881_a()
                            .setEntityName(name);
                }
            }

            if (!player.capabilities.isCreativeMode) {
                --stack.stackSize;
            }

        }

        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean p_77624_4_) {

        EntityLiving entity = ItemPainSpawnEgg.createEntityByID(stack.getItemDamage(), player.worldObj);

        if (entity instanceof IMob && getEntityNameOnly(stack) != null) {
            list.add(EnumChatFormatting.RED + getEntityNameOnly(stack));
        } else {
            list.add(EnumChatFormatting.BLUE + getEntityNameOnly(stack));
        }

        super.addInformation(stack, player, list, p_77624_4_);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> stacks) {
        for (ItemPainSpawnEgg.EGGSpots entityegginfo : ItemPainSpawnEgg.INTEGER_COLOR_MAP.values()) {
            stacks.add(new ItemStack(item, 1, entityegginfo.id));
        }

    }



}
