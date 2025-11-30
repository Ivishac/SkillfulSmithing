package com.ivishac.skillfulsmithing.item;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static final Tier FLINT = TierSortingRegistry.registerTier(
            new ForgeTier(1, 120, 1f, 1f, 4,
                    ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "flint"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier COPPER = TierSortingRegistry.registerTier(
            new ForgeTier(1, 200, 2f, 0.75f, 4,
                    ModTags.Blocks.NEEDS_COPPER_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "copper"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier TIN = TierSortingRegistry.registerTier(
            new ForgeTier(1, 50, 2f, 0.5f, 4,
                    ModTags.Blocks.NEEDS_TIN_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "tin"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier BRONZE = TierSortingRegistry.registerTier(
            new ForgeTier(1, 275, 2f, 1f, 4,
                    ModTags.Blocks.NEEDS_BRONZE_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "bronze"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier NICKEL = TierSortingRegistry.registerTier(
            new ForgeTier(1, 250, 2f, 0.8f, 4,
                    ModTags.Blocks.NEEDS_NICKEL_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "nickel"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier IRON = TierSortingRegistry.registerTier(
            new ForgeTier(1, 325, 2f, 1.3f, 4,
                    ModTags.Blocks.NEEDS_IRON_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "iron"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier STEEL = TierSortingRegistry.registerTier(
            new ForgeTier(1, 575, 2f, 1.5f, 6,
                    ModTags.Blocks.NEEDS_STEEL_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "steel"), List.of(Tiers.WOOD), List.of(Tiers.STONE));
}
