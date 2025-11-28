package com.ivishac.skillfulsmithing.item;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRE_CLAY_BALL = ITEMS.register("fire_clay_ball",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNFIRED_FIRE_CLAY_CRUCIBLE = ITEMS.register("unfired_fire_clay_crucible",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRED_FIRE_CLAY_CRUCIBLE = ITEMS.register("fired_fire_clay_crucible",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
