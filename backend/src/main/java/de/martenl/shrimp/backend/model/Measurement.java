package de.martenl.shrimp.backend.model;

import java.time.Instant;

public class Measurement {

    private final String measuringUnitId;
    private final Instant timestamp;
    private final double salinity;
    private final double pH;
    private final double temperature;

    public Measurement() {
        this.measuringUnitId = "";
        this.timestamp = Instant.now();
        this.salinity = 0.0;
        this.pH = 0.0;
        this.temperature = 0.0;
    }

    public Measurement(final String measuringUnitId, final Instant timestamp, final double salinity, final double pH, double temperature) {
        this.measuringUnitId = measuringUnitId;
        this.timestamp = timestamp;
        this.salinity = salinity;
        this.pH = pH;
        this.temperature = temperature;
    }

    public double getSalinity() {
        return salinity;
    }

    public double getpH() {
        return pH;
    }

    public String getMeasuringUnitId() {
        return measuringUnitId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "measuringUnitId='" + measuringUnitId + '\'' +
                ", timestamp=" + timestamp +
                ", salinity=" + salinity +
                ", pH=" + pH +
                '}';
    }
}

