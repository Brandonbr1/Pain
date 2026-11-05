package jerios.painmod.core.world.biome;

import jerios.painmod.core.entity.PainCreeper;
import jerios.painmod.core.entity.PainSkeleton;
import jerios.painmod.core.entity.PainSpider;
import jerios.painmod.core.entity.PainZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class PainBeach extends PainBiomeGen {
    public PainBeach(int id) {
        super(id);
        this.setColor(16440917);
        this.setBiomeName("Pain Beach");
        this.setTemperatureRainfall(0.8F, 0.4F);
        this.setHeight(height_Shores);
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;

        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainSpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainSkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainCreeper.class, 100, 4, 4));
    }
}
