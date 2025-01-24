package org.actics.customer.profiling.model.enums;

/**
 * Enum representing different levels of experience.
 */
public enum ExperienceLevel {
    KEINE("Keine Erfahrung"),
    WENIG("Wenig Erfahrung"),
    MITTEL("Mittlere Erfahrung"),
    VIEL("Viel Erfahrung");

    private final String description;

    /**
     * Constructor for ExperienceLevel enum.
     *
     * @param description The descriptive text for the experience level.
     */
    ExperienceLevel(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the experience level.
     *
     * @return The descriptive text.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string to an ExperienceLevel enum, case-insensitively.
     *
     * @param value The string to convert.
     * @return The matching ExperienceLevel.
     * @throws IllegalArgumentException If no match is found.
     */
    public static ExperienceLevel fromString(String value) {
        for (ExperienceLevel level : ExperienceLevel.values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    /**
     * Returns the name of the enum constant.
     *
     * @return The name of the enum constant.
     */
    @Override
    public String toString() {
        return description;
    }
}
