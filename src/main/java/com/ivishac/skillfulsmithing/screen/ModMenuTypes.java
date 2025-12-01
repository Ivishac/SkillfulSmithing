package com.ivishac.skillfulsmithing.screen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SkillfulSmithing.MOD_ID);

    public static final RegistryObject<MenuType<CrucibleMenu>> CRUCIBLE_MENU =
            MENUS.register("crucible_menu",
                    () -> IForgeMenuType.create(CrucibleMenu::new));

    public static final RegistryObject<MenuType<BrickKilnMenu>> KILN_MENU =
            MENUS.register("brick_kiln",
                    () -> IForgeMenuType.create(BrickKilnMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
