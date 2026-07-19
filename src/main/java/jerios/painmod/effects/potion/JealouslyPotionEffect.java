package jerios.painmod.effects.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

import java.util.List;

public class JealouslyPotionEffect extends Potion {
    public JealouslyPotionEffect(int id, int color) {
        super(id, false, color);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if (entityLivingBase instanceof EntityPlayer)  {
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            World world = player.worldObj;
            List<Entity> loadList = world.getLoadedEntityList();
            for (int i = 0; i < loadList.size(); i++) {
                Entity entity = loadList.get(i);

                if (entity instanceof EntityTameable) {
                    EntityTameable tameable = (EntityTameable) entity;
                    if (tameable.isOnSameTeam(player)) {
                        tameable.setTamed(false);
                        tameable.func_152115_b(null);
                        tameable.setAttackTarget(player);
                    }
                }

            }
        }
    }
}
