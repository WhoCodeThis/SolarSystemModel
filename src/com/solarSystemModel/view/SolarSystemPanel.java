package com.solarSystemModel.view;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import com.solarSystemModel.model.*;
import com.solarSystemModel.util.Projection;

/**
 * The panel responsible for rendering the solar system.
 * It updates the model and projects 3D positions onto the 2D view.
 */
public class SolarSystemPanel extends JPanel implements ActionListener {
    private Timer timer;
    private final double cameraDistance = 4000;         // Distance from camera to projection plane
    private final double tiltAngle = Math.toRadians(30); // Tilt angle of the orbital plane
    private SolarSystemModel solarSystemModel;

    public SolarSystemPanel(SolarSystemModel model) {
        this.solarSystemModel = model;
        setBackground(Color.BLACK);
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        solarSystemModel.update();
        repaint();
    }

    /**
     * Projects a 3D point into 2D screen space using a simple perspective projection.
     *
     * @param pt       The 3D point.
     * @param centerX  The screen center X coordinate.
     * @param centerY  The screen center Y coordinate.
     * @param planet   The associated planet (or null for the sun).
     * @return A Projection object with screen coordinates and scale.
     */
    private Projection projectPoint(Point3D pt, int centerX, int centerY, Planet planet) {
        double zWithCamera = pt.getZ() + cameraDistance;
        double factor = cameraDistance / zWithCamera;
        double sx = centerX + pt.getX() * factor;
        double sy = centerY - pt.getY() * factor;
        return new Projection(sx, sy, factor, zWithCamera, planet);
    }

    /**
     * Applies a tilt transformation to a 3D point.
     * The tilt is a rotation about the X-axis.
     *
     * @param pt The original 3D point.
     * @return The transformed 3D point.
     */
    private Point3D applyTilt(Point3D pt) {
        double x = pt.getX();
        double y = pt.getY();
        double z = pt.getZ();
        double newY = y * Math.cos(tiltAngle) - z * Math.sin(tiltAngle);
        double newZ = y * Math.sin(tiltAngle) + z * Math.cos(tiltAngle);
        return new Point3D(x, newY, newZ);
    }

    /**
     * Draws an orbit ring by sampling points along a circle and projecting them.
     *
     * @param g2d         The Graphics2D object.
     * @param orbitRadius The radius of the orbit.
     * @param centerX     The screen center X coordinate.
     * @param centerY     The screen center Y coordinate.
     */
    private void drawOrbitRing(Graphics2D g2d, double orbitRadius, int centerX, int centerY) {
        int numPoints = 100;
        int[] xPoints = new int[numPoints];
        int[] yPoints = new int[numPoints];

        for (int i = 0; i < numPoints; i++) {
            double theta = 2 * Math.PI * i / numPoints;
            double x = orbitRadius * Math.cos(theta);
            double z = orbitRadius * Math.sin(theta);
            double y = 0;
            Point3D pt = new Point3D(x, y, z);
            pt = applyTilt(pt);
            Projection proj = projectPoint(pt, centerX, centerY, null);
            xPoints[i] = (int) proj.getScreenX();
            yPoints[i] = (int) proj.getScreenY();
        }
        g2d.setColor(new Color(100, 100, 100, 100)); // Semi-transparent gray
        g2d.drawPolyline(xPoints, yPoints, numPoints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Use Graphics2D for better rendering quality.
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        // Draw orbit rings for each planet.
        for (Planet planet : solarSystemModel.getPlanets()) {
            drawOrbitRing(g2d, planet.getOrbitRadius(), centerX, centerY);
        }

        // Create a list of projections (for both the sun and the planets) for depth sorting.
        List<Projection> projections = new ArrayList<>();

        // Project the sun (assumed at the origin).
        Point3D sunPoint = new Point3D(0, 0, 0);
        Point3D tiltedSun = applyTilt(sunPoint);
        Projection sunProj = projectPoint(tiltedSun, centerX, centerY, null);
        projections.add(new Projection(sunProj.getScreenX(), sunProj.getScreenY(), sunProj.getScale(), sunProj.getDepth(), null));

        // Project each planet.
        for (Planet planet : solarSystemModel.getPlanets()) {
            Point3D worldPt = planet.getWorldCoordinates();
            Point3D tiltedPt = applyTilt(worldPt);
            Projection proj = projectPoint(tiltedPt, centerX, centerY, planet);
            projections.add(proj);
        }

        // Sort projections by depth (farther objects drawn first).
        projections.sort(Comparator.comparingDouble(Projection::getDepth).reversed());

        // Draw each projected object.
        for (Projection proj : projections) {
            if (proj.getPlanet() == null) {
                // Draw the sun.
                int sunSize = solarSystemModel.getSunSize();
                int drawSize = (int) (sunSize * proj.getScale());
                g2d.setColor(Color.YELLOW);
                g2d.fillOval((int) (proj.getScreenX() - drawSize / 2), (int) (proj.getScreenY() - drawSize / 2), drawSize, drawSize);
            } else {
                // Draw a planet.
                int drawSize = (int) (proj.getPlanet().getSize() * proj.getScale());
                g2d.setColor(proj.getPlanet().getColor());
                g2d.fillOval((int) (proj.getScreenX() - drawSize / 2), (int) (proj.getScreenY() - drawSize / 2), drawSize, drawSize);
            }
        }
    }
}
