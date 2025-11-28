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
        simpleItem(ModItems.UNFIRED_CLAY_CRUCIBLE);
        simpleItem(ModItems.FIRED_CLAY_CRUCIBLE);
        simpleItem(ModItems.FIRE_CLAY_BALL);
        simpleItem(ModItems.UNFIRED_FIRE_CLAY_CRUCIBLE);
        simpleItem(ModItems.FIRED_FIRE_CLAY_CRUCIBLE);

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SkillfulSmithing.MOD_ID, "item/" + item.getId().getPath()));
    }
}
