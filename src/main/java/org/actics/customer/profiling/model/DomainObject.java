package org.actics.customer.profiling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Represents a domain object with its attributes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainObject {
    private UUID id;
    private String type; // Changed from "name" to "type" to match the database structure.
    private String name;
    private List<DomainObjectAttribute> attributes;
}
