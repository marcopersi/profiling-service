package org.actics.customer.profiling.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeAndExperience {
    private List<InvestmentExperience> experiences;
    private String tradingFrequency;
    private boolean professionalMarketExperience;
}
