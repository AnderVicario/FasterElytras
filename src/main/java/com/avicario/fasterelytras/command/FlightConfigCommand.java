package com.avicario.fasterelytras.command;

import com.avicario.fasterelytras.config.FlightConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class FlightConfigCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("fasterelytras")
                    .requires(source -> {
                        // 1. Permite la consola (útil para scripts)
                        if (!source.isPlayer()) {
                            return true; // O source.hasPermissionLevel(4) para restringir
                        }

                        // 2. Para jugadores, verificar si es operador nivel 2+
                        ServerPlayer player = source.getPlayer();
                        if (player == null) return false;

                        PlayerList pm = source.getServer().getPlayerList();
                        return pm.isOp(player.nameAndId());
                    }) // Solo ops pueden usar estos comandos
                    .then(literal("config")
                            .then(literal("show")
                                    .executes(FlightConfigCommand::showConfig))
                            .then(literal("set")
                                    .then(literal("altitudeDeterminesSpeed")
                                            .then(argument("value", BoolArgumentType.bool())
                                                    .executes(context -> setAltitudeDeterminesSpeed(context, BoolArgumentType.getBool(context, "value")))))
                                    .then(literal("minSpeed")
                                            .then(argument("value", DoubleArgumentType.doubleArg(0))
                                                    .executes(context -> setMinSpeed(context, DoubleArgumentType.getDouble(context, "value")))))
                                    .then(literal("maxSpeed")
                                            .then(argument("value", DoubleArgumentType.doubleArg(0))
                                                    .executes(context -> setMaxSpeed(context, DoubleArgumentType.getDouble(context, "value")))))
                                    .then(literal("minHeight")
                                            .then(argument("value", DoubleArgumentType.doubleArg(0))
                                                    .executes(context -> setMinHeight(context, DoubleArgumentType.getDouble(context, "value")))))
                                    .then(literal("maxHeight")
                                            .then(argument("value", DoubleArgumentType.doubleArg(0))
                                                    .executes(context -> setMaxHeight(context, DoubleArgumentType.getDouble(context, "value"))))))
                            .then(literal("reset")
                                    .executes(FlightConfigCommand::resetConfig))
                    ));
        });
    }

    private static int showConfig(CommandContext<CommandSourceStack> context) {
        FlightConfig config = FlightConfig.getOrCreateInstance();

        Component configText = Component.literal("")
                .append(Component.translatable("command.fasterelytras.config.current"))
                .append("\n")
                .append(Component.translatable("command.fasterelytras.config.altitude_determines_speed", config.isAltitudeDeterminesSpeed()))
                .append("\n")
                .append(Component.translatable("command.fasterelytras.config.min_speed", config.getMinSpeed()))
                .append("\n")
                .append(Component.translatable("command.fasterelytras.config.max_speed", config.getMaxSpeed()))
                .append("\n")
                .append(Component.translatable("command.fasterelytras.config.min_height", config.getMinHeight()))
                .append("\n")
                .append(Component.translatable("command.fasterelytras.config.max_height", config.getMaxHeight()));

        context.getSource().sendSuccess(() -> configText, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int setAltitudeDeterminesSpeed(CommandContext<CommandSourceStack> context, boolean value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setAltitudeDeterminesSpeed(value);
        context.getSource().sendSuccess(() ->
                        Component.translatable("command.fasterelytras.set.altitude_determines_speed", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMinSpeed(CommandContext<CommandSourceStack> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMinSpeed(value);
        context.getSource().sendSuccess(() ->
                        Component.translatable("command.fasterelytras.set.min_speed", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMaxSpeed(CommandContext<CommandSourceStack> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMaxSpeed(value);
        context.getSource().sendSuccess(() ->
                        Component.translatable("command.fasterelytras.set.max_speed", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMinHeight(CommandContext<CommandSourceStack> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMinHeight(value);
        context.getSource().sendSuccess(() ->
                        Component.translatable("command.fasterelytras.set.min_height", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMaxHeight(CommandContext<CommandSourceStack> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMaxHeight(value);
        context.getSource().sendSuccess(() ->
                        Component.translatable("command.fasterelytras.set.max_height", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int resetConfig(CommandContext<CommandSourceStack> context) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.resetToDefaults();
        context.getSource().sendSuccess(() ->
                        Component.translatable("command.fasterelytras.reset"),
                true
        );
        return Command.SINGLE_SUCCESS;
    }
}