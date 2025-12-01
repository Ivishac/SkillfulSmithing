package com.ivishac.skillfulsmithing.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeatableItem extends Item {

    public static final String TAG_TEMPERATURE = "Temperature";

    public HeatableItem(Properties properties) {
        super(properties);
    }

    public static int getTemperature(ItemStack stack) {
        return stack.getOrCreateTag().getInt(TAG_TEMPERATURE);
    }

    public static void setTemperature(ItemStack stack, int temp) {
        stack.getOrCreateTag().putInt(TAG_TEMPERATURE, temp);
    }

    public static void addHeat(ItemStack stack, int delta) {
        int t = getTemperature(stack);
        setTemperature(stack, t + delta);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int temp = getTemperature(stack);
        tooltip.add(Component.literal("Temperature: " + temp + "°"));
    }
}
