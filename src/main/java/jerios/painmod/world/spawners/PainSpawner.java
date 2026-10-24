package jerios.painmod.world.spawners;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;
import java.util.Random;

public class PainSpawner  {

    // same as SpawnerAnimals but for Hostile Mobs as well

    public static void performWorldGenSpawning(World world, BiomeGenBase biome, int centerX, int centerZ, int diameterX, int diameterZ, Random random)
    {
        List<BiomeGenBase.SpawnListEntry> list = biome.getSpawnableList(EnumCreatureType.monster);

        if (!list.isEmpty())
        {
            float spawnChance = biome.getSpawningChance();
            while (random.nextFloat() < spawnChance)
            {
                BiomeGenBase.SpawnListEntry spawnlistentry = (BiomeGenBase.SpawnListEntry) WeightedRandom.getRandomItem(world.rand, list);
                IEntityLivingData ientitylivingdata = null;
                int i1 = spawnlistentry.minGroupCount + random.nextInt(1 + spawnlistentry.maxGroupCount - spawnlistentry.minGroupCount);
                int j1 = centerX + random.nextInt(diameterX);
                int k1 = centerZ + random.nextInt(diameterZ);
                int l1 = j1;
                int i2 = k1;

                for (int j2 = 0; j2 < i1; ++j2)
                {
                    boolean flag = false;

                    for (int k2 = 0; !flag && k2 < 4; ++k2)
                    {
                        int l2 = world.getTopSolidOrLiquidBlock(j1, k1);

                        if (SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, world, j1, l2, k1))
                        {
                            float f = (float)j1 + 0.5F;
                            float f1 = (float)l2;
                            float f2 = (float)k1 + 0.5F;
                            EntityLiving entityliving;

                            try
                            {
                                entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                continue;
                            }

                            entityliving.setLocationAndAngles((double)f, (double)f1, (double)f2, random.nextFloat() * 360.0F, 0.0F);
                            world.spawnEntityInWorld(entityliving);
                            ientitylivingdata = entityliving.onSpawnWithEgg(ientitylivingdata);
                            flag = true;
                        }

                        j1 += random.nextInt(5) - random.nextInt(5);

                        for (k1 += random.nextInt(5) - random.nextInt(5); j1 < centerX || j1 >= centerX + diameterX || k1 < centerZ || k1 >= centerZ + diameterX; k1 = i2 + random.nextInt(5) - random.nextInt(5))
                        {
                            j1 = l1 + random.nextInt(5) - random.nextInt(5);
                        }
                    }
                }
            }
        }
    }



}
