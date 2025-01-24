package org.actics.customer.profiling.model.enums;

/**
 * Enum representing the segmentation of customers.
 */
public enum CustomerSegmentation {
    PRIVATKUNDEN,
    PROFESSIONELLE_KUNDEN,
    INSTITUTIONELLE_KUNDEN,
    OPTING_OUT,
    OPTING_IN;

    /**
     * Converts a string to a CustomerSegmentation enum, case-insensitively.
     *
     * @param value The string value to convert.
     * @return The matching CustomerSegmentation.
     * @throws IllegalArgumentException If no match is found.
     */
    public static CustomerSegmentation fromString(String value) {
        for (CustomerSegmentation segmentation : CustomerSegmentation.values()) {
            if (segmentation.name().equalsIgnoreCase(value)) {
                return segmentation;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    /**
     * Returns the name of the enum constant.
     *
     * @return The enum name in uppercase.
     */
    @Override
    public String toString() {
        return name();
    }
}
