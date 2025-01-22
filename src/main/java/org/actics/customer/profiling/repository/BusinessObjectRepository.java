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
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BusinessObjectRepository {

    private final DSLContext dsl;

    public BusinessObjectRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    // Bestehende Methode: Alle DomainObjects abrufen
    public List<DomainObject> getAllDomainObjects() {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .fetch();

        return mapRecordsToDomainObjects(records);
    }

    // Neue Methode: DomainObjects nach Name suchen
    public List<DomainObject> findDomainObjectsByName(String name) {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .where(BusinessObject.BUSINESS_OBJECT.TYPE.eq(name))
                .fetch();

        return mapRecordsToDomainObjects(records);
    }

    // Neue Methode: DomainObjects mit spezifischem Attribut suchen
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

    // Neue Methode: Ein DomainObject anhand der ID abrufen
    public DomainObject findDomainObjectById(UUID id) {
        Result<Record> records = dsl.select()
                .from(BusinessObject.BUSINESS_OBJECT)
                .leftJoin(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES)
                .on(BusinessObject.BUSINESS_OBJECT.ID.eq(BusinessObjectAttributes.BUSINESS_OBJECT_ATTRIBUTES.BUSINESS_OBJECT_ID))
                .where(BusinessObject.BUSINESS_OBJECT.ID.eq(id))
                .fetch();

        List<DomainObject> domainObjects = mapRecordsToDomainObjects(records);
        return domainObjects.isEmpty() ? null : domainObjects.get(0);
    }

    // Hilfsmethode: Records in DomainObjects mappen
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
                            attributes
                    );
                })
                .collect(Collectors.toList());
    }


}
