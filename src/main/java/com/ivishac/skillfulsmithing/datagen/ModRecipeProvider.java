package com.ivishac.skillfulsmithing.datagen;

import com.ivishac.skillfulsmithing.block.ModBlocks;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FIRE_CLAY.get())
                .pattern("CC")
                .pattern("CC")
                .define('C', ModItems.FIRE_CLAY_BALL.get())
                .unlockedBy("has_fire_clay_ball", has(ModItems.FIRE_CLAY_BALL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SWORD_HEAD.get())
                .pattern("S")
                .pattern("S")
                .define('S', ModItems.FLINT_SHARD.get())
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICKAXE_HEAD.get())
                .pattern("FS")
                .pattern("S ")
                .define('F', Items.FLINT)
                .define('S', ModItems.FLINT_SHARD.get())
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL_HEAD.get())
                .pattern("S")
                .pattern("F")
                .define('F', Items.FLINT)
                .define('S', ModItems.FLINT_SHARD.get())
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE_HEAD.get())
                .pattern("SF")
                .pattern("S ")
                .define('F', Items.FLINT)
                .define('S', ModItems.FLINT_SHARD.get())
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_HOE_HEAD.get())
                .pattern("S ")
                .pattern(" F")
                .define('F', Items.FLINT)
                .define('S', ModItems.FLINT_SHARD.get())
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLINT_SWORD.get())
                .requires(ModItems.FLINT_SWORD_HEAD.get(), 1)
                .requires(ModItems.PLANT_FIBER.get(), 1)
                .requires(Items.STICK, 1)
                .unlockedBy(getHasName(ModItems.PLANT_FIBER.get()), has(Items.FLINT))
                .save(pWriter);

       ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLINT_PICKAXE.get())
                .requires(ModItems.FLINT_PICKAXE_HEAD.get(), 1)
                .requires(ModItems.PLANT_FIBER.get(), 1)
                .requires(Items.STICK, 1)
                .unlockedBy(getHasName(ModItems.PLANT_FIBER.get()), has(Items.FLINT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLINT_SHOVEL.get())
                .requires(ModItems.FLINT_SHOVEL_HEAD.get(), 1)
                .requires(ModItems.PLANT_FIBER.get(), 1)
                .requires(Items.STICK, 1)
                .unlockedBy(getHasName(ModItems.PLANT_FIBER.get()), has(Items.FLINT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLINT_AXE.get())
                .requires(ModItems.FLINT_AXE_HEAD.get(), 1)
                .requires(ModItems.PLANT_FIBER.get(), 1)
                .requires(Items.STICK, 1)
                .unlockedBy(getHasName(ModItems.PLANT_FIBER.get()), has(Items.FLINT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLINT_HOE.get())
                .requires(ModItems.FLINT_HOE_HEAD.get(), 1)
                .requires(ModItems.PLANT_FIBER.get(), 1)
                .requires(Items.STICK, 1)
                .unlockedBy(getHasName(ModItems.PLANT_FIBER.get()), has(Items.FLINT))
                .save(pWriter);


        oreSmelting(pWriter, List.of(ModItems.UNFIRED_CLAY_CRUCIBLE.get()), RecipeCategory.MISC, ModItems.FIRED_CLAY_CRUCIBLE.get(),
                0.25f, 200, "fired_clay_crucible");

        oreSmelting(pWriter, List.of(ModItems.UNFIRED_FIRE_CLAY_CRUCIBLE.get()), RecipeCategory.MISC, ModItems.FIRED_FIRE_CLAY_CRUCIBLE.get(),
                0.3f, 200, "fired_fire_clay_crucible");

    }

}
