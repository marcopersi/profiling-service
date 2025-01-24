package org.actics.customer.profiling.model.enums;

/**
 * Enum representing different levels of knowledge.
 */
public enum KnowledgeLevel {
    KEINE("Keine Kenntnisse"),
    GRUNDKENTNISSE("Grundkenntnisse"),
    FORTGESCHRITTEN("Fortgeschritten"),
    EXPERTE("Experte");

    private final String description;

    /**
     * Constructor to associate a description with each enum value.
     *
     * @param description A user-friendly description of the knowledge level.
     */
    KnowledgeLevel(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the knowledge level.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string to a `KnowledgeLevel` enum, case-insensitively.
     *
     * @param value The string to convert.
     * @return The matching `KnowledgeLevel` enum.
     * @throws IllegalArgumentException If no match is found.
     */
    public static KnowledgeLevel fromString(String value) {
        for (KnowledgeLevel level : KnowledgeLevel.values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    /**
     * Returns the name of the knowledge level in a readable format.
     *
     * @return The name of the knowledge level.
     */
    @Override
    public String toString() {
        return description;
    }
}
