package jerios.painmod.utils;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

// Thank you Overlord and DevMaster, I am a stupid!
public class PainFakePlayer extends FakePlayer {

    EntityLiving entityLivingBase;
    public PainFakePlayer(WorldServer world, GameProfile name, EntityLiving entityLivingBase)
    {
        super(world, name);
        this.entityLivingBase = entityLivingBase;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (entityLivingBase == null) return false;
        return entityLivingBase.attackEntityFrom(source, amount);
    }

    @Override
    public void setInWeb() {
        if (entityLivingBase != null) {
            entityLivingBase.setInWeb();
        } else {
            super.setInWeb();
        }
    }

  /**  @Override
    public World getEntityWorld()
    {
        return entityLivingBase.worldObj;
    }
   **/

    @Override
    public float getAIMoveSpeed()
    {
        if (entityLivingBase != null) {
            return (float) entityLivingBase.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        } else {
            return super.getAIMoveSpeed();
        }
    }

    @Override
    public boolean canEat(boolean ignoreHunger)
    {
        return true;
    }

    @Override
    public void setFire(int seconds) {
        if (entityLivingBase != null) {
            entityLivingBase.setFire(seconds);
        } else {
            super.setFire(seconds);
        }
    }

    @Override
    public void addPotionEffect(PotionEffect potioneffect) {
        if (entityLivingBase != null) {
            entityLivingBase.addPotionEffect(potioneffect);
        } else {
            super.addPotionEffect(potioneffect);
        }
    }

    @Override
    public void heal(float healAmount) {
        if (entityLivingBase != null) {
            entityLivingBase.heal(healAmount);
        } else {
            super.heal(healAmount);
        }
    }

    @Override
    public IAttributeInstance getEntityAttribute(IAttribute attribute)
    {
        if (entityLivingBase != null) {
            return entityLivingBase.getAttributeMap().getAttributeInstance(attribute);
        } else {
            return this.getAttributeMap().getAttributeInstance(attribute);
        }
    }
}
