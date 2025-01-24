package org.actics.customer.profiling.service;

import org.actics.customer.profiling.model.customer.Customer;
import org.actics.customer.profiling.model.customer.CustomerResponse;
import org.actics.customer.profiling.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieve all customers.
     * @return List of all customers as CustomerResponse.
     */
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Create a new customer.
     * @param customer The customer data to save.
     * @return The created customer as CustomerResponse.
     */
    public CustomerResponse createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Retrieve a customer by ID.
     * @param customerId The UUID of the customer to retrieve.
     * @return The customer data as CustomerResponse or null if not found.
     */
    public CustomerResponse findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public List<CustomerResponse> searchCustomers(
            String firstName,
            String lastName,
            LocalDate birthdate,
            String segmentation,
            String civilStatus,
            String socialSecurityNumber,
            String email,
            String phone,
            String occupation,
            String academicDegree
    ) {
        return customerRepository.search(firstName, lastName, birthdate, segmentation, civilStatus, socialSecurityNumber, email, phone, occupation, academicDegree);
    }

}