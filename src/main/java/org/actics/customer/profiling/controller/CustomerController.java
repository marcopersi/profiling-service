package org.actics.customer.profiling.controller;

import org.actics.customer.profiling.model.CustomerProfile;
import org.actics.customer.profiling.model.CustomerProfileResponse;
import org.actics.customer.profiling.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<List<CustomerProfileResponse>> getAllCustomers() {
        // Ruft alle Kunden aus dem Repository ab
        List<CustomerProfileResponse> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<CustomerProfileResponse> createCustomer(@RequestBody CustomerProfile profile) {
        // Speichert einen neuen Kunden im Repository
        customerRepository.save(profile.getFirstName(), profile.getLastName());
        CustomerProfileResponse response = new CustomerProfileResponse();
        response.setFirstName(profile.getFirstName());
        response.setLastName(profile.getLastName());
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
        // Sucht Kunden basierend auf den angegebenen Parametern
        List<CustomerProfileResponse> customers = customerRepository.search(firstName, lastName, birthdate, riskTolerance, email, phone);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerProfileResponse> getCustomerById(@PathVariable String customerId) {
        // Ruft einen Kunden anhand seiner ID aus dem Repository ab
        CustomerProfileResponse customer = customerRepository.findById(customerId);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
