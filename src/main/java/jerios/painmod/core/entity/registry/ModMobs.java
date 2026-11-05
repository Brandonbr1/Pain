package jerios.painmod.core.entity.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import jerios.painmod.forge.PainShare;
import jerios.painmod.forge.PainMod;
import jerios.painmod.core.entity.PainCreeper;
import jerios.painmod.core.entity.PainSkeleton;
import jerios.painmod.core.entity.PainSpider;
import jerios.painmod.core.entity.PainZombie;
import jerios.painmod.core.items.spawner.ItemPainSpawnEgg;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class ModMobs {


    public static void register() {
        registerEntityWithEgg(PainZombie.class, "painZombie", 128, 1,true, 5,5);
        registerEntityWithEgg(PainSkeleton.class, "PainSkeleton", 128, 1,true, 5,5);
        registerEntityWithEgg(PainCreeper.class, "PainCreeper", 128, 1,true, 5,5);
        registerEntityWithEgg(PainSpider.class, "PainSpider", 128, 1,true, 5,5);
    }

    static int mobID = 1;

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange,
                                      int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(
                entityClass,
                entityName,
                getModID(),
                PainMod.INSTANCE,
                trackingRange,
                updateFrequency,
                sendsVelocityUpdates);
    }

    public static void registerEntityWithEgg(Class<? extends Entity> entityClass, String entityName, int trackingRange,
                                             int updateFrequency, boolean sendsVelocityUpdates, int spot1, int spot2) {
        int mobId = getModID();
        EntityRegistry.registerModEntity(
                entityClass,
                entityName,
                mobId,
                PainMod.INSTANCE,
                trackingRange,
                updateFrequency,
                sendsVelocityUpdates);
        ItemPainSpawnEgg.addSpawnEgg(mobId, entityClass, PainShare.MOD_ID_DOT + entityName, spot1, spot2);

    }

    private static int getModID() {
        return mobID++;
    }

    private static void addBiomes(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
                                  EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
    }

}
