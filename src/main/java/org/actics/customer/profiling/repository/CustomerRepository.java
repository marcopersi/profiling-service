package org.actics.customer.profiling.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.actics.customer.profiling.model.customer.*;
import org.actics.customer.profiling.model.enums.*;
import org.jooq.*;

import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
public class CustomerRepository {

    private static final Logger log = LoggerFactory.getLogger(CustomerRepository.class);

    private DSLContext dsl;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerRepository(DSLContext dslContext, ObjectMapper objectMapper) {
        this.dsl = dslContext;
        this.objectMapper = objectMapper;
    }

    public List<CustomerResponse> findAll() {
        return dsl.select(
                        field("id"),
                        field("salutation"),
                        field("first_name"),
                        field("last_name"),
                        field("birthdate"),
                        field("civil_status"),
                        field("social_security_number"),
                        field("occupation"),
                        field("employer"),
                        field("academic_degree"),
                        field("segmentation"),
                        field("contact_details"),
                        field("hobbies"),
                        field("address")
                )
                .from(table("customers"))
                .fetch(this::mapRecordToResponse);
    }

    public CustomerResponse save(Customer customer) {
        JSONB contactDetails = null;
        JSONB hobbies = null;
        JSONB address = null;

        try {
            contactDetails = customer.getContactDetails() != null
                    ? JSONB.valueOf(objectMapper.writeValueAsString(customer.getContactDetails()))
                    : null;
            hobbies = customer.getHobbies() != null
                    ? JSONB.valueOf(objectMapper.writeValueAsString(customer.getHobbies()))
                    : null;
            address = customer.getAddress() != null
                    ? JSONB.valueOf(objectMapper.writeValueAsString(customer.getAddress()))
                    : null;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize JSON fields", e);
        }

        // Verwende fetchOne(), um ein einzelnes Record zu erhalten
        Record record = dsl.insertInto(table("customers"))
                .columns(
                        field("id"),
                        field("salutation"),
                        field("first_name"),
                        field("last_name"),
                        field("birthdate"),
                        field("civil_status"),
                        field("social_security_number"),
                        field("occupation"),
                        field("employer"),
                        field("academic_degree"),
                        field("segmentation"),
                        field("contact_details"),
                        field("hobbies"),
                        field("address")
                )
                .values(
                        customer.getId() != null ? customer.getId() : UUID.randomUUID(),
                        customer.getSalutation() != null ? customer.getSalutation().name() : null,
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getBirthdate(),
                        customer.getCivilStatus() != null ? customer.getCivilStatus().name() : null,
                        customer.getSocialSecurityNumber(),
                        customer.getOccupation(),
                        customer.getEmployer(),
                        customer.getAcademicDegree(),
                        customer.getSegmentation() != null ? customer.getSegmentation().name() : null,
                        contactDetails,
                        hobbies,
                        address
                )
                .onConflict(field("id"))
                .doUpdate()
                .set(field("salutation"), customer.getSalutation() != null ? customer.getSalutation().name() : null)
                .set(field("first_name"), customer.getFirstName())
                .set(field("last_name"), customer.getLastName())
                .set(field("birthdate"), customer.getBirthdate())
                .set(field("civil_status"), customer.getCivilStatus() != null ? customer.getCivilStatus().name() : null)
                .set(field("social_security_number"), customer.getSocialSecurityNumber())
                .set(field("occupation"), customer.getOccupation())
                .set(field("employer"), customer.getEmployer())
                .set(field("academic_degree"), customer.getAcademicDegree())
                .set(field("segmentation"), customer.getSegmentation() != null ? customer.getSegmentation().name() : null)
                .set(field("contact_details"), contactDetails)
                .set(field("hobbies"), hobbies)
                .set(field("address"), address)
                .returning()
                .fetchOne(); // Ruft genau einen Datensatz ab

        // Verwende mapRecordToResponse für die Umwandlung
        return record != null ? mapRecordToResponse(record):null;
    }

    private CustomerResponse mapRecordToResponse(Record record) {

        log.info("Raw birthdate value: {}", record.getValue("birthdate"));


        try {
            return CustomerResponse.builder()
                    .id(record.getValue("id", UUID.class))
                    .salutation(record.getValue("salutation", String.class) != null
                            ? Salutation.valueOf(record.getValue("salutation", String.class)) : null)
                    .firstName(record.getValue("first_name", String.class))
                    .lastName(record.getValue("last_name", String.class))

                    .birthdate(record.getValue("birthdate", java.sql.Date.class) != null
                            ? record.getValue("birthdate", java.sql.Date.class).toLocalDate()
                            : null)

                    .civilStatus(record.getValue("civil_status", String.class) != null
                            ? CivilStatus.valueOf(record.getValue("civil_status", String.class)) : null)
                    .socialSecurityNumber(record.getValue("social_security_number", String.class))
                    .occupation(record.getValue("occupation", String.class))
                    .employer(record.getValue("employer", String.class))
                    .academicDegree(record.getValue("academic_degree", String.class))
                    .segmentation(record.getValue("segmentation", String.class) != null
                            ? CustomerSegmentation.valueOf(record.getValue("segmentation", String.class)) : null)
                    .contactDetails(record.getValue("contact_details", JSONB.class) != null
                            ? objectMapper.readValue(record.getValue("contact_details", JSONB.class).data(), CustomerContactDetails.class)
                            : null)
                    .hobbies(record.getValue("hobbies", JSONB.class) != null
                            ? objectMapper.readValue(record.getValue("hobbies", JSONB.class).data(), new TypeReference<List<Hobby>>() {})
                            : null)
                    .address(record.getValue("address", JSONB.class) != null
                            ? objectMapper.readValue(record.getValue("address", JSONB.class).data(), Address.class)
                            : null)
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to map record to CustomerResponse", e);
        }
    }

