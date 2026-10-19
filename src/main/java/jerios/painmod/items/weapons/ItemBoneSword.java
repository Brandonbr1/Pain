package jerios.painmod.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

// heals skeletons, does lots of damage to anything without a chestplate
public class ItemBoneSword extends ItemSword {
    public ItemBoneSword(ToolMaterial material) {
        super(material);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {







        return super.onLeftClickEntity(stack, player, entity);
    }
}
