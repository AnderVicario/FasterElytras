package com.avicario.fasterelytras;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fasterelytras implements ModInitializer {
    public static final String MOD_ID = "fasterelytras";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Configuración - modifica estos valores para cambiar el comportamiento
    public static final double SPEED_MULTIPLIER = 2.0;
    public static final double MAX_SPEED = 5.0;
    public static final double BOOST_STRENGTH = 1.5;

    @Override
    public void onInitialize() {
        LOGGER.info("Faster Elytras mod initialized!");
        LOGGER.info("Speed multiplier: " + SPEED_MULTIPLIER);
        LOGGER.info("Max speed: " + MAX_SPEED);
    }
}