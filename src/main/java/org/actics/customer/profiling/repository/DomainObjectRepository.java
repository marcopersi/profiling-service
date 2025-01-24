package org.actics.customer.profiling.repository;

import org.actics.customer.profiling.jooq.tables.BusinessObject;
import org.actics.customer.profiling.jooq.tables.BusinessObjectAttributes;
import org.actics.customer.profiling.jooq.tables.records.BusinessObjectRecord;
import org.actics.customer.profiling.model.DomainObject;
import org.actics.customer.profiling.model.DomainObjectAttribute;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DomainObjectRepository {

    private final DSLContext dsl;

    public DomainObjectRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<DomainObject> findAll() {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .fetch();

        return mapRecordsToDomainObjects(records);
    }

    public List<DomainObject> findDomainObjectsByName(String name) {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .where(BusinessObject.BUSINESS_OBJECT.TYPE.eq(name))
                .fetch();

        return mapRecordsToDomainObjects(records);
    }

    public List<DomainObject> findDomainObjectsByAttribute(String attributeName, String attributeValue) {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .where(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_NAME.eq(attributeName)
                        .and(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_VALUE.eq(attributeValue)))
                .fetch();

        return mapRecordsToDomainObjects(records);
    }

    public Optional<DomainObject> findById(UUID id) {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .where(BusinessObject.BUSINESS_OBJECT.ID.eq(id))
                .fetch();

        List<DomainObject> domainObjects = mapRecordsToDomainObjects(records);
        return domainObjects.isEmpty() ? Optional.empty() : Optional.of(domainObjects.get(0));
    }

    public Optional<DomainObject> save(DomainObject domainObject) {
        if (domainObject == null) {
            return Optional.empty();
        }

        UUID domainObjectId = Optional.ofNullable(domainObject.getId()).orElse(UUID.randomUUID());
        domainObject.setId(domainObjectId);

        if (existsById(domainObjectId)) {
            dsl.update(BusinessObject.BUSINESS_OBJECT)
                    .set(BusinessObject.BUSINESS_OBJECT.TYPE, domainObject.getType())
                    .set(BusinessObject.BUSINESS_OBJECT.NAME, domainObject.getName())
                    .where(BusinessObject.BUSINESS_OBJECT.ID.eq(domainObjectId))
                    .execute();
        } else {
            dsl.insertInto(BusinessObject.BUSINESS_OBJECT)
                    .set(BusinessObject.BUSINESS_OBJECT.ID, domainObjectId)
                    .set(BusinessObject.BUSINESS_OBJECT.TYPE, domainObject.getType())
                    .set(BusinessObject.BUSINESS_OBJECT.NAME, domainObject.getName())
                    .execute();
        }

        updateAttributes(domainObject);

        return findById(domainObjectId);
    }

    public boolean deleteById(UUID id) {
        dsl.deleteFrom(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .where(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID.eq(id))
                .execute();

        int deletedObjects = dsl.deleteFrom(BusinessObject.BUSINESS_OBJECT)
                .where(BusinessObject.BUSINESS_OBJECT.ID.eq(id))
                .execute();

        return deletedObjects > 0;
    }

    private void updateAttributes(DomainObject domainObject) {
        UUID domainObjectId = domainObject.getId();

        // Lösche existierende Attribute
        dsl.deleteFrom(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .where(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID.eq(domainObjectId))
                .execute();

        // Füge neue Attribute hinzu
        domainObject.getAttributes().forEach(attribute -> {
            UUID attributeId = Optional.ofNullable(attribute.getId()).orElse(UUID.randomUUID());
            attribute.setId(attributeId);

            dsl.insertInto(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ID, attributeId)
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID, domainObjectId)
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_NAME, attribute.getAttributeName())
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_VALUE, attribute.getAttributeValue())
                    .execute();
        });
    }

    private List<DomainObject> mapRecordsToDomainObjects(Result<Record> records) {
        return records.intoGroups(BusinessObject.BUSINESS_OBJECT.ID).entrySet().stream()
                .map(entry -> {
                    BusinessObjectRecord businessObjectRecord = entry.getValue().get(0).into(BusinessObject.BUSINESS_OBJECT);
                    List<DomainObjectAttribute> attributes = entry.getValue().stream()
                            .filter(record -> record.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ID) != null)
                            .map(record -> new DomainObjectAttribute(
                                    record.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ID),
                                    null,
                                    record.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_NAME),
                                    record.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_VALUE)
                            ))
                            .collect(Collectors.toList());

                    return new DomainObject(
                            businessObjectRecord.getId(),
                            businessObjectRecord.getType(),
                            businessObjectRecord.getName(),
                            attributes
                    );
                })
                .collect(Collectors.toList());
    }

    public boolean existsById(UUID id) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(BusinessObject.BUSINESS_OBJECT)
                        .where(BusinessObject.BUSINESS_OBJECT.ID.eq(id))
        );
    }
}
