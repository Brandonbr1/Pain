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

// Thank you Overlord and DevMaster, I am a stupid!
public class PainFakePlayer extends EntityPlayerMP {

    EntityLiving entityLivingBase;
    public PainFakePlayer(WorldServer world, GameProfile name, EntityLiving entityLivingBase)
    {
        super(FMLCommonHandler.instance().getMinecraftServerInstance(), world, name, new ItemInWorldManager(world));
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

    @Override public boolean canCommandSenderUseCommand(int i, String s){ return false; }
    @Override public ChunkCoordinates getPlayerCoordinates()
    {
        return new ChunkCoordinates(0,0,0);
    }
    @Override public void addChatComponentMessage(IChatComponent chatmessagecomponent){}
    @Override public void addStat(StatBase par1StatBase, int par2){}
    @Override public void openGui(Object mod, int modGuiId, World world, int x, int y, int z){}
    @Override public boolean isEntityInvulnerable(){ return true; }
    @Override public boolean canAttackPlayer(EntityPlayer player){ return false; }
    @Override public void onDeath(DamageSource source){ return; }
    @Override public void onUpdate(){ return; }
    @Override public void travelToDimension(int dim){ return; }
    @Override public void func_147100_a(C15PacketClientSettings pkt){ return; }
}
