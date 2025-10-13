package com.avicario.fasterelytras;

import com.avicario.fasterelytras.command.FlightConfigCommand;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FasterElytras implements ModInitializer {
    public static final String MOD_ID = "fasterelytras";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        FlightConfigCommand.register();
        LOGGER.info("Faster Elytras mod initialized!");
    }
}