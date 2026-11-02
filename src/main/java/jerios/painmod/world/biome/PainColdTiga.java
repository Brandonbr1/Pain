package jerios.painmod.world.biome;

import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenTaiga;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class PainColdTiga extends PainBiomeGen{


    public PainColdTiga(int id, int p2) {
        super(id);
        this.setColor(3233098);
        this.setBiomeName("Pain Cold Taiga");
        this.func_76733_a(5159473);
        this.setEnableSnow();
        this.setTemperatureRainfall(-0.5F, 0.4F);
        this.setHeight(height_MidPlains).func_150563_c(16777215);

        this.topBlock = ModItemBlocks.painGrass;
        this.fillerBlock = ModItemBlocks.painDirt;

        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
    }





}
