package jerios.painmod.items;

import jerios.painmod.registry.TabsRegistry;
import net.minecraft.item.ItemMonsterPlacer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.*;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPainSpawnEgg extends ItemMonsterPlacer {

    public static final Map<Integer, Class<?>> INTEGER_CLASS_MAP = new HashMap<>(32);
    public static final Map<Integer, String> INTEGER_STRING_MAP = new HashMap<>(32);
    public static final Map<Integer, EGGSpots> INTEGER_COLOR_MAP = new LinkedHashMap<>(32);
    public static final Map<String, Class<?>> STRING_CLASS_MAP = new HashMap<>(32);
    public static final Map<Integer, String> STRING_ID_MAP = new HashMap<>(32);

    public static void addSpawnEgg(int id, Class<?> clazz, String s, int color1, int color2) {
        INTEGER_CLASS_MAP.put(id, clazz);
        INTEGER_STRING_MAP.put(id, s);
        INTEGER_COLOR_MAP.put(id, new EGGSpots(color1, color2, id));
        STRING_CLASS_MAP.put(s, clazz);
        STRING_ID_MAP.put(id, s);
    }

    public ItemPainSpawnEgg() {
        super();
        this.setUnlocalizedName("monsterPlacer");
        this.setTextureName("spawn_egg");
        this.setCreativeTab(TabsRegistry.SPAWN_EGG_TAB);
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        String s = (StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String s1 = INTEGER_STRING_MAP.get(p_77653_1_.getItemDamage());

        if (s1 != null) {
            s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
        }

        return s;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_) {
        EGGSpots entityegginfo = INTEGER_COLOR_MAP.get(p_82790_1_.getItemDamage());
        return entityegginfo != null ? (p_82790_2_ == 0 ? entityegginfo.spot1 : entityegginfo.spot2) : 16777215;
    }

    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_,
                             int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (!p_77648_3_.isRemote) {
            Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
            p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
            p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
            p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
            double d0 = 0.0D;

            if (p_77648_7_ == 1 && block.getRenderType() == 11) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(
                    p_77648_3_,
                    p_77648_1_.getItemDamage(),
                    (double) p_77648_4_ + 0.5D,
                    (double) p_77648_5_ + d0,
                    (double) p_77648_6_ + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && p_77648_1_.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(p_77648_1_.getDisplayName());
                }

                if (!p_77648_2_.capabilities.isCreativeMode) {
                    --p_77648_1_.stackSize;
                }
            }

        }
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
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

                    if (worldIn.getBlock(i, j, k) instanceof BlockLiquid) {
                        Entity entity = spawnCreature(worldIn, itemStackIn.getItemDamage(), i, j, k);

                        if (entity != null) {
                            if (entity instanceof EntityLivingBase && itemStackIn.hasDisplayName()) {
                                ((EntityLiving) entity).setCustomNameTag(itemStackIn.getDisplayName());
                            }

                            if (!player.capabilities.isCreativeMode) {
                                --itemStackIn.stackSize;
                            }
                        }
                    }
                }

            }
            return itemStackIn;
        }
    }

    /**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    public static Entity spawnCreature(World p_77840_0_, int p_77840_1_, double p_77840_2_, double p_77840_4_,
                                       double p_77840_6_) {
        if (!INTEGER_COLOR_MAP.containsKey(p_77840_1_)) {
            return null;
        } else {
            EntityLiving entityliving = null;

            for (int j = 0; j < 1; ++j) {
                entityliving = createEntityByID(p_77840_1_, p_77840_0_);

                if (entityliving != null) {
                    entityliving.setLocationAndAngles(
                            p_77840_2_,
                            p_77840_4_,
                            p_77840_6_,
                            MathHelper.wrapAngleTo180_float(p_77840_0_.rand.nextFloat() * 360.0F),
                            0.0F);
                    entityliving.rotationYawHead = entityliving.rotationYaw;
                    entityliving.renderYawOffset = entityliving.rotationYaw;
                    entityliving.onSpawnWithEgg(null);
                    p_77840_0_.spawnEntityInWorld(entityliving);
                    entityliving.playLivingSound();
                }
            }

            return entityliving;
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> p_150895_3_) {

        for (EGGSpots entityegginfo : INTEGER_COLOR_MAP.values()) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, entityegginfo.id));
        }
    }

    public static StatBase createStat1(EGGSpots p_151182_0_) {
        String s = STRING_ID_MAP.get(p_151182_0_.id);
        return s == null ? null
                : (new StatBase(
                "stat.killEntity." + s,
                new ChatComponentTranslation(
                        "stat.entityKill",
                        new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) })))
                .registerStat();
    }

    public static StatBase createStat2(EGGSpots p_151176_0_) {
        String s = STRING_ID_MAP.get(p_151176_0_.id);
        return s == null ? null
                : (new StatBase(
                "stat.entityKilledBy." + s,
                new ChatComponentTranslation(
                        "stat.entityKilledBy",
                        new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) })))
                .registerStat();
    }

    public static class EGGSpots {

        public int spot1;
        public int spot2;
        public int id;
        public final StatBase statBase1;
        public final StatBase statBase2;

        public EGGSpots(int spot1, int spot2, int id) {
            this.spot1 = spot1;
            this.spot2 = spot2;
            this.id = id;
            statBase1 = createStat1(this);
            statBase2 = createStat2(this);
        }

    }

    public static EntityLiving createEntityByID(int id, World world) {
        try {

            final Class<?> oclass = INTEGER_CLASS_MAP.get(id);

            if (oclass != null) {
                final MethodType type = MethodType.methodType(void.class, World.class);
                final MethodHandle handle = MethodHandles.lookup()
                        .findConstructor(oclass, type);
                handle.asType(type);

                return (EntityLiving) handle.invoke(world);
            }
        } catch (Throwable ignored) {}

        return null;
    }

    public static Entity createEntityByName(String name, World world) {
        try {

            final Class<?> oclass = STRING_CLASS_MAP.get(name);

            final MethodType type = MethodType.methodType(void.class, World.class);
            final MethodHandle handle = MethodHandles.lookup()
                    .findConstructor(oclass, type);

            return (EntityLiving) handle.invoke(world);
        } catch (Throwable ignored) {}

        return null;
    }

}
