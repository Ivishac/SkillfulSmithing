package com.ivishac.skillfulsmithing;

import com.ivishac.skillfulsmithing.util.ISmithingSkill;
import com.ivishac.skillfulsmithing.util.SmithingSkillCapability;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkillfulSmithing.MOD_ID)
public class SmithingCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("smithing")
                        .requires(src -> src.hasPermission(0)) // everyone can use
                        .executes(SmithingCommands::showSmithingInfo)
        );
    }

    private static int showSmithingInfo(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();

        if (!(source.getEntity() instanceof Player player)) {
            source.sendFailure(Component.literal("This command can only be used by a player."));
            return Command.SINGLE_SUCCESS;
        }

        LazyOptional<ISmithingSkill> cap =
                player.getCapability(SmithingSkillCapability.SMITHING_SKILL);

        ISmithingSkill skill = cap.orElse(null);
        if (skill == null) {
            player.sendSystemMessage(Component.literal("Smithing skill data not available."));
            return Command.SINGLE_SUCCESS;
        }

        int level = skill.getLevel();
        float xp = skill.getXp();
        float xpNext = skill.getXpForNextLevel();

        player.sendSystemMessage(Component.literal(
                "Smithing Level: " + level +
                        "  |  XP: " + String.format("%.1f", xp) +
                        " / " + String.format("%.1f", xpNext)
        ));

        return Command.SINGLE_SUCCESS;
    }
}