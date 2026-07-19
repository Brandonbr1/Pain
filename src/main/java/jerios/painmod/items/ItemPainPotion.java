package jerios.painmod.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemPainPotion extends ItemPotion {

  public static Map<Integer,PotionsProps> integerPotionsPropsMap = new LinkedHashMap<>();


    public ItemPainPotion()
    {
        super();
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer entityLiving)
    {
        if (!entityLiving.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

        if (!world.isRemote)
        {
                PotionsProps props = integerPotionsPropsMap.get(stack.getItemDamage());
                entityLiving.addPotionEffect(props.effect);
        }

        if (!entityLiving.capabilities.isCreativeMode)
        {
            if (stack.stackSize <= 0)
            {
                return new ItemStack(Items.glass_bottle);
            }

            entityLiving.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }

        return stack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player)
    {
            player.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
            return itemStackIn;

    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }


    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.drink;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float subX, float subY, float subZ)
    {
        return false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer world, List<String> tooltip, boolean flag)
    {
            PotionsProps props = integerPotionsPropsMap.get(stack.getItemDamage());
            if (props != null) {
                tooltip.add(props.potionName + " Potency: " + props.potency + "Duration In Ticks: " + props.duration);
            }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        PotionsProps props = integerPotionsPropsMap.get(stack.getItemDamage());
        if (props != null) {
            return props.potionName;
        }
        return "";
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item tab, CreativeTabs items, List<ItemStack> subItems)
    {
        for (PotionsProps props : integerPotionsPropsMap.values()) {
            subItems.add(new ItemStack(tab, 1, props.potionID));
        }

    }

    public static class PotionsProps{
        public String potionName;
        public int color1;
        public int duration;
        public int potency;
        public PotionEffect effect;
        public int potionID;

        public PotionsProps(String potionName, int color1, int duration, int potency, int id) {
            this.potionName =potionName;
            this.color1 = color1;
            this.duration = duration;
            this.potency = potency;
            this.potionID = id;
            this.effect = new PotionEffect(potionID, duration, potency);
        }
    }


}
