package com.ivishac.skillfulsmithing.block;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.custom.BrickKiln;
import com.ivishac.skillfulsmithing.block.custom.MoldTable;
import com.ivishac.skillfulsmithing.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SkillfulSmithing.MOD_ID);

    public static final RegistryObject<Block> FIRE_CLAY = registerBlock("fire_clay",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> MOLD_TABLE = registerBlock("mold_table",
            () -> new MoldTable(BlockBehaviour.Properties
                    .of()
                    .strength(2.5F, 6.0F)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> BRICK_KILN = registerBlock("brick_kiln",
            () -> new BrickKiln(BlockBehaviour.Properties
                    .of()
                    .strength(2.5F, 6.0F)
                    .requiresCorrectToolForDrops()
    ));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
