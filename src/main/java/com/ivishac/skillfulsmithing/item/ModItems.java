package com.ivishac.skillfulsmithing.item;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.item.custom.CrucibleItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SkillfulSmithing.MOD_ID);

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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
