package de.martenl.shrimp.backend.model;



public class PondInput {
    private final String name;
    private final String mode;

    public PondInput(String name, String mode) {
        this.name = name;
        this.mode = mode;
    }

    public PondInput() {
        this("", "");
    }

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }
}
