package de.martenl.shrimp.trafficgenerator.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Document
public class MeasurementPrototype {

    @Id
    String id;

    private String measuringUnitId;
    private boolean enabled;
    private double salinityMid;
    private double salinityVariance;
    private double pHMid;
    private double pHVariance;

    public MeasurementPrototype() {
        this.measuringUnitId = "";
        this.enabled = false;
        this.salinityMid = 0.0;
        this.salinityVariance = 0.0;
        this.pHMid = 5.0;
        this.pHVariance = 0.0;
    }

    public MeasurementPrototype(String measuringUnitId, boolean enabled, double salinityMid, double salinityVariance, double pHMid, double pHVariance) {
        this.measuringUnitId = measuringUnitId;
        this.enabled = enabled;
        this.salinityMid = salinityMid;
        this.salinityVariance = salinityVariance;
        this.pHMid = pHMid;
        this.pHVariance = pHVariance;
    }

    public String getId() {
        return measuringUnitId;
    }

    public String getMeasuringUnitId() {
        return measuringUnitId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getSalinityMid() {
        return salinityMid;
    }

    public double getSalinityVariance() {
        return salinityVariance;
    }

    public double getpHMid() {
        return pHMid;
    }

    public double getpHVariance() {
        return pHVariance;
    }

    public MeasurementPrototype changeEnabled() {
        return new MeasurementPrototype(measuringUnitId, !enabled, salinityMid, salinityVariance, pHMid, pHVariance);
    }

    public static class MeasurementPrototypeValidator implements Validator {
        @Override
        public boolean supports(Class<?> aClass) {
            return MeasurementPrototype.class.equals(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {

            MeasurementPrototype mp = (MeasurementPrototype) o;

            if (mp.getSalinityMid() < 0.0) {
                errors.rejectValue("salinityMid", "salinityMid.negativevalue");
            }
            if (mp.getSalinityMid() > 1.0) {
                errors.rejectValue("salinityMid", "salinityMid.toolarge");
            }
            if (mp.getSalinityVariance() < 0.0) {
                errors.rejectValue("salinityVariance", "salinityVariance.negativevalue");
            }
            if (mp.getpHMid() < 0.0) {
                errors.rejectValue("pHMid", "pHMid.negativevalue");
            }
            if (mp.getpHMid() > 14.0) {
                errors.rejectValue("pHMid", "pHMid.toolarge");
            }
            if (mp.getpHVariance() < 0.0) {
                errors.rejectValue("pHVariance", "pHVariance.negativevalue");
            }

        }
    }
}
