package com.solarSystemModel;

import javax.swing.JFrame;
import com.solarSystemModel.model.SolarSystemModel;
import com.solarSystemModel.view.SolarSystemPanel;

/**
 * The main application class.
 * It sets up the JFrame, creates the solar system model,
 * and adds the simulation panel.
 */
public class SolarSystemApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("3D Solar System Simulation (SOLID Design)");
        SolarSystemModel model = new SolarSystemModel();
        SolarSystemPanel panel = new SolarSystemPanel(model);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window on screen.
        frame.setVisible(true);
    }
}
