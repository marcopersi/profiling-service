package org.actics.customer.profiling.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.actics.customer.profiling.model.Customer;
import org.actics.customer.profiling.model.CustomerProfileResponse;
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
                            schema = @Schema(implementation = CustomerProfileResponse.class)))})
    @GetMapping
    public ResponseEntity<List<CustomerProfileResponse>> getAllCustomerProfiles() {
        List<CustomerProfileResponse> profiles = customerProfileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @Operation(summary = "Create a new customer profile", description = "Create a new customer profile",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerProfileResponse.class)))})
    @PostMapping
    public ResponseEntity<CustomerProfileResponse> createCustomerProfile(@RequestBody Customer profile) {
        CustomerProfileResponse response = customerProfileService.createProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a customer profile by ID", description = "Retrieve a customer profile by ID",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerProfileResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping("/{profileId}")
    public ResponseEntity<CustomerProfileResponse> getCustomerProfileById(@PathVariable String profileId) {
        CustomerProfileResponse response = customerProfileService.getProfileById(UUID.fromString(profileId));
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Update a customer profile", description = "Update an existing customer profile",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerProfileResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping("/{profileId}")
    public ResponseEntity<CustomerProfileResponse> updateCustomerProfile(@PathVariable String profileId,
                                                                         @RequestBody Customer profile) {
        CustomerProfileResponse response = customerProfileService.updateProfile(UUID.fromString(profileId), profile);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Delete a customer profile", description = "Delete a customer profile by ID",
            responses = {@ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteCustomerProfile(@PathVariable String profileId) {
        boolean deleted = customerProfileService.deleteProfile(UUID.fromString(profileId));
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
