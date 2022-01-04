package de.martenl.shrimp.backend.model;

public class PondConfiguration {

    private final String name;
    private final double salinity;

    public PondConfiguration(String name, double salinity) {
        this.name = name;
        this.salinity = salinity;
    }

    public String getName() {
        return name;
    }

    public double getSalinity() {
        return salinity;
    }

    @Override
    public String toString() {
        return "PondConfiguration{" +
                "name='" + name + '\'' +
                ", salinity=" + salinity +
                '}';
    }
}
