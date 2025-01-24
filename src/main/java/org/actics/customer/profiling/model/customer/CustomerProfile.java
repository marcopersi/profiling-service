package org.actics.customer.profiling.model.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.actics.customer.profiling.model.enums.RiskTolerance;

import java.util.UUID;

/**
 * DTO for creating or updating customer profiles.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {
    private UUID id;
    private UUID customerId;
    private EconomicCircumstances economicCircumstances;
    private KnowledgeAndExperience knowledgeAndExperience;
    private String investmentPurpose;
    private RiskTolerance riskTolerance;
    private String investmentObjectives; // Optional
}
