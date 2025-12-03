package com.ivishac.skillfulsmithing.util;

import net.minecraft.nbt.CompoundTag;

public interface ISmithingSkill {
    int getLevel();
    float getXp();

    /** Add raw XP and handle leveling up. */
    void addXp(float amount);

    /** How much XP is required for the NEXT level. */
    float getXpForNextLevel();

    CompoundTag saveNBT();
    void loadNBT(CompoundTag tag);
}