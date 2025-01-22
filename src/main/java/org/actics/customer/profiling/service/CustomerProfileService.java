package org.actics.customer.profiling.service;

import org.actics.customer.profiling.model.CustomerProfile;
import org.actics.customer.profiling.model.CustomerProfileResponse;
import org.actics.customer.profiling.repository.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerProfileService {


    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    public List<CustomerProfileResponse> getAllProfiles() {
        return customerProfileRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CustomerProfileResponse createProfile(CustomerProfile profile) {
        CustomerProfile savedProfile = customerProfileRepository.save(profile);
        return mapToResponse(savedProfile);
    }

    public CustomerProfileResponse getProfileById(UUID profileId) {
        return customerProfileRepository.findById(profileId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    public CustomerProfileResponse updateProfile(UUID profileId, CustomerProfile updatedProfile) {
        return customerProfileRepository.findById(profileId)
                .map(existingProfile -> {
                    existingProfile.setFirstName(updatedProfile.getFirstName());
                    existingProfile.setLastName(updatedProfile.getLastName());
                    existingProfile.setBirthdate(updatedProfile.getBirthdate());
                    existingProfile.setContactDetails(updatedProfile.getContactDetails());
                    existingProfile.setEconomicCircumstances(updatedProfile.getEconomicCircumstances());
                    existingProfile.setRiskTolerance(updatedProfile.getRiskTolerance());
                    existingProfile.setInvestmentExperience(updatedProfile.getInvestmentExperience());
                    existingProfile.setInvestmentObjectives(updatedProfile.getInvestmentObjectives());
                    CustomerProfile savedProfile = customerProfileRepository.save(existingProfile);
                    return mapToResponse(savedProfile);
                })
                .orElse(null);
    }

    public boolean deleteProfile(UUID profileId) {
        if (customerProfileRepository.existsById(profileId)) {
            customerProfileRepository.deleteById(profileId);
            return true;
        }
        return false;
    }

    private CustomerProfileResponse mapToResponse(CustomerProfile profile) {
        CustomerProfileResponse response = new CustomerProfileResponse();
        response.setId(profile.getId());
        response.setFirstName(profile.getFirstName());
        response.setLastName(profile.getLastName());
        return response;
    }

}
