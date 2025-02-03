package com.solarSystemModel.model;

import java.awt.Color;

/**
 * Represents a planet in the solar system.
 * Contains orbit parameters and visual properties.
 */
public class Planet {
    private String name;
    private double orbitRadius;   // Distance from the sun (world units)
    private double orbitSpeed;    // Degrees to move per update
    private double angle;         // Current orbital angle (in degrees)
    private int size;             // Diameter (world units)
    private Color color;

    public Planet(String name, double orbitRadius, double orbitSpeed, double initialAngle, int size, Color color) {
        this.name = name;
        this.orbitRadius = orbitRadius;
        this.orbitSpeed = orbitSpeed;
        this.angle = initialAngle;
        this.size = size;
        this.color = color;
    }

    /**
     * Updates the orbital angle of the planet.
     */
    public void update() {
        angle = (angle + orbitSpeed) % 360;
    }

    /**
     * Computes the 3D world coordinates of the planet.
     * The orbit is assumed to be in the X-Z plane (with Y = 0).
     *
     * @return a Point3D representing the planet's position.
     */
    public Point3D getWorldCoordinates() {
        double rad = Math.toRadians(angle);
        double x = orbitRadius * Math.cos(rad);
        double z = orbitRadius * Math.sin(rad);
        double y = 0;
        return new Point3D(x, y, z);
    }

    // Getters

    public String getName() {
        return name;
    }

    public double getOrbitRadius() {
        return orbitRadius;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}
