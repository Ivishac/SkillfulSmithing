package com.ivishac.skillfulsmithing.datagen;

import com.ivishac.skillfulsmithing.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNFIRED_CLAY_CRUCIBLE.get())
                .pattern("CSC")
                .pattern(" C ")
                .define('C', Items.CLAY_BALL)
                .define('S', Items.SAND)
                .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.STICK))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNFIRED_FIRE_CLAY_CRUCIBLE.get())
                .pattern("FSF")
                .pattern(" F ")
                .define('F', ModItems.FIRE_CLAY_BALL.get())
                .define('S', Items.SAND)
                .unlockedBy(getHasName(ModItems.FIRE_CLAY_BALL.get()), has(ModItems.FIRE_CLAY_BALL.get()))
                .save(pWriter);

        oreSmelting(pWriter, List.of(ModItems.UNFIRED_CLAY_CRUCIBLE.get()), RecipeCategory.MISC, ModItems.FIRED_CLAY_CRUCIBLE.get(),
                0.25f, 200, "fired_clay_crucible");

        oreSmelting(pWriter, List.of(ModItems.UNFIRED_FIRE_CLAY_CRUCIBLE.get()), RecipeCategory.MISC, ModItems.FIRED_FIRE_CLAY_CRUCIBLE.get(),
                0.3f, 200, "fired_fire_clay_crucible");

    }

    public static void removeVanillaToolRecipes(Consumer<FinishedRecipe> pWriter) {
        String[] toolsToRemove = {
                "wooden_pickaxe", "wooden_axe", "wooden_shovel", "wooden_hoe", "wooden_sword",
                "stone_pickaxe", "stone_axe", "stone_shovel", "stone_hoe", "stone_sword",
                "iron_pickaxe", "iron_axe", "iron_shovel", "iron_hoe", "iron_sword",
                "golden_pickaxe", "golden_axe", "golden_shovel", "golden_hoe", "golden_sword",
                "diamond_pickaxe", "diamond_axe", "diamond_shovel", "diamond_hoe", "diamond_sword",
                "netherite_pickaxe", "netherite_axe", "netherite_shovel", "netherite_hoe", "netherite_sword"
        };

        for (String tool : toolsToRemove) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.AIR)
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("   ")
                    .define(' ', Items.AIR)
                    .unlockedBy("has_air", has(Items.AIR))
                    .save(pWriter, "minecraft:" + tool);
        }
    }

}
