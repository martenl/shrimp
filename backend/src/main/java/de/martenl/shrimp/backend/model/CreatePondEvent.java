package de.martenl.shrimp.backend.model;

public class CreatePondEvent {
    final String name;

    public CreatePondEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
