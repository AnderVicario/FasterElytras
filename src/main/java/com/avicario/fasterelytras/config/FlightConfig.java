package com.avicario.fasterelytras.config;

public class FlightConfig {
    private static FlightConfig instance;

    // Valores por defecto
    private boolean altitudeDeterminesSpeed = true;
    private double minSpeed = 7.2;
    private double maxSpeed = 150.0;
    private double minHeight = 0.0;
    private double maxHeight = 256.0;

    public static FlightConfig getOrCreateInstance() {
        if (instance == null) {
            instance = new FlightConfig();
        }
        return instance;
    }

    // Getters y Setters
    public boolean isAltitudeDeterminesSpeed() { return altitudeDeterminesSpeed; }
    public void setAltitudeDeterminesSpeed(boolean altitudeDeterminesSpeed) { this.altitudeDeterminesSpeed = altitudeDeterminesSpeed; }

    public double getMinSpeed() { return minSpeed; }
    public void setMinSpeed(double minSpeed) { this.minSpeed = minSpeed; }

    public double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }

    public double getMinHeight() { return minHeight; }
    public void setMinHeight(double minHeight) { this.minHeight = minHeight; }

    public double getMaxHeight() { return maxHeight; }
    public void setMaxHeight(double maxHeight) { this.maxHeight = maxHeight; }
}