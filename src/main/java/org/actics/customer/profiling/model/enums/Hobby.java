package org.actics.customer.profiling.model.enums;

/**
 * Enum representing various hobbies.
 */
public enum Hobby {
    LESEN,
    SPORT,
    MUSIK,
    REISEN,
    KOCHEN,
    WANDERN,
    FOTOGRAFIE,
    GARTENARBEIT,
    MALEN,
    SCHREIBEN,
    SCHWIMMEN,
    KLETTERN;

    /**
     * Converts a string to a `Hobby` enum, case-insensitively.
     *
     * @param value The string to convert.
     * @return The matching `Hobby` enum.
     * @throws IllegalArgumentException If no match is found.
     */
    public static Hobby fromString(String value) {
        for (Hobby hobby : Hobby.values()) {
            if (hobby.name().equalsIgnoreCase(value)) {
                return hobby;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    /**
     * Returns the name of the hobby in a readable format.
     *
     * @return The name of the hobby.
     */
    @Override
    public String toString() {
        return name();
    }
}
