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
            new ForgeTier(1, 120, 4f, 1f, 4,
                    ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.EMPTY),
            new ResourceLocation(SkillfulSmithing.MOD_ID, "flint"), List.of(Tiers.WOOD), List.of(Tiers.STONE));
}
