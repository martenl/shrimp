package de.martenl.shrimp.trafficgenerator.domain;

import java.time.Instant;
import java.time.ZonedDateTime;

public class Measurement {

    private final String measuringUnitId;
    private final Instant timestamp;
    private final double salinity;
    private final double pH;

    public Measurement() {
        this.measuringUnitId = "";
        this.timestamp = Instant.ofEpochMilli(0);
        this.salinity = 0.0;
        this.pH = 0.0;
    }

    public Measurement(final String measuringUnitId, final Instant timestamp, final double salinity, final double pH) {
        this.measuringUnitId = measuringUnitId;
        this.timestamp = timestamp;
        this.salinity = salinity;
        this.pH = pH;
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
