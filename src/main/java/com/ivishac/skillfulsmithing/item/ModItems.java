package com.ivishac.skillfulsmithing.item;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.item.custom.CrucibleItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SkillfulSmithing.MOD_ID);

    // BASIC MATERIALS

    public static final RegistryObject<Item> UNFIRED_CLAY_CRUCIBLE = ITEMS.register("unfired_clay_crucible",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRED_CLAY_CRUCIBLE = ITEMS.register("fired_clay_crucible",
            () -> new CrucibleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRE_CLAY_BALL = ITEMS.register("fire_clay_ball",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNFIRED_FIRE_CLAY_CRUCIBLE = ITEMS.register("unfired_fire_clay_crucible",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRED_FIRE_CLAY_CRUCIBLE = ITEMS.register("fired_fire_clay_crucible",
            () -> new CrucibleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber",
            () -> new Item(new Item.Properties()));

    // FLINT SET

    public static final RegistryObject<Item> FLINT_SWORD = ITEMS.register("flint_sword",
            () -> new SwordItem(ModToolTiers.FLINT, 3, 1.0f, new Item.Properties()));
    public static final RegistryObject<Item> FLINT_PICKAXE = ITEMS.register("flint_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FLINT, 1, -2.0f, new Item.Properties()));
    public static final RegistryObject<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new ShovelItem(ModToolTiers.FLINT, 1, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(ModToolTiers.FLINT, 5, 0.5f, new Item.Properties()));
    public static final RegistryObject<Item> FLINT_HOE = ITEMS.register("flint_hoe",
            () -> new HoeItem(ModToolTiers.FLINT, 1, -2.4f, new Item.Properties()));

    // HAMMERS & TONGS

    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new PickaxeItem(ModToolTiers.COPPER, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> TIN_HAMMER = ITEMS.register("tin_hammer",
            () -> new PickaxeItem(ModToolTiers.TIN, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_HAMMER = ITEMS.register("bronze_hammer",
            () -> new PickaxeItem(ModToolTiers.BRONZE, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> NICKEL_HAMMER = ITEMS.register("nickel_hammer",
            () -> new PickaxeItem(ModToolTiers.NICKEL, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new PickaxeItem(ModToolTiers.IRON, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_HAMMER = ITEMS.register("steel_hammer",
            () -> new PickaxeItem(ModToolTiers.STEEL, 2, 0.7f, new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_TONGS = ITEMS.register("wooden_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_TONGS = ITEMS.register("copper_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> TIN_TONGS = ITEMS.register("tin_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_TONGS = ITEMS.register("bronze_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> NICKEL_TONGS = ITEMS.register("nickel_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_TONGS = ITEMS.register("iron_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));
    public static final RegistryObject<Item> STEEL_TONGS = ITEMS.register("steel_tongs",
            () -> new PickaxeItem(ModToolTiers.FLINT, 2, 0.7f, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
