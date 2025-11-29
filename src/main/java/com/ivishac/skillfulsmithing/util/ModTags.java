package com.ivishac.skillfulsmithing.util;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> NEEDS_FLINT_TOOL = tag("needs_flint_tool");

        public static final TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(SkillfulSmithing.MOD_ID, name));
        }
    }

    public static class Items {



        public static final TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(SkillfulSmithing.MOD_ID, name));
        }
    }
}
