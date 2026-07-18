package jerios.painmod.registry;

import jerios.painmod.config.PainConfig;
import jerios.painmod.effects.potion.DeadlyPoision;
import jerios.painmod.effects.potion.PotionEffectUnnecessaryEating;
import jerios.painmod.effects.potion.PotionMalnourishment;
import net.minecraft.potion.Potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ModPotions {

    public static Potion gluttony;
    public static Potion malnourishment;
    public static Potion deadlyPoison;

    public static void register() {
        increasePotionArray();
        gluttony  = new PotionEffectUnnecessaryEating(PainConfig.glutID, 14270531).setPotionName("potion.superHunger");
        malnourishment = new PotionMalnourishment(PainConfig.malnourishment, 14270531).setPotionName("potion.malnourishment");
        deadlyPoison = new DeadlyPoision(PainConfig.deadlyPoison, 14270531).setPotionName("potion.deadlyPosion");
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
