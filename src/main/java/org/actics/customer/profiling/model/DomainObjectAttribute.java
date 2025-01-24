package org.actics.customer.profiling.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents an attribute of a domain object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainObjectAttribute {

    @NotNull
    private UUID id;

    private DomainObject domainObject;

    @NotNull
    @Size(min = 1, max = 255)
    private String attributeName;

    @Size(max = 255)
    private String attributeValue;
}
