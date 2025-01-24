package org.actics.customer.profiling.service;

import org.actics.customer.profiling.model.customer.CustomerProfile;
import org.actics.customer.profiling.model.customer.CustomerProfileResponse;
import org.actics.customer.profiling.model.customer.EconomicCircumstances;
import org.actics.customer.profiling.model.customer.KnowledgeAndExperience;
import org.actics.customer.profiling.repository.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    /**
     * Retrieve all customer profiles.
     */
    public List<CustomerProfileResponse> getAllProfiles() {
        return customerProfileRepository.findAll();
    }

    /**
     * Create a new customer profile.
     */
    public CustomerProfileResponse createProfile(CustomerProfile profile) {
        return customerProfileRepository.save(profile);
    }

    /**
     * Retrieve a customer profile by ID.
     */
    public CustomerProfileResponse getProfileById(UUID profileId) {
        return customerProfileRepository.findById(profileId).orElse(null);
    }

    /**
     * Update an existing customer profile.
     */
    public CustomerProfileResponse updateProfile(CustomerProfile updatedProfile) {
        return customerProfileRepository.save(updatedProfile);
    }

    /**
     * Delete a customer profile.
     */
    public boolean deleteProfile(UUID profileId) {
        if (customerProfileRepository.existsById(profileId)) {
            customerProfileRepository.deleteById(profileId);
            return true;
        }
        return false;
    }

    /**
     * Update economic circumstances for a customer profile.
     */
    public CustomerProfileResponse updateEconomicCircumstances(UUID profileId, EconomicCircumstances circumstances) {
        //FIXME implementation missing
        return null;
    }

    /**
     * Retrieve economic circumstances for a customer profile.
     */
    public EconomicCircumstances getEconomicCircumstances(UUID profileId) {
        return customerProfileRepository.findById(profileId)
                .map(CustomerProfileResponse::getEconomicCircumstances)
                .orElse(null);
    }

    /**
     * Update knowledge and experience for a customer profile.
     */
    public CustomerProfileResponse updateKnowledgeAndExperience(UUID profileId, KnowledgeAndExperience knowledgeAndExperience) {
        //FIXME implementation missing
        return null;
    }

    /**
     * Retrieve knowledge and experience for a customer profile.
     */
    public KnowledgeAndExperience getKnowledgeAndExperience(UUID profileId) {
        return customerProfileRepository.findById(profileId)
                .map(CustomerProfileResponse::getKnowledgeAndExperience)
                .orElse(null);
    }

}
