package com.ivishac.skillfulsmithing.datagen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.block.custom.MoldTable;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SkillfulSmithing.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.FIRE_CLAY);

        moldTableBlock(ModBlocks.MOLD_TABLE);

        simpleBlockWithItem(ModBlocks.BRICK_KILN.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/brick_kiln"))); //fixed item texture
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void moldTableBlock(RegistryObject<Block> blockReg) {
        Block block = blockReg.get();
        String name = key(block).getPath(); // "mold_table"

        ModelFile emptyModel = new ModelFile.UncheckedModelFile(
                modLoc("block/mold_table_empty"));
        ModelFile filledModel = new ModelFile.UncheckedModelFile(
                modLoc("block/mold_table_filled"));

        // Blockstate variants based on the FILLED property
        getVariantBuilder(block)
                .partialState().with(MoldTable.FILLED, Boolean.FALSE)
                .modelForState()
                .modelFile(emptyModel)
                .addModel()

                .partialState().with(MoldTable.FILLED, Boolean.TRUE)
                .modelForState()
                .modelFile(filledModel)
                .addModel();

        // Item model -> use empty version
        itemModels().getBuilder(name)
                .parent(emptyModel);
    }

    private ResourceLocation key(Block block) {
        return block.builtInRegistryHolder().key().location();
    }
}
