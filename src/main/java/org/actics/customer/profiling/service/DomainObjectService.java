package org.actics.customer.profiling.service;

import org.actics.customer.profiling.model.DomainObject;
import org.actics.customer.profiling.model.DomainObjectAttribute;
import org.actics.customer.profiling.repository.DomainObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        domainObject.setId(UUID.randomUUID());
        return domainObjectRepository.save(Optional.of(domainObject)).orElse(null);
    }

    public DomainObject updateDomainObject(UUID id, DomainObject updatedDomainObject) {
        Optional<DomainObject> existingDomainObject = domainObjectRepository.findById(id);
        if (existingDomainObject.isPresent()) {
            DomainObject domainObject = existingDomainObject.get();
            domainObject.setType(updatedDomainObject.getType());
            domainObject.setAttributes(updatedDomainObject.getAttributes());
            return domainObjectRepository.save(Optional.of(domainObject)).orElse(null);
        }
        return null;
    }

    public boolean deleteDomainObject(UUID id) {
        if (domainObjectRepository.existsById(id)) {
            return domainObjectRepository.deleteById(id);
        }
        return false;
    }

    public DomainObjectAttribute addAttributeToDomainObject(UUID domainObjectId, DomainObjectAttribute attribute) {
        Optional<DomainObject> domainObjectOptional = domainObjectRepository.findById(domainObjectId);
        if (domainObjectOptional.isPresent()) {
            DomainObject domainObject = domainObjectOptional.get();
            attribute.setId(UUID.randomUUID());
            domainObject.getAttributes().add(attribute);
            domainObjectRepository.save(Optional.of(domainObject));
            return attribute;
        }
        return null;
    }

    public List<DomainObjectAttribute> getAttributesForDomainObject(UUID domainObjectId) {
        return domainObjectRepository.findById(domainObjectId)
                .map(DomainObject::getAttributes)
                .orElse(null);
    }

    public boolean deleteAttributeFromDomainObject(UUID domainObjectId, UUID attributeId) {
        Optional<DomainObject> domainObjectOptional = domainObjectRepository.findById(domainObjectId);
        if (domainObjectOptional.isPresent()) {
            DomainObject domainObject = domainObjectOptional.get();
            List<DomainObjectAttribute> updatedAttributes = domainObject.getAttributes().stream()
                    .filter(attr -> !attr.getId().equals(attributeId))
                    .collect(Collectors.toList());
            domainObject.setAttributes(updatedAttributes);
            domainObjectRepository.save(Optional.of(domainObject));
            return true;
        }
        return false;
    }

    public List<DomainObject> findDomainObjectsByName(String name) {
        return domainObjectRepository.findDomainObjectsByName(name);
    }

    public List<DomainObject> findDomainObjectsByAttribute(String attributeName, String attributeValue) {
        return domainObjectRepository.findDomainObjectsByAttribute(attributeName, attributeValue);
    }
}