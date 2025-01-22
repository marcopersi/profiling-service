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

    // Bestehende Methode: Alle DomainObjects abrufen
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

    public Optional<DomainObject> save(Optional<DomainObject> domainObjectOptional) {
        if (domainObjectOptional.isEmpty()) {
            return Optional.empty();
        }

        DomainObject domainObject = domainObjectOptional.get();

        if (domainObject.getId() == null) {
            domainObject.setId(UUID.randomUUID());
            dsl.insertInto(BusinessObject.BUSINESS_OBJECT)
                    .set(BusinessObject.BUSINESS_OBJECT.ID, domainObject.getId())
                    .set(BusinessObject.BUSINESS_OBJECT.TYPE, domainObject.getType())
                    .execute();
        } else {
            dsl.update(BusinessObject.BUSINESS_OBJECT)
                    .set(BusinessObject.BUSINESS_OBJECT.TYPE, domainObject.getType())
                    .where(BusinessObject.BUSINESS_OBJECT.ID.eq(domainObject.getId()))
                    .execute();
        }

        // Attribute aktualisieren
        dsl.deleteFrom(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .where(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID.eq(domainObject.getId()))
                .execute();

        domainObject.getAttributes().forEach(attribute -> {
            attribute.setId(UUID.randomUUID());
            dsl.insertInto(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ID, attribute.getId())
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID, domainObject.getId())
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_NAME, attribute.getAttributeName())
                    .set(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_VALUE, attribute.getAttributeValue())
                    .execute();
        });

        // Speichern abgeschlossen, aktualisiertes DomainObject zurückgeben
        return findById(domainObject.getId());
    }

    // Neue Methode: Ein DomainObject löschen
    public boolean deleteById(UUID id) {
        dsl.deleteFrom(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .where(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID.eq(id))
                .execute();

        int deletedObjects = dsl.deleteFrom(BusinessObject.BUSINESS_OBJECT)
                .where(BusinessObject.BUSINESS_OBJECT.ID.eq(id))
                .execute();

        return deletedObjects > 0;
    }

    // Hilfsmethode: Records in DomainObjects mappen
    private List<DomainObject> mapRecordsToDomainObjects(Result<Record> records) {
        return records.intoGroups(BusinessObject.BUSINESS_OBJECT.ID).values().stream()
                .map(recordResult -> {
                    BusinessObjectRecord businessObjectRecord = recordResult.get(0).into(BusinessObject.BUSINESS_OBJECT);
                    List<DomainObjectAttribute> attributes = recordResult.stream()
                            .filter(domainObjectAttribute -> domainObjectAttribute.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ID) != null)
                            .map(domainObjectAttribute -> new DomainObjectAttribute(
                                    domainObjectAttribute.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ID),
                                    null,
                                    domainObjectAttribute.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_NAME),
                                    domainObjectAttribute.get(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.ATTRIBUTE_VALUE)
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
        Optional<DomainObject> byId = findById(id);
        return byId.isPresent();
    }
}