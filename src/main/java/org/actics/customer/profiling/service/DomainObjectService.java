package org.actics.customer.profiling.service;

import org.actics.customer.profiling.model.DomainObject;
import org.actics.customer.profiling.model.DomainObjectAttribute;
import org.actics.customer.profiling.repository.DomainObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DomainObjectService {

    private final DomainObjectRepository domainObjectRepository;

    public DomainObjectService(DomainObjectRepository domainObjectRepository) {
        this.domainObjectRepository = domainObjectRepository;
    }

    public List<DomainObject> getAllDomainObjects() {
        return domainObjectRepository.findAll();
    }

    public DomainObject getDomainObjectById(UUID id) {
        return domainObjectRepository.findById(id).orElse(null);
    }

    public DomainObject createDomainObject(DomainObject domainObject) {
        return domainObjectRepository.save(domainObject).orElse(null);
    }

    public DomainObject updateDomainObject(UUID id, DomainObject updatedDomainObject) {
        return domainObjectRepository.findById(id)
                .map(existingDomainObject -> {
                    existingDomainObject.setType(updatedDomainObject.getType());
                    existingDomainObject.setName(updatedDomainObject.getName());
                    existingDomainObject.setAttributes(updatedDomainObject.getAttributes());
                    return domainObjectRepository.save(existingDomainObject).orElse(null);
                })
                .orElse(null);
    }

    public boolean deleteDomainObject(UUID id) {
        if (domainObjectRepository.existsById(id)) {
            return domainObjectRepository.deleteById(id);
        }
        return false;
    }

    public DomainObjectAttribute addAttributeToDomainObject(UUID domainObjectId, DomainObjectAttribute attribute) {
        return domainObjectRepository.findById(domainObjectId)
                .map(domainObject -> {
                    attribute.setId(null); // Die Datenbank generiert die ID
                    domainObject.getAttributes().add(attribute);
                    domainObjectRepository.save(domainObject);
                    return attribute;
                })
                .orElse(null);
    }

    public List<DomainObjectAttribute> getAttributesForDomainObject(UUID domainObjectId) {
        return domainObjectRepository.findById(domainObjectId)
                .map(DomainObject::getAttributes)
                .orElse(null);
    }

    public boolean deleteAttributeFromDomainObject(UUID domainObjectId, UUID attributeId) {
        return domainObjectRepository.findById(domainObjectId)
                .map(domainObject -> {
                    List<DomainObjectAttribute> updatedAttributes = domainObject.getAttributes().stream()
                            .filter(attr -> !attr.getId().equals(attributeId))
                            .collect(Collectors.toList());
                    domainObject.setAttributes(updatedAttributes);
                    domainObjectRepository.save(domainObject);
                    return true;
                })
                .orElse(false);
    }

    public List<DomainObject> findDomainObjectsByName(String name) {
        return domainObjectRepository.findDomainObjectsByName(name);
    }

    public List<DomainObject> findDomainObjectsByAttribute(String attributeName, String attributeValue) {
        return domainObjectRepository.findDomainObjectsByAttribute(attributeName, attributeValue);
    }
}
