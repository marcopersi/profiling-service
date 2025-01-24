package org.actics.customer.profiling.model.customer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {

    private String street;
    private String postalCode;
    private String city;
    private String country;

}
