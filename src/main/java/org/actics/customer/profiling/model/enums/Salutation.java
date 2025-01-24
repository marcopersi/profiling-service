package org.actics.customer.profiling.model.enums;

/**
 * Enum representing different salutations.
 */
public enum Salutation {
    HERR("Herr"),
    FRAU("Frau"),
    DR("Doktor"),
    PROF("Professor");

    private final String description;

    /**
     * Constructor to associate a user-friendly description with each salutation.
     *
     * @param description A user-friendly description of the salutation.
     */
    Salutation(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the salutation.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string to a `Salutation` enum, case-insensitively.
     *
     * @param value The string to convert.
     * @return The matching `Salutation` enum.
     * @throws IllegalArgumentException If no match is found.
     */
    public static Salutation fromString(String value) {
        for (Salutation salutation : Salutation.values()) {
            if (salutation.name().equalsIgnoreCase(value)) {
                return salutation;
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
