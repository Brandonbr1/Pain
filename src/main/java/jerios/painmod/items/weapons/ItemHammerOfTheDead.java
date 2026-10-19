package jerios.painmod.items.weapons;

import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class ItemHammerOfTheDead extends ItemSword {

    public ItemHammerOfTheDead(ToolMaterial material) {
        super(material);
        setCreativeTab(TabsRegistry.WEAPONS_TAB);
    }



    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        if (worldIn.isRemote) {
            return itemStackIn;
        } else {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, player, true);

            if (movingobjectposition != null) {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!worldIn.canMineBlock(player, i, j, k)) {
                        return itemStackIn;
                    }

                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStackIn)) {
                        return itemStackIn;
                    }


                   List<Entity> entityList = worldIn.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(i,j,k).expand(2,2,2));

                    for (int l = 0; l < entityList.size(); l++) {
                        Entity entity = entityList.get(i);

                        if (entity instanceof EntityLivingBase ) {
                            EntityLivingBase b = (EntityLivingBase) entity;


                        }

                    }



                }

            }
            return itemStackIn;
        }
    }
}
