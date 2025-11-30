package com.ivishac.skillfulsmithing.worldgen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> FIRE_CLAY_PLACED_KEY = registerKey("fire_clay_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(FIRE_CLAY_PLACED_KEY, new PlacedFeature(
                configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.FIRE_CLAY_DISK_KEY),
                List.of(
                        CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        BiomeFilter.biome()
                )));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(SkillfulSmithing.MOD_ID, name));
    }

}
