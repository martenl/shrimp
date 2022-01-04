package de.martenl.shrimp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document
public class Pond {

    @Id
    String id;

    String name;
    PondConfiguration pondConfiguration;
    PondDimension pondDimension;
    String mode;
    Instant lastUpdate;
    List<Measurement> measurements;

    public Pond() {
        id = "";
        measurements = new ArrayList<>();
    }

    public Pond(String id, String name, String mode, Instant lastUpdate, PondConfiguration pondConfiguration, PondDimension pondDimension, List<Measurement> measurements) {
        this.id = id;
        this.name = name;
        this.mode = mode;
        this.lastUpdate = lastUpdate;
        this.pondConfiguration = pondConfiguration;
        this.pondDimension = pondDimension;
        this.measurements = measurements;
    }

    public Pond(String name, String mode) {
        this("", name, mode, Instant.now(), null, null, List.of());
    }

    public Pond addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        lastUpdate = measurement.getTimestamp();
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public PondConfiguration getPondConfiguration() {
        return pondConfiguration;
    }

    public PondDimension getPondDimension() {
        return pondDimension;
    }

    public Pond updatePondDimension(final PondDimension updatedPondDimension) {
        if (updatedPondDimension == null) return this;
        return new Pond(id, name, mode, lastUpdate, pondConfiguration, updatedPondDimension, measurements);
    }

    public Pond updatePondConfiguration(final PondConfiguration updatedPondConfiguration) {
        if (updatedPondConfiguration == null) return this;
        return new Pond(id, name, mode, lastUpdate, updatedPondConfiguration, pondDimension, measurements);
    }

    public interface PondData {
        String getId();

        String getName();

        String getMode();

        Instant getLastUpdate();
    }
}
