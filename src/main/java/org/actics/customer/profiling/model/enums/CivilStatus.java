package org.actics.customer.profiling.model.enums;

/**
 * Enum representing the civil status of a customer.
 */
public enum CivilStatus {
    LEDIG,
    VERHEIRATET,
    GESCHIEDEN,
    VERWITWET,
    EINGETRAGENE_PARTNERSCHAFT,
    GERICHTLICH_AUFGELÖSTE_PARTNERSCHAFT,
    TOD_AUFGELÖSTE_PARTNERSCHAFT;

    /**
     * Optional: Static method to convert a string to a CivilStatus enum, with case-insensitive matching.
     *
     * @param value The string value to convert.
     * @return The matching CivilStatus.
     * @throws IllegalArgumentException If no match is found.
     */
    public static CivilStatus fromString(String value) {
        for (CivilStatus status : CivilStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    /**
     * Optional: Override toString to customize the string representation.
     *
     * @return The name of the enum in uppercase.
     */
    @Override
    public String toString() {
        return name();
    }
}
