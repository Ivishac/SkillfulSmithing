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

    // WHEN ADDING A NEW TIER, MAKE SURE TO ADD IT IN THE ORDER OF TIER FROM BEST TO WORST. FOR EXAMPLE, COPPER MUST BE REGISTERED AFTER BRONZE.
    // IF IT IS BEFORE BRONZE, IT WILL NOT WORK.

    public static final Tier STEEL = TierSortingRegistry.registerTier(
            new ForgeTier(1, 575, 2f, 1.5f, 6,
                    ModTags.Blocks.NEEDS_STEEL_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "steel"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));

    public static final Tier IRON = TierSortingRegistry.registerTier(
            new ForgeTier(1, 325, 2f, 1.3f, 4,
                    ModTags.Blocks.NEEDS_IRON_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "iron"), List.of(Tiers.IRON), List.of(ModToolTiers.STEEL));

    public static final Tier NICKEL = TierSortingRegistry.registerTier(
            new ForgeTier(2, 221, 3f, 0.8f, 10,
                    ModTags.Blocks.NEEDS_NICKEL_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "nickel"), List.of(Tiers.GOLD), List.of(Tiers.IRON, ModToolTiers.IRON));

    public static final Tier BRONZE = TierSortingRegistry.registerTier(
            new ForgeTier(2, 275, 4f, 1f, 5,
                    ModTags.Blocks.NEEDS_BRONZE_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "bronze"), List.of(Tiers.STONE), List.of(ModToolTiers.NICKEL, Tiers.IRON));

    public static final Tier COPPER = TierSortingRegistry.registerTier(
            new ForgeTier(1, 180, 3f, 0.75f, 6,
                    ModTags.Blocks.NEEDS_COPPER_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "copper"), List.of(Tiers.STONE), List.of(ModToolTiers.BRONZE));

    public static final Tier TIN = TierSortingRegistry.registerTier(
            new ForgeTier(1, 60, 2f, 0.5f, 4,
                    ModTags.Blocks.NEEDS_TIN_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "tin"), List.of(Tiers.WOOD), List.of(Tiers.STONE));

    public static final Tier FLINT = TierSortingRegistry.registerTier(
            new ForgeTier(0, 99, 1.9f, 0f, 2,
                    ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "flint"), List.of(Tiers.WOOD), List.of(Tiers.STONE));
}
