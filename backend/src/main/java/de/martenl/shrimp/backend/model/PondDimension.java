package de.martenl.shrimp.backend.model;

import de.martenl.shrimp.backend.services.PondService;

public class PondDimension {

    private final double width;
    private final double length;
    private final double depth;
    private final PondShape shape;
    private final PondSlope slope;

    public PondDimension(double width, double length, double depth, PondShape shape, PondSlope slope) {
        this.width = width;
        this.length = length;
        this.depth = depth;
        this.shape = shape;
        this.slope = slope;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getDepth() {
        return depth;
    }

    public PondShape getShape() {
        return shape;
    }

    public PondSlope getSlope() {
        return slope;
    }

    @Override
    public String toString() {
        return "PondDimension{" +
                "width=" + width +
                ", length=" + length +
                ", depth=" + depth +
                ", shape=" + shape +
                ", slope=" + slope +
                '}';
    }
}
