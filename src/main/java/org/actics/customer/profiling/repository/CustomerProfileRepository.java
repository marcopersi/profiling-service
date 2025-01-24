package org.actics.customer.profiling.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.actics.customer.profiling.jooq.tables.CustomerProfiles;
import org.actics.customer.profiling.jooq.tables.records.CustomerProfilesRecord;
import org.actics.customer.profiling.model.customer.*;
import org.actics.customer.profiling.model.enums.RiskTolerance;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerProfileRepository {

    private final DSLContext dsl;
    @Autowired
    private ObjectMapper objectMapper;

    public CustomerProfileRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<CustomerProfileResponse> findAll() {
        return dsl.selectFrom(CustomerProfiles.CUSTOMER_PROFILES)
                .fetch()
                .map(this::mapToDomain);
    }

    public Optional<CustomerProfileResponse> findById(UUID profileId) {
        CustomerProfilesRecord customerProfilesRecord = dsl.selectFrom(CustomerProfiles.CUSTOMER_PROFILES)
                .where(CustomerProfiles.CUSTOMER_PROFILES.ID.eq(profileId))
                .fetchOne();

        return Optional.ofNullable(customerProfilesRecord).map(this::mapToDomain);
    }

    public CustomerProfileResponse save(CustomerProfile customerProfile) {
        CustomerProfilesRecord customerProfilesRecord = dsl.newRecord(CustomerProfiles.CUSTOMER_PROFILES);

        try {

            customerProfilesRecord.setCustomerId(customerProfile.getCustomerId());

            customerProfilesRecord.setRiskTolerance(
                    customerProfile.getRiskTolerance() != null
                            ? customerProfile.getRiskTolerance().name()
                            : null
            );

            customerProfilesRecord.setEconomicCircumstances(
                    customerProfile.getEconomicCircumstances() != null
                            ? JSONB.valueOf(objectMapper.writeValueAsString(customerProfile.getEconomicCircumstances()))
                            : null
            );
            customerProfilesRecord.setKnowledgeAndExperience(
                    customerProfile.getKnowledgeAndExperience() != null
                            ? JSONB.valueOf(objectMapper.writeValueAsString(customerProfile.getKnowledgeAndExperience()))
                            : null
            );
            // Set InvestmentPurpose
            customerProfilesRecord.setInvestmentObjectives(customerProfile.getInvestmentObjectives());
            customerProfilesRecord.setInvestmentPurpose(customerProfile.getInvestmentPurpose());

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize JSON fields for CustomerProfile", e);
        }

        if (dsl.fetchExists(
                dsl.selectOne()
                        .from(CustomerProfiles.CUSTOMER_PROFILES)
                        .where(CustomerProfiles.CUSTOMER_PROFILES.CUSTOMER_ID.eq(customerProfilesRecord.getCustomerId()))
        )) {
            customerProfilesRecord.update();
        } else {
            customerProfilesRecord.insert();
        }

        return CustomerProfileResponse.builder()
                .id(customerProfilesRecord.getId())
                .customerId(customerProfilesRecord.getCustomerId())
                .riskTolerance(customerProfile.getRiskTolerance())
                .economicCircumstances(customerProfile.getEconomicCircumstances())
                .knowledgeAndExperience(customerProfile.getKnowledgeAndExperience())
                .investmentPurpose(customerProfile.getInvestmentPurpose())
                .investmentObjectives(customerProfile.getInvestmentObjectives()) // Optional
                .build();
    }

    public boolean existsById(UUID profileId) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(CustomerProfiles.CUSTOMER_PROFILES)
                        .where(CustomerProfiles.CUSTOMER_PROFILES.ID.eq(profileId))
        );
    }

    public void deleteById(UUID profileId) {
        dsl.deleteFrom(CustomerProfiles.CUSTOMER_PROFILES)
                .where(CustomerProfiles.CUSTOMER_PROFILES.ID.eq(profileId))
                .execute();
    }

    private CustomerProfileResponse mapToDomain(CustomerProfilesRecord customerProfilesRecord) {
        try {
            return CustomerProfileResponse.builder()
                    .economicCircumstances(customerProfilesRecord.getEconomicCircumstances() != null
                            ? objectMapper.readValue(customerProfilesRecord.getEconomicCircumstances().data(), EconomicCircumstances.class)
                            : null)
                    .knowledgeAndExperience(customerProfilesRecord.getKnowledgeAndExperience() != null
                            ? objectMapper.readValue(customerProfilesRecord.getKnowledgeAndExperience().data(), KnowledgeAndExperience.class)
                            : null)
                    .riskTolerance(customerProfilesRecord.getRiskTolerance() != null
                            ? RiskTolerance.valueOf(customerProfilesRecord.getRiskTolerance().toUpperCase())
                            : null)
                    .investmentPurpose(customerProfilesRecord.getInvestmentPurpose())
                    .investmentObjectives(customerProfilesRecord.getInvestmentObjectives())
                    .customerId(customerProfilesRecord.getCustomerId())
                    .id(customerProfilesRecord.getId())
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to map CustomerProfilesRecord to CustomerProfileResponse", e);
        }
    }

}
