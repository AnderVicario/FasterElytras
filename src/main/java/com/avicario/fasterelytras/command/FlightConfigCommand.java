package com.avicario.fasterelytras.command;

import com.avicario.fasterelytras.config.FlightConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class FlightConfigCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("fasterelytras")
                    .requires(source -> source.hasPermissionLevel(2)) // Solo ops pueden usar estos comandos
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

    private static int showConfig(CommandContext<ServerCommandSource> context) {
        FlightConfig config = FlightConfig.getOrCreateInstance();

        Text configText = Text.literal("")
                .append(Text.translatable("command.fasterelytras.config.current"))
                .append("\n")
                .append(Text.translatable("command.fasterelytras.config.altitude_determines_speed", config.isAltitudeDeterminesSpeed()))
                .append("\n")
                .append(Text.translatable("command.fasterelytras.config.min_speed", config.getMinSpeed()))
                .append("\n")
                .append(Text.translatable("command.fasterelytras.config.max_speed", config.getMaxSpeed()))
                .append("\n")
                .append(Text.translatable("command.fasterelytras.config.min_height", config.getMinHeight()))
                .append("\n")
                .append(Text.translatable("command.fasterelytras.config.max_height", config.getMaxHeight()));

        context.getSource().sendFeedback(() -> configText, true);
        return Command.SINGLE_SUCCESS;
    }

    private static int setAltitudeDeterminesSpeed(CommandContext<ServerCommandSource> context, boolean value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setAltitudeDeterminesSpeed(value);
        context.getSource().sendFeedback(() ->
                        Text.translatable("command.fasterelytras.set.altitude_determines_speed", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMinSpeed(CommandContext<ServerCommandSource> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMinSpeed(value);
        context.getSource().sendFeedback(() ->
                        Text.translatable("command.fasterelytras.set.min_speed", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMaxSpeed(CommandContext<ServerCommandSource> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMaxSpeed(value);
        context.getSource().sendFeedback(() ->
                        Text.translatable("command.fasterelytras.set.max_speed", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMinHeight(CommandContext<ServerCommandSource> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMinHeight(value);
        context.getSource().sendFeedback(() ->
                        Text.translatable("command.fasterelytras.set.min_height", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setMaxHeight(CommandContext<ServerCommandSource> context, double value) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.setMaxHeight(value);
        context.getSource().sendFeedback(() ->
                        Text.translatable("command.fasterelytras.set.max_height", value),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int resetConfig(CommandContext<ServerCommandSource> context) {
        FlightConfig config = FlightConfig.getOrCreateInstance();
        config.resetToDefaults();
        context.getSource().sendFeedback(() ->
                        Text.translatable("command.fasterelytras.reset"),
                true
        );
        return Command.SINGLE_SUCCESS;
    }
}