package com.ivishac.skillfulsmithing.util;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class SmithingSkillHelper {

    public static ISmithingSkill get(Player player) {
        LazyOptional<ISmithingSkill> cap =
                player.getCapability(SmithingSkillCapability.SMITHING_SKILL);
        return cap.orElseThrow(() -> new IllegalStateException("SmithingSkill not present on player"));
    }

    public static void addXp(Player player, float amount) {
        player.getCapability(SmithingSkillCapability.SMITHING_SKILL).ifPresent(skill -> {
            skill.addXp(amount);
        });
    }

    public static int getLevel(Player player) {
        return player.getCapability(SmithingSkillCapability.SMITHING_SKILL)
                .map(ISmithingSkill::getLevel)
                .orElse(0);
    }

    public static float getXp(Player player) {
        return player.getCapability(SmithingSkillCapability.SMITHING_SKILL)
                .map(ISmithingSkill::getXp)
                .orElse(0f);
    }
}