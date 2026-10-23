package jerios.painmod.world.biome;

import jerios.painmod.Share;
import net.minecraft.world.biome.BiomeGenBase;

public class PainBiomeManager extends BiomeGenBase {

    public static final BiomeGenBase[] painBiomes = new BiomeGenBase[256];
    public final int biomeID;


    public PainBiomeManager(BiomeGenBase biomeGenBase, int id, String name) {
        super(id, false);
        this.biomeID = id;
        painBiomes[id] = biomeGenBase;
        if (painBiomes[id] != null)
        {
            throw new RuntimeException("Biome Already registered!!! ID: " + id + " Biome Name: " + name );
        }
    }


    public static BiomeGenBase[] getBiomeGenArray()
    {
        return painBiomes;
    }

    public static BiomeGenBase getBiome(int id)
    {
        if (id >= 0 && id <= painBiomes.length)
        {
            return painBiomes[id];
        }
        else
        {
            Share.LOG.warn("Biome ID is out of bounds: " + id + ", defaulting to 0 (Ocean)");
            return ocean;
        }
    }

}
