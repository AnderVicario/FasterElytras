package com.avicario.fasterelytras.client;

import com.avicario.fasterelytras.config.FlightConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::createConfigScreen;
    }

    public static Screen createConfigScreen(Screen parent) {
        FlightConfig config = FlightConfig.getOrCreateInstance();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.fasterelytras.config"))
                .setSavingRunnable(config::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory settings = builder.getOrCreateCategory(Text.translatable("category.fasterelytras.settings"));

        // Altitude Determines Speed
        settings.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.fasterelytras.altitude_determines_speed"), config.isAltitudeDeterminesSpeed())
                .setDefaultValue(true)
                .setTooltip(Text.translatable("tooltip.fasterelytras.altitude_determines_speed"))
                .setSaveConsumer(config::setAltitudeDeterminesSpeed)
                .build());

        // Min Speed
        settings.addEntry(entryBuilder.startDoubleField(Text.translatable("option.fasterelytras.min_speed"), config.getMinSpeed())
                .setDefaultValue(36.0)
                .setMin(0.0)
                .setMax(100.0)
                .setTooltip(Text.translatable("tooltip.fasterelytras.min_speed"))
                .setSaveConsumer(config::setMinSpeed)
                .build());

        // Max Speed
        settings.addEntry(entryBuilder.startDoubleField(Text.translatable("option.fasterelytras.max_speed"), config.getMaxSpeed())
                .setDefaultValue(39.0)
                .setMin(0.0)
                .setMax(100.0)
                .setTooltip(Text.translatable("tooltip.fasterelytras.max_speed"))
                .setSaveConsumer(config::setMaxSpeed)
                .build());

        // Min Height
        settings.addEntry(entryBuilder.startDoubleField(Text.translatable("option.fasterelytras.min_height"), config.getMinHeight())
                .setDefaultValue(0.0)
                .setMin(-64.0)
                .setMax(320.0)
                .setTooltip(Text.translatable("tooltip.fasterelytras.min_height"))
                .setSaveConsumer(config::setMinHeight)
                .build());

        // Max Height
        settings.addEntry(entryBuilder.startDoubleField(Text.translatable("option.fasterelytras.max_height"), config.getMaxHeight())
                .setDefaultValue(256.0)
                .setMin(-64.0)
                .setMax(320.0)
                .setTooltip(Text.translatable("tooltip.fasterelytras.max_height"))
                .setSaveConsumer(config::setMaxHeight)
                .build());

        return builder.build();
    }
}