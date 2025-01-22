package org.actics.customer.profiling.controller;

import org.actics.customer.profiling.model.Customer;
import org.actics.customer.profiling.model.CustomerProfileResponse;
import org.actics.customer.profiling.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerProfileResponse>> getAllCustomers() {
        List<CustomerProfileResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<CustomerProfileResponse> createCustomer(@RequestBody Customer profile) {
        CustomerProfileResponse response = customerService.createCustomer(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerProfileResponse>> searchCustomers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String birthdate,
            @RequestParam(required = false) String riskTolerance,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone) {
        List<CustomerProfileResponse> customers = customerService.searchCustomers(firstName, lastName, birthdate, riskTolerance, email, phone);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerProfileResponse> getCustomerById(@PathVariable String customerId) {
        CustomerProfileResponse customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}