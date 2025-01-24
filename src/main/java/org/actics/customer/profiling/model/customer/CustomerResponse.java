package org.actics.customer.profiling.model.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.actics.customer.profiling.model.enums.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private UUID id;
    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private CivilStatus civilStatus;
    private String socialSecurityNumber;
    private Address address;
    private List<Hobby> hobbies;
    private String occupation;
    private String employer;
    private String academicDegree;
    private CustomerSegmentation segmentation;
    private CustomerContactDetails contactDetails;
}
