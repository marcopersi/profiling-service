package org.actics.customer.profiling.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.actics.customer.profiling.model.customer.*;
import org.actics.customer.profiling.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customer-profiles")
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    @Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @Operation(summary = "Get all customer profiles", description = "Retrieve all customer profiles",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class)))})
    @GetMapping
    public ResponseEntity<List<CustomerProfileResponse>> getAllCustomerProfiles() {
        List<CustomerProfileResponse> profiles = customerProfileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @Operation(summary = "Create a new customer profile", description = "Create a new customer profile",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class)))})
    @PostMapping
    public ResponseEntity<CustomerProfileResponse> createCustomerProfile(@RequestBody CustomerProfile customerProfile) {
        CustomerProfileResponse response = customerProfileService.createProfile(customerProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a customer profile by ID", description = "Retrieve a customer profile by ID",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping("/{profileId}")
    public ResponseEntity<CustomerProfileResponse> getCustomerProfileById(@PathVariable UUID profileId) {
        CustomerProfileResponse response = customerProfileService.getProfileById(profileId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Update a customer profile", description = "Update an existing customer profile",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    public ResponseEntity<CustomerProfileResponse> updateCustomerProfile(@RequestBody CustomerProfile profile) {
        CustomerProfileResponse response = customerProfileService.updateProfile(profile);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Delete a customer profile", description = "Delete a customer profile by ID",
            responses = {@ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable UUID profileId) {
        boolean deleted = customerProfileService.deleteProfile(profileId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Update economic circumstances", description = "Update the economic circumstances for a customer profile",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EconomicCircumstances.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping("/{profileId}/economic-circumstances")
    public ResponseEntity<CustomerProfileResponse> updateEconomicCircumstances(@PathVariable UUID profileId,
                                                                             @RequestBody EconomicCircumstances circumstances) {
        CustomerProfileResponse updated = customerProfileService.updateEconomicCircumstances(profileId, circumstances);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Get economic circumstances", description = "Retrieve the economic circumstances for a customer profile",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EconomicCircumstances.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping("/{profileId}/economic-circumstances")
    public ResponseEntity<EconomicCircumstances> getEconomicCircumstances(@PathVariable UUID profileId) {
        EconomicCircumstances circumstances = customerProfileService.getEconomicCircumstances(profileId);
        return circumstances != null ? ResponseEntity.ok(circumstances) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Update knowledge and experience", description = "Update the knowledge and experience for a customer profile",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KnowledgeAndExperience.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping("/{profileId}/knowledge-experience")
    public ResponseEntity<KnowledgeAndExperience> updateKnowledgeAndExperience(@PathVariable UUID profileId,
                                                                               @RequestBody KnowledgeAndExperience knowledgeAndExperience) {
        CustomerProfileResponse updated = customerProfileService.updateKnowledgeAndExperience(profileId, knowledgeAndExperience);
        return updated != null ? ResponseEntity.ok(updated.getKnowledgeAndExperience()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Get knowledge and experience", description = "Retrieve the knowledge and experience for a customer profile",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KnowledgeAndExperience.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping("/{profileId}/knowledge-experience")
    public ResponseEntity<KnowledgeAndExperience> getKnowledgeAndExperience(@PathVariable UUID profileId) {
        KnowledgeAndExperience knowledgeAndExperience = customerProfileService.getKnowledgeAndExperience(profileId);
        return knowledgeAndExperience != null ? ResponseEntity.ok(knowledgeAndExperience) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
