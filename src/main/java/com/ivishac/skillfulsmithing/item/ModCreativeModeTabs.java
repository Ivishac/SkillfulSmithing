package com.ivishac.skillfulsmithing.item;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SkillfulSmithing.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SKILLFUL_SMITHING = CREATIVE_MODE_TABS.register("skillful_smithing",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FIRED_CLAY_CRUCIBLE.get()))
                    .title(Component.translatable("creativetab.skillful_smithing"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModBlocks.FIRE_CLAY.get());

                        pOutput.accept(ModItems.UNFIRED_CLAY_CRUCIBLE.get());
                        pOutput.accept(ModItems.FIRED_CLAY_CRUCIBLE.get());
                        pOutput.accept(ModItems.FIRE_CLAY_BALL.get());
                        pOutput.accept(ModItems.PLANT_FIBER.get());
                        pOutput.accept(ModItems.UNFIRED_FIRE_CLAY_CRUCIBLE.get());
                        pOutput.accept(ModItems.FIRED_FIRE_CLAY_CRUCIBLE.get());
                        pOutput.accept(ModItems.FLINT_SWORD.get());
                        pOutput.accept(ModItems.FLINT_PICKAXE.get());
                        pOutput.accept(ModItems.FLINT_AXE.get());
                        pOutput.accept(ModItems.FLINT_SHOVEL.get());
                        pOutput.accept(ModItems.FLINT_HOE.get());
                        pOutput.accept(ModItems.COPPER_HAMMER.get());
                        pOutput.accept(ModItems.TIN_HAMMER.get());
                        pOutput.accept(ModItems.BRONZE_HAMMER.get());
                        pOutput.accept(ModItems.NICKEL_HAMMER.get());
                        pOutput.accept(ModItems.IRON_HAMMER.get());
                        pOutput.accept(ModItems.STEEL_HAMMER.get());
                        pOutput.accept(ModItems.WOODEN_TONGS.get());
                        pOutput.accept(ModItems.COPPER_TONGS.get());
                        pOutput.accept(ModItems.TIN_TONGS.get());
                        pOutput.accept(ModItems.BRONZE_TONGS.get());
                        pOutput.accept(ModItems.NICKEL_TONGS.get());
                        pOutput.accept(ModItems.IRON_TONGS.get());
                        pOutput.accept(ModItems.STEEL_TONGS.get());

                    })
                    .build());

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}
