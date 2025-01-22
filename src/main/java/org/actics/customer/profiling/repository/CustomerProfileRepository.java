package org.actics.customer.profiling.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.actics.customer.profiling.jooq.tables.CustomerProfiles;
import org.actics.customer.profiling.jooq.tables.records.CustomerProfilesRecord;
import org.actics.customer.profiling.model.Customer;
import org.actics.customer.profiling.model.CustomerContactDetails;
import org.actics.customer.profiling.model.CustomerProfileEconomicCircumstances;
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

    public List<Customer> findAll() {
        return dsl.selectFrom(CustomerProfiles.CUSTOMER_PROFILES)
                .fetch()
                .map(this::mapToDomain);
    }

    public Optional<Customer> findById(UUID profileId) {
        CustomerProfilesRecord customerProfilesRecord = dsl.selectFrom(CustomerProfiles.CUSTOMER_PROFILES)
                .where(CustomerProfiles.CUSTOMER_PROFILES.ID.eq(profileId))
                .fetchOne();

        return Optional.ofNullable(customerProfilesRecord).map(this::mapToDomain);
    }

    public Customer save(Customer profile) {
        CustomerProfilesRecord customerProfilesRecord = dsl.newRecord(CustomerProfiles.CUSTOMER_PROFILES);
        customerProfilesRecord.setId(profile.getId());
        customerProfilesRecord.setFirstName(profile.getFirstName());
        customerProfilesRecord.setLastName(profile.getLastName());
        customerProfilesRecord.setBirthdate(profile.getBirthdate());

        try {
            customerProfilesRecord.setContactDetails(profile.getContactDetails() != null
                    ? JSONB.valueOf(objectMapper.writeValueAsString(profile.getContactDetails()))
                    : null);

            customerProfilesRecord.setEconomicCircumstances(profile.getEconomicCircumstances() != null
                    ? JSONB.valueOf(objectMapper.writeValueAsString(profile.getEconomicCircumstances()))
                    : null);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize JSON fields for Customer", e);
        }

        customerProfilesRecord.setRiskTolerance(profile.getRiskTolerance().getValue());
        customerProfilesRecord.setInvestmentExperience(profile.getInvestmentExperience());
        customerProfilesRecord.setInvestmentObjectives(profile.getInvestmentObjectives());

        if (dsl.fetchExists(
                dsl.selectOne()
                        .from(CustomerProfiles.CUSTOMER_PROFILES)
                        .where(CustomerProfiles.CUSTOMER_PROFILES.ID.eq(customerProfilesRecord.getId()))
        )) {
            customerProfilesRecord.update();
        } else {
            customerProfilesRecord.insert();
        }

        return mapToDomain(customerProfilesRecord);
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

    private Customer mapToDomain(CustomerProfilesRecord customerProfilesRecord) {
        try {
            return new Customer(
                    customerProfilesRecord.getId(),
                    customerProfilesRecord.getFirstName(),
                    customerProfilesRecord.getLastName(),
                    customerProfilesRecord.getBirthdate(),
                    customerProfilesRecord.getContactDetails() != null
                            ? objectMapper.readValue(customerProfilesRecord.getContactDetails().data(), CustomerContactDetails.class)
                            : null,
                    customerProfilesRecord.getEconomicCircumstances() != null
                            ? objectMapper.readValue(customerProfilesRecord.getEconomicCircumstances().data(), CustomerProfileEconomicCircumstances.class)
                            : null,
                    Customer.RiskToleranceEnum.fromValue(customerProfilesRecord.getRiskTolerance()),
                    customerProfilesRecord.getInvestmentExperience(),
                    customerProfilesRecord.getInvestmentObjectives()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to map CustomerProfilesRecord to Customer", e);
        }
    }

}
