package jerios.painmod.items.weapons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.registry.TabsRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

// Make it apply every bad potion effect in the game + high dam
public class ItemBowOfCursing extends ItemBow {

    public ItemBowOfCursing()
    {
        super();
        setUnlocalizedName("painBow");
        setTextureName("painmod:painBow");
        setCreativeTab(TabsRegistry.WEAPONS_TAB);
      //  setFull3D(); // Rcreates thee more bows holding bow incorrectly bug.
    }


    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int itemInUseCount)
    {
        int j = this.getMaxItemUseDuration(itemstack) - itemInUseCount;

        ArrowLooseEvent event = new ArrowLooseEvent(entityplayer, itemstack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

        if (flag || entityplayer.inventory.hasItem(Items.arrow))
        {
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(world, entityplayer, f * 2.0F);


            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
            {
                entityarrow.setFire(100);
            }

            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
               int invL = entityplayer.inventory.mainInventory.length;

                for (int i = 0; i < invL; i++) {
                  ItemStack stack = entityplayer.inventory.getStackInSlot(i);

                  if (stack != null)
                  {
                      if (stack.getItem() != this) {
                          entityplayer.inventory.mainInventory[i] = null;
                          break;
                      }
                  }


                }
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getItemIcon(ItemStack itemStackIn, int p_70620_2_)
    {
        return itemStackIn.getItem().requiresMultipleRenderPasses() ? itemStackIn.getItem().getIconFromDamageForRenderPass(itemStackIn.getItemDamage(), p_70620_2_) : itemStackIn.getIconIndex();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return this.getIconFromDamage(p_77618_1_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.itemIcon;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return super.getIcon(stack, pass);
    }

   /** @SideOnly(Side.CLIENT)
    public IIcon getIconIndex()
    {
        return this.getItem().getIconIndex(this);
    }
   **/
}
