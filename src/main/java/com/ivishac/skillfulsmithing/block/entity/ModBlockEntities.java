package com.ivishac.skillfulsmithing.block.entity;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SkillfulSmithing.MOD_ID);

    public static final RegistryObject<BlockEntityType<MoldTableBlockEntity>> MOLD_TABLE_BE =
            BLOCK_ENTITIES.register("mold_table_be",
                    () -> BlockEntityType.Builder.of(MoldTableBlockEntity::new,
                            ModBlocks.MOLD_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
