package org.actics.customer.profiling.api;

import org.actics.customer.profiling.model.customer.Customer;
import org.actics.customer.profiling.model.customer.CustomerResponse;
import org.actics.customer.profiling.service.CustomerService;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "customers", description = "Customer API for handling customer data")
@RequestMapping("/customers")
public class CustomersApi {

    private final CustomerService customerService;

    public CustomersApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            operationId = "getCustomerById",
            summary = "Retrieve a customer by ID",
            description = "Get details of a customer by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(name = "customerId", description = "The unique ID of the customer", required = true, in = ParameterIn.PATH) @PathVariable String customerId
    ) {
        CustomerResponse customer = customerService.findById(UUID.fromString(customerId));
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @Operation(
            operationId = "getAllCustomers",
            summary = "Retrieve all customers",
            description = "Get a list of all customers.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of customers", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class))))
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(
            operationId = "createCustomer",
            summary = "Create a new customer",
            description = "Add a new customer to the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> createCustomer(
            @Parameter(name = "Customer", description = "The customer to be created", required = true) @Valid @RequestBody Customer customer
    ) {
        CustomerResponse createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(201).body(createdCustomer);
    }

    @Operation(
            operationId = "searchCustomers",
            summary = "Search customers",
            description = "Search for customers based on various attributes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matching customers", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid query parameters")
            }
    )

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> searchCustomers(
            @Parameter(name = "firstName", description = "Filter by first name", in = ParameterIn.QUERY) @RequestParam(required = false) String firstName,
            @Parameter(name = "lastName", description = "Filter by last name", in = ParameterIn.QUERY) @RequestParam(required = false) String lastName,
            @Parameter(name = "birthdate", description = "Filter by birthdate", in = ParameterIn.QUERY) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) LocalDate birthdate,
            @Parameter(name = "segmentation", description = "Filter by customer segmentation", in = ParameterIn.QUERY) @RequestParam(required = false) String segmentation,
            @Parameter(name = "civilStatus", description = "Filter by civil status", in = ParameterIn.QUERY) @RequestParam(required = false) String civilStatus,
            @Parameter(name = "socialSecurityNumber", description = "Filter by social security number", in = ParameterIn.QUERY) @RequestParam(required = false) String socialSecurityNumber,
            @Parameter(name = "email", description = "Filter by email", in = ParameterIn.QUERY) @RequestParam(required = false) String email,
            @Parameter(name = "phone", description = "Filter by phone", in = ParameterIn.QUERY) @RequestParam(required = false) String phone,
            @Parameter(name = "occupation", description = "Filter by occupation", in = ParameterIn.QUERY) @RequestParam(required = false) String occupation,
            @Parameter(name = "academicDegree", description = "Filter by academic degree", in = ParameterIn.QUERY) @RequestParam(required = false) String academicDegree
    ) {
        List<CustomerResponse> customers = customerService.searchCustomers(firstName, lastName, birthdate, segmentation, civilStatus, socialSecurityNumber, email, phone, occupation, academicDegree);
        return ResponseEntity.ok(customers);
    }
}
