package com.ivishac.skillfulsmithing;

import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.block.entity.ModBlockEntities;
import com.ivishac.skillfulsmithing.item.ModCreativeModeTabs;
import com.ivishac.skillfulsmithing.item.ModItems;
import com.ivishac.skillfulsmithing.loot.ModLootModifiers;
import com.ivishac.skillfulsmithing.screen.BrickKilnScreen;
import com.ivishac.skillfulsmithing.screen.CrucibleScreen;
import com.ivishac.skillfulsmithing.screen.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Mod(SkillfulSmithing.MOD_ID)
public class SkillfulSmithing
{
    public static final String MOD_ID = "skillfulsmithing";

    private static final Logger LOGGER = LogUtils.getLogger();

    public SkillfulSmithing(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            List<Item> vanillaTools = Arrays.asList(
                    Items.WOODEN_SHOVEL, Items.WOODEN_PICKAXE, Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_SWORD,
                    Items.STONE_SHOVEL, Items.STONE_PICKAXE, Items.STONE_AXE, Items.STONE_HOE, Items.STONE_SWORD,
                    Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD,
                    Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD,
                    Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_SWORD,
                    Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE, Items.NETHERITE_SWORD
            );

            Field maxDamageField = null;
            try {
                // Try to find the field by name "maxDamage" (dev environment)
                maxDamageField = Item.class.getDeclaredField("maxDamage");
            } catch (NoSuchFieldException e1) {
                try {
                    // Try SRG name
                    maxDamageField = Item.class.getDeclaredField("f_41370_");
                } catch (NoSuchFieldException e2) {
                    LOGGER.error("Could not find maxDamage field", e2);
                }
            }

            if (maxDamageField != null) {
                maxDamageField.setAccessible(true);
                for (Item item : vanillaTools) {
                    try {
                        maxDamageField.setInt(item, 1);
                        LOGGER.info("Set max damage for " + item + " to 1");
                    } catch (IllegalAccessException e) {
                        LOGGER.error("Failed to set max damage for " + item, e);
                    }
                }
            }
        });

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            MenuScreens.register(ModMenuTypes.CRUCIBLE_MENU.get(), CrucibleScreen::new);
            MenuScreens.register(ModMenuTypes.KILN_MENU.get(), BrickKilnScreen::new);
        }
    }
}
