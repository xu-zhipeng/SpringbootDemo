package com.youjun.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    public static class SerializationException extends RuntimeException {
        SerializationException(String message) {
            super(message);
        }

        SerializationException(Exception e) {
            super(e);
        }
    }

    public static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()).registerModule(new ParameterNamesModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    }

    public static String toJson(Object o) throws SerializationException {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new SerializationException(String.format("Failed to serialize: [%s]", o.toString()));
        }
    }

    public static Map<String, Object> fromJson(String context) throws SerializationException {
        Map<String, Object> resultMap = null;
        if (StringUtils.isNotBlank(context)) {
            JavaType type = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Object.class);
            try {
                resultMap = mapper.readValue(context, type);
            } catch (IOException e) {
                throw new SerializationException(String.format("Failed to decode: [%s]", context));
            }
        }
        return resultMap;
    }

    public static <T> T fromJson(String content, Class<T> valueType) throws SerializationException {
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new SerializationException(String.format("Failed to Deserialize: [%s]", content));
        }
    }

    public static <T> T fromJson(String content, TypeReference<T> valueTypeRef) throws SerializationException {
        try {
            return mapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            throw new SerializationException(String.format("Failed to Deserialize: [%s]", content));
        }
    }

    public static LinkedHashMap<String, Object> fromJsonToLinkedHashMap(String context) throws SerializationException {
        LinkedHashMap<String, Object> resultMap = null;
        if (StringUtils.isNotBlank(context)) {
            JavaType type = mapper.getTypeFactory().constructParametricType(LinkedHashMap.class, String.class, Object.class);
            try {
                resultMap = mapper.readValue(context, type);
            } catch (IOException e) {
                throw new SerializationException(String.format("Failed to Deserialize: [%s]", context));
            }
        }
        return resultMap;
    }

    public static LinkedHashMap<String, Object> fromJsonNodeToLinkedHashMap(JsonNode jsonNode) throws SerializationException {
        LinkedHashMap<String, Object> resultMap = null;
        if (Objects.nonNull(jsonNode)) {
            JavaType type = mapper.getTypeFactory().constructParametricType(LinkedHashMap.class, String.class, Object.class);
            resultMap = mapper.convertValue(jsonNode, type);
        }
        return resultMap;
    }

    /**
     * 深拷贝
     *
     * @param clazz
     * @param source
     * @param <T>
     * @return
     * @throws SerializationException
     */
    public static <T> T copyProperties(Class<T> clazz, Object source) throws SerializationException {
        String context = toJson(source);
        try {
            return mapper.readValue(context, clazz);
        } catch (IOException e) {
            throw new SerializationException(String.format("Failed to Deserialize: [%s]", context));
        }
    }

    /**
     * 使用示例
     * @param args
     */
    /*public static void main(String[] args) {
        List<BCTUserDTO> result = JsonUtils.fromJson(json, new TypeReference<List<BCTUserDTO>>() {});
    }*/
}
