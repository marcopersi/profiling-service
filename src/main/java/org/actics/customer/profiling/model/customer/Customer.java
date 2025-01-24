package org.actics.customer.profiling.model.customer;

import org.actics.customer.profiling.model.enums.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

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
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
