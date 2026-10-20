package jerios.painmod.registry;

import jerios.painmod.config.PainConfig;
import jerios.painmod.effects.potion.DeadlyPoision;
import jerios.painmod.effects.potion.PlaugeEffect;
import jerios.painmod.effects.potion.UnnecessaryEatingEffect;
import jerios.painmod.effects.potion.MalnourishmentEffect;
import jerios.painmod.effects.potion.RageEffect;
import jerios.painmod.effects.potion.SlothEffect;
import jerios.painmod.items.ItemPainPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

public class ModPotions {

    public static Potion gluttony;
    public static Potion malnourishment;
    public static Potion deadlyPoison;
    public static Potion plaugeEffect;
    public static Potion rageEffect;
    public static Potion sloth;

    public static void register() {
        increasePotionArray();
        gluttony  = new UnnecessaryEatingEffect(PainConfig.glutID, 14270531).setPotionName("painmod.potion.superHunger");
        addPotion(PainConfig.glutID, new PotionEffect(PainConfig.glutID, 2400, 1));
        malnourishment = new MalnourishmentEffect(PainConfig.malnourishment, 14270531).setPotionName("painmod.potion.malnourishment");
        addPotion(PainConfig.malnourishment, new PotionEffect(PainConfig.malnourishment, 2400, 1));
        deadlyPoison = new DeadlyPoision(PainConfig.deadlyPoison, 14270531).setPotionName("painmod.potion.deadlyPosion");
        addPotion(PainConfig.deadlyPoison, new PotionEffect(PainConfig.deadlyPoison, 2400, 1));
        plaugeEffect = new PlaugeEffect(PainConfig.plaugeEffect, 14270531).setPotionName("painmod.potion.plauge");
        addPotion(PainConfig.plaugeEffect, new PotionEffect(PainConfig.plaugeEffect, 2400, 1));
        rageEffect = new RageEffect(PainConfig.rageEffect, 14270531).setPotionName("painmod.potion.wrath");
        addPotion(PainConfig.rageEffect, new PotionEffect(PainConfig.rageEffect, 2400, 1));
        sloth = new SlothEffect(PainConfig.slothEffect, 14270531).setPotionName("painmod.potion.sloth");
        addPotion(PainConfig.slothEffect, new PotionEffect(PainConfig.slothEffect, 2400, 1));
    }

    private static void addPotion(int id, PotionEffect effect) {
        ItemPainPotion.integerPotionsPropsMap.put(id, effect);
    }



    public static void increasePotionArray() {
        if (Potion.potionTypes.length > 32) {
            Potion.potionTypes = new Potion[256];
        }
      }

    }
