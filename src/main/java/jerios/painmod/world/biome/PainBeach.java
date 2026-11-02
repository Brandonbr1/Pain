package jerios.painmod.world.biome;

import net.minecraft.init.Blocks;

public class PainBeach extends PainBiomeGen {
    public PainBeach(int id) {
        super(id);
        this.setColor(16440917);
        this.setBiomeName("Pain Beach");
        this.setTemperatureRainfall(0.8F, 0.4F);
        this.setHeight(height_Shores);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;
    }
}
