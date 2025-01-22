package org.actics.customer.profiling.model;

import java.util.Objects;
import java.util.UUID;

public class DomainObjectAttribute {

    private UUID id;
    private DomainObject domainObject;
    private String attributeName;
    private String attributeValue;

    // Standard-Konstruktor
    public DomainObjectAttribute() {}

    // Konstruktor mit Feldern
    public DomainObjectAttribute(UUID id, DomainObject domainObject, String attributeName, String attributeValue) {
        this.id = id;
        this.domainObject = domainObject;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    // Getter und Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DomainObject getDomainObject() {
        return domainObject;
    }

    public void setDomainObject(DomainObject domainObject) {
        this.domainObject = domainObject;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    // equals-Methode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DomainObjectAttribute that = (DomainObjectAttribute) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(domainObject, that.domainObject) &&
                Objects.equals(attributeName, that.attributeName) &&
                Objects.equals(attributeValue, that.attributeValue);
    }

    // hashCode-Methode
    @Override
    public int hashCode() {
        return Objects.hash(id, domainObject, attributeName, attributeValue);
    }

    // toString-Methode
    @Override
    public String toString() {
        return "DomainObjectAttribute{" +
                "id=" + id +
                ", domainObject=" + domainObject +
                ", attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                '}';
    }
}
