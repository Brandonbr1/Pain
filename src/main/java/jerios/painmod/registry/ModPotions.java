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
        addToMap(PainConfig.glutID, "Gluttony", 14270531, 9000, 4);
        malnourishment = new MalnourishmentEffect(PainConfig.malnourishment, 14270531).setPotionName("painmod.potion.malnourishment");
        addToMap(PainConfig.malnourishment, "Malnourismnet", 7834, 9000, 4);
        deadlyPoison = new DeadlyPoision(PainConfig.deadlyPoison, 14270531).setPotionName("painmod.potion.deadlyPosion");
        plaugeEffect = new PlaugeEffect(PainConfig.plaugeEffect, 14270531).setPotionName("painmod.potion.plauge");
        rageEffect = new RageEffect(PainConfig.rageEffect, 14270531).setPotionName("painmod.potion.wrath");
        sloth = new SlothEffect(PainConfig.slothEffect, 14270531).setPotionName("painmod.potion.sloth");
    }

    private static void addToMap(int id, String name, int color, int dur, int potency) {
        ItemPainPotion.integerPotionsPropsMap.put(id, new ItemPainPotion.PotionsProps(name, color,dur,potency,id));
    }


    public static void increasePotionArray()  {
        if (Potion.potionTypes.length > 32) {
            return;
        }

        String[] potionNames = new String[]{
                "potionTypes", "field_76425_a"
        };

        for (int i = 0; i < potionNames.length; i++) {
            try {
                Field potions = Potion.class.getDeclaredField(potionNames[i]);
                potions.setAccessible(true);

                if(Modifier.isFinal(potions.getModifiers())){
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(potions, potions.getModifiers() & ~Modifier.FINAL);
                }

                Potion[] extendedPotionArray = new Potion[128];

                System.arraycopy(Potion.potionTypes, 0, extendedPotionArray,0,Potion.potionTypes.length);
                potions.set(null, extendedPotionArray);


            } catch (Exception ignored) {

            }

        }




        }


    }
