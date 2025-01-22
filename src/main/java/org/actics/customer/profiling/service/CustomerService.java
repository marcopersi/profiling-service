package org.actics.customer.profiling.service;

import org.actics.customer.profiling.model.CustomerProfile;
import org.actics.customer.profiling.model.CustomerProfileResponse;
import org.actics.customer.profiling.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerProfileResponse> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerProfileResponse createCustomer(CustomerProfile profile) {
        customerRepository.save(profile.getFirstName(), profile.getLastName());
        CustomerProfileResponse response = new CustomerProfileResponse();
        response.setFirstName(profile.getFirstName());
        response.setLastName(profile.getLastName());
        return response;
    }

    public CustomerProfileResponse getCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public List<CustomerProfileResponse> searchCustomers(String firstName, String lastName, String birthdate, String riskTolerance, String email, String phone) {
        return customerRepository.search(firstName, lastName, birthdate, riskTolerance, email, phone);
    }
}