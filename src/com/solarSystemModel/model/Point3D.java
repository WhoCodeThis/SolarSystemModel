package com.solarSystemModel.model;

/**
 * A simple class to represent a 3D point.
 */
public class Point3D {
    private final double x;
    private final double y;
    private final double z;

    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Getters

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }
}