    public CustomerResponse findById(UUID customerId) {
        Record record = dsl.select(
                        field("id"),
                        field("salutation"),
                        field("first_name"),
                        field("last_name"),
                        field("birthdate"),
                        field("civil_status"),
                        field("social_security_number"),
                        field("occupation"),
                        field("employer"),
                        field("academic_degree"),
                        field("segmentation"),
                        field("contact_details"),
                        field("hobbies"),
                        field("address")
                )
                .from(table("customers"))
                .where(field("id").eq(customerId))
                .fetchOne();

        if (record == null) {
            return null;
        }

        try {
            return CustomerResponse.builder()
                    .id(record.getValue("id", UUID.class))
                    .salutation(record.getValue("salutation", String.class) != null ? Salutation.valueOf(record.getValue("salutation", String.class)): null)
                    .firstName(record.getValue("first_name", String.class))
                    .lastName(record.getValue("last_name", String.class))
                    // Erzwinge die Nutzung des benutzerdefinierten Converters
                    .birthdate(convertToLocalDate(record.getValue("birthdate", Object.class)))
                    .civilStatus(record.getValue("civil_status", String.class) != null ? CivilStatus.valueOf(record.getValue("civil_status", String.class)): null)
                    .socialSecurityNumber(record.getValue("social_security_number", String.class))
                    .occupation(record.getValue("occupation", String.class))
                    .employer(record.getValue("employer", String.class))
                    .academicDegree(record.getValue("academic_degree", String.class))
                    .segmentation(record.getValue("segmentation", String.class) != null ? CustomerSegmentation.valueOf(record.getValue("segmentation", String.class)): null)
                    .contactDetails(objectMapper.readValue(record.getValue("contact_details", JSONB.class).data(),CustomerContactDetails.class))
                    .hobbies(objectMapper.readValue(record.getValue("hobbies", JSONB.class).data(),new TypeReference<List<Hobby>>() {}))
                    .address(objectMapper.readValue(record.getValue("address", JSONB.class).data(), Address.class))
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Error mapping customer record", e);
        }
    }

    private LocalDate convertToLocalDate(Object value) {
        if (value instanceof String) {
            return LocalDate.parse((String) value); // String zu LocalDate parsen
        } else if (value instanceof java.sql.Date) {
            return ((java.sql.Date) value).toLocalDate(); // SQL-Date zu LocalDate konvertieren
        } else if (value == null) {
            return null; // Nullwerte behandeln
        } else {
            throw new IllegalArgumentException("Unsupported type for birthdate: " + value.getClass());
        }
    }

    public List<CustomerResponse> search(
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
        // Grundabfrage definieren
        var query = dsl.select(
                        field("id"),
                        field("salutation"),
                        field("first_name"),
                        field("last_name"),
                        field("birthdate"),
                        field("civil_status"),
                        field("social_security_number"),
                        field("occupation"),
                        field("employer"),
                        field("academic_degree"),
                        field("segmentation"),
                        field("contact_details"),
                        field("hobbies"),
                        field("address")
                )
                .from(table("customers"));

        // Dynamische Bedingungen aufbauen
        List<Condition> conditions = new ArrayList<>();
        if (firstName != null) conditions.add(field("first_name").eq(firstName));
        if (lastName != null) conditions.add(field("last_name").eq(lastName));
        if (birthdate != null) conditions.add(field("birthdate").eq(birthdate));
        if (segmentation != null) conditions.add(field("segmentation").eq(segmentation));
        if (civilStatus != null) conditions.add(field("civil_status").eq(civilStatus));
        if (socialSecurityNumber != null) conditions.add(field("social_security_number").eq(socialSecurityNumber));
        if (email != null) conditions.add(field("contact_details->>'email'").eq(email));
        if (phone != null) conditions.add(field("contact_details->>'phone'").eq(phone));
        if (occupation != null) conditions.add(field("occupation").eq(occupation));
        if (academicDegree != null) conditions.add(field("academic_degree").eq(academicDegree));

        // Dynamische Bedingungen anwenden
        if (!conditions.isEmpty()) {
            return query.where(conditions).fetch().map(this::mapRecordToResponse);
        }
        return query.fetch().map(this::mapRecordToResponse);
    }

}
