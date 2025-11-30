package com.ivishac.skillfulsmithing.datagen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SkillfulSmithing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // BASIC MATERIALS
        simpleItem(ModItems.UNFIRED_CLAY_CRUCIBLE);
        simpleItem(ModItems.FIRED_CLAY_CRUCIBLE);
        simpleItem(ModItems.FIRE_CLAY_BALL);
        simpleItem(ModItems.UNFIRED_FIRE_CLAY_CRUCIBLE);
        simpleItem(ModItems.FIRED_FIRE_CLAY_CRUCIBLE);
        simpleItem(ModItems.PLANT_FIBER);
        // FLINT SET
        handheldItem(ModItems.FLINT_SWORD);
        handheldItem(ModItems.FLINT_PICKAXE);
        handheldItem(ModItems.FLINT_AXE);
        handheldItem(ModItems.FLINT_SHOVEL);
        handheldItem(ModItems.FLINT_HOE);
        // HAMMERS & TONGS
        handheldItem(ModItems.COPPER_HAMMER);
        handheldItem(ModItems.TIN_HAMMER);
        handheldItem(ModItems.BRONZE_HAMMER);
        handheldItem(ModItems.NICKEL_HAMMER);
        handheldItem(ModItems.IRON_HAMMER);
        handheldItem(ModItems.STEEL_HAMMER);

        handheldItem(ModItems.WOODEN_TONGS);
        handheldItem(ModItems.COPPER_TONGS);
        handheldItem(ModItems.TIN_TONGS);
        handheldItem(ModItems.BRONZE_TONGS);
        handheldItem(ModItems.NICKEL_TONGS);
        handheldItem(ModItems.IRON_TONGS);
        handheldItem(ModItems.STEEL_TONGS);

    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(SkillfulSmithing.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SkillfulSmithing.MOD_ID, "item/" + item.getId().getPath()));
    }
}
