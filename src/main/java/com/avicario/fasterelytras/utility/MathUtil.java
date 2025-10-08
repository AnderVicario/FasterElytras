package com.avicario.fasterelytras.utility;

public class MathUtil {
    /**
     * Calcula un valor lineal interpolado entre dos puntos
     */
    public static double getLinealValue(double x1, double y1, double x2, double y2, double x) {
        if (x1 == x2) return y1;
        return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
    }
}