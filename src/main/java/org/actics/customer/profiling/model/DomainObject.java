package org.actics.customer.profiling.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DomainObject {

    private UUID id;
    private String name;
    private List<DomainObjectAttribute> attributes;

    // Standard-Konstruktor
    public DomainObject() {}

    // Konstruktor mit Feldern
    public DomainObject(UUID id, String name, List<DomainObjectAttribute> attributes) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                Objects.equals(name, that.name) &&
                Objects.equals(attributes, that.attributes);
    }

    // hashCode-Methode
    @Override
    public int hashCode() {
        return Objects.hash(id, name, attributes);
    }

    // toString-Methode
    @Override
    public String toString() {
        return "DomainObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
