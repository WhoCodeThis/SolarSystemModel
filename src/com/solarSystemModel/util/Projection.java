package com.solarSystemModel.util;

import com.solarSystemModel.model.Planet;

/**
 * Holds projection data for a 3D point projected onto the 2D screen.
 */
public class Projection {
    private final double screenX;
    private final double screenY;
    private final double scale;  // Scale factor from perspective projection
    private final double depth;  // Depth value used for painter’s sorting
    private final Planet planet; // Associated planet (null for the sun)

    public Projection(double screenX, double screenY, double scale, double depth, Planet planet) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.scale = scale;
        this.depth = depth;
        this.planet = planet;
    }

    // Getters
    public double getScreenX() {
        return screenX;
    }

    public double getScreenY() {
        return screenY;
    }

    public double getScale() {
        return scale;
    }

    public double getDepth() {
        return depth;
    }

    public Planet getPlanet() {
        return planet;
    }
}
