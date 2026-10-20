package jerios.painmod.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import jerios.painmod.block.BlockCorruptedDarknessBlock;
import jerios.painmod.block.BlockPainPortal;
import jerios.painmod.block.PainBlock;
import jerios.painmod.block.PainGrass;
import jerios.painmod.items.ItemPainPotion;
import jerios.painmod.items.ItemPainSpawnEgg;
import jerios.painmod.items.essence.ItemHarmfulEssence;
import jerios.painmod.items.weapons.ItemBowOfCursing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ModItemBlocks {

    public static Item spawnEgg;
    public static ItemPainPotion potionPain;
    public static Block painPortal;
    public static Item cursedBow;

    public static Item harmfulEssence;


    public static Block corruptedBlock;


    public static Block painDirt;

    public static void register() {
        spawnEgg = new ItemPainSpawnEgg().setCreativeTab(TabsRegistry.SPAWN_EGG_TAB);
        registerItem(spawnEgg, "spawn_egg");

        potionPain = (ItemPainPotion) new ItemPainPotion().setCreativeTab(TabsRegistry.SPAWN_EGG_TAB).setUnlocalizedName("potion").setTextureName("potion");
        registerItem(potionPain, "pain_potion");


        cursedBow = new ItemBowOfCursing();
        registerItem(cursedBow, "CursedBow");



        harmfulEssence = new ItemHarmfulEssence();
        registerItem(harmfulEssence, "HarmfulEssence");


        painPortal = new BlockPainPortal();
        registerBlock(painPortal, "PainPortal");

        corruptedBlock = new BlockCorruptedDarknessBlock();
        registerBlock(corruptedBlock, "CorruptedDarknessBlock");

        painDirt = new PainGrass("painDirt");
        registerBlock(painDirt, "Pain Dirt");


    }



    public static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

    public static void registerBlock(Block block, String name){
        GameRegistry.registerBlock(block, name);
    }


}
