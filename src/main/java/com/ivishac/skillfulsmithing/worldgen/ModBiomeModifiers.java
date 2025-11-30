package com.ivishac.skillfulsmithing.worldgen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_FIRE_CLAY = registerKey("add_fire_clay");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_FIRE_CLAY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_RIVER), // Spawn in Rivers (like vanilla clay)
                // You can also use BiomeTags.HAS_VILLAGE_PLAINS or create your own tag for swamps, etc.
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FIRE_CLAY_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES // Or LOCAL_MODIFICATIONS / VEGETAL_DECORATION depending on pref.
                // Vanilla clay is often UNDERGROUND_ORES or LOCAL_MODIFICATIONS
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(SkillfulSmithing.MOD_ID, name));
    }
}

