package com.youjun.api.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN;

/**
 * <p>
 *  JsonNodeTypeHandler
 * </p>
 *
 * @author kirk
 * @since 2021/5/20
 */
@MappedTypes({JsonNode.class})
public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {
    private static final Logger log = LoggerFactory.getLogger(JsonNodeTypeHandler.class);

    private static final PGobject jsonObject = new PGobject();
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()).registerModule(new ParameterNamesModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(WRITE_BIGDECIMAL_AS_PLAIN);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode jsonNode, JdbcType jdbcType) throws SQLException {
        if (ps != null) {
            try {
                jsonObject.setType("jsonb");
                jsonObject.setValue(objectMapper.writeValueAsString(jsonNode));
                ps.setObject(i, jsonObject, Types.OTHER);
            } catch (JsonProcessingException e) {
                log.error("JsonbTypeHandler set error");
                e.printStackTrace();
                ps.setNull(i, Types.OTHER);
            }
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object o = rs.getObject(columnName);
        if (o == null) {
            return objectMapper.createObjectNode();
        } else {
            if (o instanceof PGobject && ((PGobject) o).getValue() != null) {
                try {
                    return objectMapper.readValue(((PGobject) o).getValue(),  JsonNode.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (o instanceof String) {
                try {
                    return objectMapper.readValue((String) o,  JsonNode.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return objectMapper.createObjectNode();
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object o = rs.getObject(columnIndex);
        if (o == null) {
            return objectMapper.createObjectNode();
        } else {
            if (o instanceof PGobject && ((PGobject) o).getValue() != null) {
                try {
                    return objectMapper.readValue(((PGobject) o).getValue(), JsonNode.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (o instanceof String) {
                try {
                    return objectMapper.readValue((String) o, JsonNode.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return objectMapper.createObjectNode();
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object o = cs.getObject(columnIndex);
        if (o == null) {
            return objectMapper.createObjectNode();
        } else {
            if (o instanceof PGobject && ((PGobject) o).getValue() != null) {
                try {
                    return objectMapper.readValue(((PGobject) o).getValue(),  JsonNode.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (o instanceof String) {
                try {
                    return objectMapper.readValue((String) o,  JsonNode.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return objectMapper.createObjectNode();
    }

}

