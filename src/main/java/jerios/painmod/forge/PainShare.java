package jerios.painmod.forge;

import jerios.painmod.Tags;
import jerios.painmod.vanilla.damageSource.RagePainSource;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PainShare {

    public static final Logger LOG = LogManager.getLogger(Tags.MOD_ID);


    public static final Random rand = new Random();

    public static final String MOD_ID_DOT = PainMod.MODID + ".";
    public static final String MODID_PREFIX = PainMod.MODID + ":";
    public static final String ENTITY_PREFIX = "textures/entity/";

    public static final String ENTITY_AGREESIVE = ENTITY_PREFIX + "agressive/";
    public static final String ENTITY_PEACEFUL = ENTITY_PREFIX + "passive/";

    public static final DamageSource RAGE = new RagePainSource();

    public static final List<Potion> tempBadPotionEffects = new ArrayList<>();
    public static Potion[] badPotionEffectsA;


}
