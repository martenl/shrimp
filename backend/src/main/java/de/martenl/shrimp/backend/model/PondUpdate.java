package de.martenl.shrimp.backend.model;


public class PondUpdate {

    private final String name;
    private PondDimension updatedPondDimension;
    private PondConfiguration updatedPondConfiguration;

    public PondUpdate(String name,PondDimension updatedPondDimension, PondConfiguration updatedPondConfiguration) {
        this.name = name;
        this.updatedPondDimension = updatedPondDimension;
        this.updatedPondConfiguration = updatedPondConfiguration;
    }

    public PondUpdate() {
        this("", null, null);
    }

    @Override
    public String toString() {
        return "PondUpdate{" +
                "name=" + name +
                "updatedPondDimension=" + updatedPondDimension +
                ", updatedPondConfiguration=" + updatedPondConfiguration +
                '}';
    }

    public String getName() {
        return name;
    }

    public PondDimension getUpdatedPondDimension() {
        return updatedPondDimension;
    }

    public PondConfiguration getUpdatedPondConfiguration() {
        return updatedPondConfiguration;
    }
}
