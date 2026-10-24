package jerios.painmod.items.weapons;

import com.google.common.collect.Multimap;
import jerios.painmod.registry.ToolsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

// heals skeletons, does lots of damage to anything without a chestplate
public class ItemBoneSword extends ItemSword {
    public ItemBoneSword() {
        super(ToolsRegistry.BONE_MATERIAL);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        int totalDamage = 0;


        totalDamage = 20 - player.getTotalArmorValue();

        ItemStack chestplate = player.inventory.armorInventory[2];

        if (chestplate == null) {
            entity.attackEntityFrom(DamageSource.generic, totalDamage);
        }


        return true;
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)0, 0));
        return multimap;
    }


}
