package org.actics.customer.profiling.controller;

import org.actics.customer.profiling.model.DomainObject;
import org.actics.customer.profiling.model.DomainObjectAttribute;
import org.actics.customer.profiling.service.DomainObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/domain-objects")
public class DomainObjectController {

    private final DomainObjectService domainObjectService;

    public DomainObjectController(DomainObjectService domainObjectService) {
        this.domainObjectService = domainObjectService;
    }

    @GetMapping
    public ResponseEntity<List<DomainObject>> getAllDomainObjects() {
        List<DomainObject> domainObjects = domainObjectService.getAllDomainObjects();
        return ResponseEntity.ok(domainObjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomainObject> getDomainObjectById(@PathVariable UUID id) {
        DomainObject domainObject = domainObjectService.getDomainObjectById(id);
        if (domainObject != null) {
            return ResponseEntity.ok(domainObject);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DomainObject> createDomainObject(@RequestBody DomainObject domainObject) {
        DomainObject createdDomainObject = domainObjectService.createDomainObject(domainObject);
        return ResponseEntity.ok(createdDomainObject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomainObject> updateDomainObject(@PathVariable UUID id, @RequestBody DomainObject domainObject) {
        DomainObject updatedDomainObject = domainObjectService.updateDomainObject(id, domainObject);
        if (updatedDomainObject != null) {
            return ResponseEntity.ok(updatedDomainObject);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomainObject(@PathVariable UUID id) {
        boolean deleted = domainObjectService.deleteDomainObject(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{domainObjectId}/attributes")
    public ResponseEntity<DomainObjectAttribute> addAttributeToDomainObject(
            @PathVariable UUID domainObjectId,
            @RequestBody DomainObjectAttribute domainObjectAttribute) {
        DomainObjectAttribute createdAttribute = domainObjectService.addAttributeToDomainObject(domainObjectId, domainObjectAttribute);
        return ResponseEntity.ok(createdAttribute);
    }

    @GetMapping("/{domainObjectId}/attributes")
    public ResponseEntity<List<DomainObjectAttribute>> getAttributesForDomainObject(@PathVariable UUID domainObjectId) {
        List<DomainObjectAttribute> attributes = domainObjectService.getAttributesForDomainObject(domainObjectId);
        return ResponseEntity.ok(attributes);
    }

    @DeleteMapping("/{domainObjectId}/attributes/{attributeId}")
    public ResponseEntity<Void> deleteAttributeFromDomainObject(
            @PathVariable UUID domainObjectId, @PathVariable UUID attributeId) {
        boolean deleted = domainObjectService.deleteAttributeFromDomainObject(domainObjectId, attributeId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}