package jerios.painmod.items.weapons;

import jerios.painmod.registry.TabsRegistry;
import jerios.painmod.utils.SidedCheckers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public class ItemDeathChainsaw extends ItemSword {
    public ItemDeathChainsaw(ToolMaterial material) {
        super(material);
        setCreativeTab(TabsRegistry.WEAPONS_TAB);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {


        if (SidedCheckers.isServer(player.worldObj) && SidedCheckers.isServer(entity.worldObj))
        {
            if (entity instanceof EntityLivingBase) {
                int counter = 0;
                ArrayList<EntityLivingBase> baseArrayList = new ArrayList<>();


             List<Entity> ents = player.worldObj.getEntitiesWithinAABBExcludingEntity(player,entity.boundingBox.expand(5,2,5));

                for (int i = 0; i < ents.size(); i++)
                {
                    Entity entity1 = ents.get(i);

                    if (entity1 instanceof EntityLivingBase)
                    {
                        EntityLivingBase base = (EntityLivingBase) entity1;

                        counter++;

                        baseArrayList.add(base);




                    }

                    if (i == ents.size() - 1)
                    {
                        for (int j = 0; j < baseArrayList.size(); j++)
                        {
                            EntityLivingBase entityLivingBase = baseArrayList.get(j);

                            entityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(player), counter);

                        }

                    }

                }



                return super.onLeftClickEntity(stack, player, entity);
            }

        }



        return super.onLeftClickEntity(stack, player, entity);
    }
}
