package org.actics.customer.profiling.model.enums;


public enum AssetClass {
    AKTIEN,
    OBLIGATIONEN,
    FONDS,
    DERIVATE,
    STRUKTURIERTE_PRODUKTE,
    ALTERNATIVE_ANLAGEN;

    // Optional: Override toString if specific formatting is needed
    @Override
    public String toString() {
        return name();
    }

    // Optional: Static method to get an enum from a string, with error handling
    public static AssetClass fromString(String value) {
        for (AssetClass assetClass : AssetClass.values()) {
            if (assetClass.name().equalsIgnoreCase(value)) {
                return assetClass;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
