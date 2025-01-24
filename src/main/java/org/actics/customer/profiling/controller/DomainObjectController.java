package org.actics.customer.profiling.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.actics.customer.profiling.model.DomainObject;
import org.actics.customer.profiling.model.DomainObjectAttribute;
import org.actics.customer.profiling.service.DomainObjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/domain-objects")
@Tag(name = "Domain Objects", description = "API for managing domain objects and their attributes")
public class DomainObjectController {

    private final DomainObjectService domainObjectService;

    public DomainObjectController(DomainObjectService domainObjectService) {
        this.domainObjectService = domainObjectService;
    }

    @Operation(summary = "Get all domain objects", description = "Retrieve all domain objects",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DomainObject.class)))})
    @GetMapping
    public ResponseEntity<List<DomainObject>> getAllDomainObjects() {
        List<DomainObject> domainObjects = domainObjectService.getAllDomainObjects();
        return ResponseEntity.ok(domainObjects);
    }

    @Operation(summary = "Get domain object by ID", description = "Retrieve a specific domain object by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DomainObject.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping("/{id}")
    public ResponseEntity<DomainObject> getDomainObjectById(@PathVariable UUID id) {
        DomainObject domainObject = domainObjectService.getDomainObjectById(id);
        return domainObject != null ? ResponseEntity.ok(domainObject) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Create a new domain object", description = "Create a new domain object",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DomainObject.class)))})
    @PostMapping
    public ResponseEntity<DomainObject> createDomainObject(@Valid @RequestBody DomainObject domainObject) {
        DomainObject createdDomainObject = domainObjectService.createDomainObject(domainObject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDomainObject);
    }

    @Operation(summary = "Update a domain object", description = "Update an existing domain object",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DomainObject.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping("/{id}")
    public ResponseEntity<DomainObject> updateDomainObject(@PathVariable UUID id, @Valid @RequestBody DomainObject domainObject) {
        DomainObject updatedDomainObject = domainObjectService.updateDomainObject(id, domainObject);
        return updatedDomainObject != null ? ResponseEntity.ok(updatedDomainObject) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Delete a domain object", description = "Delete a domain object by ID",
            responses = {@ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomainObject(@PathVariable UUID id) {
        boolean deleted = domainObjectService.deleteDomainObject(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Add an attribute to a domain object", description = "Add a new attribute to a domain object",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DomainObjectAttribute.class)))})
    @PostMapping("/{domainObjectId}/attributes")
    public ResponseEntity<DomainObjectAttribute> addAttributeToDomainObject(
            @PathVariable UUID domainObjectId,
            @Valid @RequestBody DomainObjectAttribute domainObjectAttribute) {
        DomainObjectAttribute createdAttribute = domainObjectService.addAttributeToDomainObject(domainObjectId, domainObjectAttribute);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttribute);
    }

    @Operation(summary = "Get attributes of a domain object", description = "Retrieve all attributes for a specific domain object",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DomainObjectAttribute.class)))})
    @GetMapping("/{domainObjectId}/attributes")
    public ResponseEntity<List<DomainObjectAttribute>> getAttributesForDomainObject(@PathVariable UUID domainObjectId) {
        List<DomainObjectAttribute> attributes = domainObjectService.getAttributesForDomainObject(domainObjectId);
        return ResponseEntity.ok(attributes);
    }

    @Operation(summary = "Delete an attribute from a domain object", description = "Delete a specific attribute from a domain object",
            responses = {@ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found")})
    @DeleteMapping("/{domainObjectId}/attributes/{attributeId}")
    public ResponseEntity<Void> deleteAttributeFromDomainObject(
            @PathVariable UUID domainObjectId, @PathVariable UUID attributeId) {
        boolean deleted = domainObjectService.deleteAttributeFromDomainObject(domainObjectId, attributeId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
