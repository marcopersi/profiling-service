package org.actics.customer.profiling.configuration;

import org.jooq.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonbToObjectConverter<T> implements Converter<Object, T> {

    private final Class<T> type;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonbToObjectConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public T from(Object databaseObject) {
        if (databaseObject == null) return null;
        try {
            return objectMapper.readValue(databaseObject.toString(), type);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to deserialize JSONB", e);
        }
    }

    @Override
    public Object to(T userObject) {
        if (userObject == null) return null;
        try {
            return objectMapper.writeValueAsString(userObject);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize object to JSONB", e);
        }
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<T> toType() {
        return type;
    }
}
