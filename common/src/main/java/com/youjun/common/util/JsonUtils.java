package com.youjun.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.youjun.common.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

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
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        df.setTimeZone(tz);
        mapper = new ObjectMapper();
        //设置 Date格式
        mapper.setDateFormat(df);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                String formattedDate = formatter.format(date);
                jsonGenerator.writeString(formattedDate);
            }
        });
        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                String date = jsonParser.getText();
                try {
                    return format.parse(date);
                } catch (ParseException e) {
                    throw new ApiException("Data 转换失败");
                }
            }
        });
        mapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
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
