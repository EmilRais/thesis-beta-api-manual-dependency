package dk.developer.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Converter {
    private static final Converter INSTANCE = new Converter(new ObjectMapper());
    private final ObjectMapper mapper;

    public static Converter converter() {
        return INSTANCE;
    }

    private Converter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T fromJson(String json, Class<T> type) throws RuntimeException {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, TypeReference<T> nestedType) throws RuntimeException {
        try {
            return mapper.readValue(json, nestedType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object object) throws RuntimeException {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T convert(Object object, Class<T> type) throws IllegalArgumentException {
        return mapper.convertValue(object, type);
    }

    public <T> T convert(Object object, TypeReference<T> nestedType) throws IllegalArgumentException {
        return mapper.convertValue(object, nestedType);
    }
}
