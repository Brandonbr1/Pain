package jerios.painmod.items.essence;

import jerios.painmod.PainConstants;
import jerios.painmod.PainMod;
import jerios.painmod.config.PainConfig;
import jerios.painmod.registry.TabsRegistry;
import jerios.painmod.utils.SidedCheckers;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Objects;

public class ItemHarmfulEssence extends Item {

    public ItemHarmfulEssence() {
        setUnlocalizedName("harmfulessence");
        setTextureName("harmfulessence");
        setCreativeTab(TabsRegistry.ITEM_TAB);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (SidedCheckers.isServer(worldIn))
        {
            final Potion[] badPotions = PainMod.badPotionEffectsA;
            final String[] names = PainConfig.blacklistedPotionsHarmfulEssence;

            if (entityIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityIn;

                if (player.ticksExisted % 5 == 0) {
                    Potion potion = badPotions[PainConstants.rand.nextInt(badPotions.length)];

                    if (potion != null)
                    {
                        for (int i = 0; i < names.length; i++)
                        {
                            if (!Objects.equals(potion.getName(), names[i]))
                            {
                                player.addPotionEffect(new PotionEffect(potion.id, 5, 2));
                            }
                        }


                    }

                }
            }



        }


    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = Items.gold_ingot.getIconFromDamage(1);
    }
}
