package org.actics.customer.profiling.repository;

import org.actics.customer.profiling.model.CustomerProfileResponse;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.*;

@Repository
public class CustomerRepository {

    private DSLContext dsl;

    @Autowired
    public void setDslContext(DSLContext dslContext) {
        this.dsl = dslContext;
    }
    public List<CustomerProfileResponse> findAll() {
        return dsl.selectFrom(table("customers"))
                .fetchInto(CustomerProfileResponse.class);
    }

    public void save(String firstName, String lastName) {
        dsl.insertInto(table("customers"))
                .columns(field("first_name"), field("last_name"))
                .values(firstName, lastName)
                .execute();
    }

    public List<CustomerProfileResponse> search(String firstName, String lastName, String birthdate, String riskTolerance, String email, String phone) {
        // Beispiel einer dynamischen Abfrage
        return dsl.selectFrom(table("customers"))
                .where(firstName != null ? field("first_name").eq(firstName) : noCondition())
                .and(lastName != null ? field("last_name").eq(lastName) : noCondition())
                .and(birthdate != null ? field("birthdate").eq(birthdate) : noCondition())
                .and(riskTolerance != null ? field("risk_tolerance").eq(riskTolerance) : noCondition())
                .and(email != null ? field("email").eq(email) : noCondition())
                .and(phone != null ? field("phone").eq(phone) : noCondition())
                .fetchInto(CustomerProfileResponse.class);
    }

    public CustomerProfileResponse findById(String customerId) {
        return dsl.selectFrom(table("customers"))
                .where(field("id").eq(customerId))
                .fetchOneInto(CustomerProfileResponse.class);
    }
}
