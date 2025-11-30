package com.ivishac.skillfulsmithing.worldgen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_CLAY_DISK_KEY = registerKey("fire_clay_disk");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        context.register(FIRE_CLAY_DISK_KEY, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(BlockStateProvider.simple(ModBlocks.FIRE_CLAY.get())),
                BlockPredicate.matchesBlocks(List.of(Blocks.DIRT)),
                UniformInt.of(1, 2), 1
        )));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SkillfulSmithing.MOD_ID, name));
    }
}
