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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemPainPotion extends ItemPotion {

  public static Map<Integer,PotionEffect> integerPotionsPropsMap = new LinkedHashMap<>();


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
                PotionEffect props = integerPotionsPropsMap.get(stack.getItemDamage());
                entityLiving.addPotionEffect(props);
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
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer world, List<String> tooltip, boolean flag)
    {
        if (stack.getItemDamage() != 0)
        {
            PotionEffect potioneffect = integerPotionsPropsMap.get(stack.getItemDamage());
            String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();

            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            if (potioneffect.getAmplifier() > 0)
            {
                s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
            }

            if (potioneffect.getDuration() > 20)
            {
                s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
            }

            if (potion.isBadEffect())
            {
                tooltip.add(EnumChatFormatting.RED + s1);
            }
            else
            {
                tooltip.add(EnumChatFormatting.GRAY + s1);

            }
        }

    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        PotionEffect props = integerPotionsPropsMap.get(stack.getItemDamage());
        if (props != null) {
            return StatCollector.translateToLocal(props.getEffectName());
        }
        return "";
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item tab, CreativeTabs items, List<ItemStack> subItems)
    {
        for (PotionEffect props : integerPotionsPropsMap.values()) {
            subItems.add(new ItemStack(tab, 1, props.getPotionID()));
        }
    }

    public static class PotionsProps{
        public PotionEffect effect;
        public int throwablePotionID;
        public PotionsProps(int potionID, int duration, int potency) {
            this.effect = new PotionEffect(potionID, duration, potency);
        }

        public PotionsProps(int potionID, int duration, int potency, int throwablePotionID) {
            this.effect = new PotionEffect(potionID, duration, potency);
            this.throwablePotionID = potionID + 400;
        }
    }



}
