package jerios.painmod.core.world.biome;

import jerios.painmod.core.entity.PainCreeper;
import jerios.painmod.core.entity.PainSkeleton;
import jerios.painmod.core.entity.PainSpider;
import jerios.painmod.core.entity.PainZombie;
import jerios.painmod.core.items.registry.ModItems;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;

public class PainColdTiga extends PainBiomeGen{


    public PainColdTiga(int id, int p2) {
        super(id);
        this.setColor(3233098);
        this.setBiomeName("Pain Cold Taiga");
        this.func_76733_a(5159473);
        this.setEnableSnow();
        this.setTemperatureRainfall(-0.5F, 0.4F);
        this.setHeight(height_MidPlains).func_150563_c(16777215);

        this.topBlock = ModItems.painGrass;
        this.fillerBlock = ModItems.painDirt;

        spawnableCreatureList.clear();
        spawnableMonsterList.clear();

        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainSpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainSkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainCreeper.class, 100, 4, 4));

        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
    }





}
