package com.avicario.fasterelytras.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FlightConfig {
    private static FlightConfig instance;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("fasterelytras.json");

    // Valores por defecto
    private boolean altitudeDeterminesSpeed = true;
    private double minSpeed = 36.0;
    private double maxSpeed = 39.0;
    private double minHeight = 0.0;
    private double maxHeight = 256.0;

    public static FlightConfig getOrCreateInstance() {
        if (instance == null) {
            instance = loadConfig();
        }
        return instance;
    }

    private static FlightConfig loadConfig() {
        try {
            if (Files.exists(CONFIG_FILE)) {
                String json = Files.readString(CONFIG_FILE);
                return GSON.fromJson(json, FlightConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FlightConfig();
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            Files.writeString(CONFIG_FILE, GSON.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetToDefaults() {
        this.altitudeDeterminesSpeed = true;
        this.minSpeed = 36.0;
        this.maxSpeed = 39.0;
        this.minHeight = 0.0;
        this.maxHeight = 256.0;
        save();
    }

    // Getters y Setters (actualizados para guardar automáticamente)
    public boolean isAltitudeDeterminesSpeed() { return altitudeDeterminesSpeed; }
    public void setAltitudeDeterminesSpeed(boolean altitudeDeterminesSpeed) {
        this.altitudeDeterminesSpeed = altitudeDeterminesSpeed;
        save();
    }

    public double getMinSpeed() { return minSpeed; }
    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
        save();
    }

    public double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
        save();
    }

    public double getMinHeight() { return minHeight; }
    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
        save();
    }

    public double getMaxHeight() { return maxHeight; }
    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
        save();
    }
}