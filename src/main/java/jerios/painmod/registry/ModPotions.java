package jerios.painmod.registry;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import jerios.painmod.effects.potion.PotionEffectUnecessaryEating;
import jerios.painmod.effects.potion.PotionMalnurishment;
import net.minecraft.potion.Potion;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ModPotions {

    public static Potion gluttony;
    public static Potion malnurishment;

    public static void register() {
        increasePotionArray();
        gluttony  = new PotionEffectUnecessaryEating(100, 421);
        malnurishment = new PotionMalnurishment(101, 4221);
    }

    public static void increasePotionArray()  {
        if (Potion.potionTypes.length > 128) {
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

                Potion[] extendedPotionArray = new Potion[256];

                System.arraycopy(Potion.potionTypes, 0, extendedPotionArray,0,Potion.potionTypes.length);
                potions.set(null, extendedPotionArray);


            } catch (Exception ignored) {

            }

        }




        }


    }
