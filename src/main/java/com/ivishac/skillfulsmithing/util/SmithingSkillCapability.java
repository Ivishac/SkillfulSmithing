package com.ivishac.skillfulsmithing.util;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.util.LazyOptional;

@Mod.EventBusSubscriber(modid = SkillfulSmithing.MOD_ID)
public class SmithingSkillCapability {

    public static final ResourceLocation ID =
            new ResourceLocation(SkillfulSmithing.MOD_ID, "smithing_skill");

    public static final Capability<ISmithingSkill> SMITHING_SKILL =
            CapabilityManager.get(new CapabilityToken<>() {});

    /** Provider that actually holds the SmithingSkill for one player. */
    public static class Provider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

        private final SmithingSkill backend = new SmithingSkill();
        private final LazyOptional<ISmithingSkill> optional = LazyOptional.of(() -> backend);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == SMITHING_SKILL ? optional.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return backend.saveNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            backend.loadNBT(nbt);
        }
    }

    /** Attach the capability to all players. */
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(ID, new Provider());
        }
    }
}