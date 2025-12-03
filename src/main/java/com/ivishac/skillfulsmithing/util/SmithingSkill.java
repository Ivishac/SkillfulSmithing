package com.ivishac.skillfulsmithing.util;

import net.minecraft.nbt.CompoundTag;

public class SmithingSkill implements ISmithingSkill {

    private static final String TAG_LEVEL = "Level";
    private static final String TAG_XP    = "Xp";

    private int level = 0;
    private float xp = 0f;

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public float getXp() {
        return xp;
    }

    @Override
    public void addXp(float amount) {
        if (amount <= 0) return;

        xp += amount;
        // Simple non-linear curve; tweak to your liking
        while (xp >= getXpForNextLevel()) {
            xp -= getXpForNextLevel();
            level++;
        }
    }

    @Override
    public float getXpForNextLevel() {
        // Example: base 10 XP, grows by 20% per level
        return 10f * (float)Math.pow(1.2, level);
    }

    @Override
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(TAG_LEVEL, level);
        tag.putFloat(TAG_XP, xp);
        return tag;
    }

    @Override
    public void loadNBT(CompoundTag tag) {
        this.level = tag.getInt(TAG_LEVEL);
        this.xp = tag.getFloat(TAG_XP);
    }
}