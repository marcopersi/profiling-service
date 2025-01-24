package org.actics.customer.profiling.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.actics.customer.profiling.model.enums.AssetClass;
import org.actics.customer.profiling.model.enums.ExperienceLevel;
import org.actics.customer.profiling.model.enums.KnowledgeLevel;

/**
 * Represents the investment experience of a customer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentExperience {
    private AssetClass assetClass;
    private KnowledgeLevel knowledgeLevel;
    private ExperienceLevel experienceLevel;
}
