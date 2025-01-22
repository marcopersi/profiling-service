package org.actics.customer.profiling.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DomainObject {

    private UUID id;
    private String type; // Umbenannt von "name" zu "type", um der Datenbankstruktur zu entsprechen.
    private List<DomainObjectAttribute> attributes;
    private String name;

    // Standard-Konstruktor
    public DomainObject() {}

    // Konstruktor mit Feldern
    public DomainObject(UUID id, String type, String name, List<DomainObjectAttribute> attributes) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.attributes = attributes;
    }

    // Getter und Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String theName) {
        this.name = theName;
    }


    public List<DomainObjectAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DomainObjectAttribute> attributes) {
        this.attributes = attributes;
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
        DomainObject that = (DomainObject) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(attributes, that.attributes);
    }

    // hashCode-Methode
    @Override
    public int hashCode() {
        return Objects.hash(id, type, attributes);
    }

    // toString-Methode
    @Override
    public String toString() {
        return "DomainObject{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
