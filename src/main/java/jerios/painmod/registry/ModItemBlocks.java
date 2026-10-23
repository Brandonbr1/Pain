package jerios.painmod.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import jerios.painmod.block.BlockCorruptedDarknessBlock;
import jerios.painmod.block.BlockPainPortal;
import jerios.painmod.block.PainBlock;
import jerios.painmod.block.PainGrass;
import jerios.painmod.block.spawner.BlockPainSpawner;
import jerios.painmod.items.ItemPainPotion;
import jerios.painmod.items.ItemPainSpawnEgg;
import jerios.painmod.items.essence.ItemHarmfulEssence;
import jerios.painmod.items.spawner.ItemMonsterSpawner;
import jerios.painmod.items.weapons.ItemBowOfCursing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;

public class ModItemBlocks {

    public static Item spawnEgg;
    public static ItemPainPotion potionPain;
    public static Block painPortal;
    public static ItemBow cursedBow;

    public static Item harmfulEssence;


    public static Block corruptedBlock;

    public static Block painGrass;

    public static Block painDirt;

    public static Block painLogs;

    public static Block spawner;

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

        painGrass = new PainGrass("painGrass");
        registerBlock(painGrass, "PainGrass");

        painDirt = new PainBlock(Material.ground, "painDirt");
        registerBlock(painDirt, "PainDirt");

        spawner = new BlockPainSpawner();

        GameRegistry.registerBlock(spawner, ItemMonsterSpawner.class, "PainMonsterSpawner");

    }



    public static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

    public static void registerBlock(Block block, String name){
        GameRegistry.registerBlock(block, name);
    }


}
