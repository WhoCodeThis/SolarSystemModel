package com.solarSystemModel.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the solar system data.
 * Contains a list of planets and the sun's properties.
 */
public class SolarSystemModel {
    private List<Planet> planets;
    private int sunSize = 50; // Diameter of the sun (world units)

    public SolarSystemModel() {
        planets = new ArrayList<>();
        // Initialize planets: (name, orbitRadius, orbitSpeed, initialAngle, size, color)
        planets.add(new Planet("Mercury", 80, 4.5,   0,   6, Color.LIGHT_GRAY));
        planets.add(new Planet("Venus",   120, 3.0,  45,   8, Color.YELLOW));
        planets.add(new Planet("Earth",   160, 2.5,  90,   8, Color.BLUE));
        planets.add(new Planet("Mars",    200, 2.0, 135,   6, Color.RED));
        planets.add(new Planet("Jupiter", 260, 1.5, 180,  12, Color.ORANGE));
        planets.add(new Planet("Saturn",  320, 1.3, 225,  10, new Color(222, 184, 135))); // Burlywood
        planets.add(new Planet("Uranus",  380, 1.0, 270,   9, Color.CYAN));
        planets.add(new Planet("Neptune", 440, 0.8, 315,   9, new Color(0, 0, 139)));      // Dark blue
    }

    /**
     * Returns the list of planets.
     */
    public List<Planet> getPlanets(){
        return planets;
    }

    /**
     * Returns the sun's size.
     */
    public int getSunSize(){
        return sunSize;
    }

    /**
     * Updates the state of all planets.
     */
    public void update() {
        for (Planet planet : planets) {
            planet.update();
        }
    }
}
