package org.actics.customer.profiling.controller;

import org.actics.customer.profiling.model.customer.Customer;
import org.actics.customer.profiling.model.customer.CustomerResponse;
import org.actics.customer.profiling.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieve all customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Create a new customer
     */
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody Customer customer) {
        CustomerResponse response = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieve a customer by ID
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String customerId) {
        CustomerResponse customer = customerService.findById(UUID.fromString(customerId));
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Search customers by attributes
     */
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchCustomers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String birthdate,
            @RequestParam(required = false) String segmentation,
            @RequestParam(required = false) String civilStatus,
            @RequestParam(required = false) String socialSecurityNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String occupation,
            @RequestParam(required = false) String academicDegree
    ) {
        List<CustomerResponse> customers = customerService.searchCustomers(
                firstName,
                lastName,
                birthdate != null ? LocalDate.parse(birthdate) : null,
                segmentation,
                civilStatus,
                socialSecurityNumber,
                email,
                phone,
                occupation,
                academicDegree
        );
        return ResponseEntity.ok(customers);
    }

}
