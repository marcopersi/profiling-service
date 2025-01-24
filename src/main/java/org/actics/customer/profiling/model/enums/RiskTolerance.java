package org.actics.customer.profiling.model.enums;

/**
 * Enum representing different levels of risk tolerance.
 */
public enum RiskTolerance {
    NIEDRIG("Niedriges Risiko"),
    MITTEL("Mittleres Risiko"),
    HOCH("Hohes Risiko");

    private final String description;

    /**
     * Constructor to associate a description with each risk tolerance level.
     *
     * @param description A user-friendly description of the risk level.
     */
    RiskTolerance(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the risk tolerance level.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string to a `RiskTolerance` enum, case-insensitively.
     *
     * @param value The string to convert.
     * @return The matching `RiskTolerance` enum.
     * @throws IllegalArgumentException If no match is found.
     */
    public static RiskTolerance fromString(String value) {
        for (RiskTolerance riskTolerance : RiskTolerance.values()) {
            if (riskTolerance.name().equalsIgnoreCase(value)) {
                return riskTolerance;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    /**
     * Returns the description instead of the enum name.
     *
     * @return The description.
     */
    @Override
    public String toString() {
        return description;
    }
}
