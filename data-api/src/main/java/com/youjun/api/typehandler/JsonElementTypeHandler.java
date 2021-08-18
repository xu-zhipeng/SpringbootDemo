package com.youjun.api.typehandler;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;

/**
 * Map PostgreSQL JSON type to Gson JsonElement does not return null (empty node instead).
 * Just use JSON string representation as intermediate data format.
 *
 * @see JsonElement
 */
@MappedTypes({JsonElement.class, JsonArray.class, JsonObject.class})
public class JsonElementTypeHandler extends BaseTypeHandler<JsonElement> {
    private static final Gson gson = new Gson();
    private static final PGobject jsonObject = new PGobject();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonElement parameter, JdbcType jdbcType) throws SQLException {
        try {
            StringWriter writer = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setLenient(false);
            Streams.write(parameter, jsonWriter);
            jsonObject.setType("jsonb");
            jsonObject.setValue(writer.toString());
            ps.setObject(i, jsonObject, Types.OTHER);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public JsonElement getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object jsonSource = rs.getString(columnName);
        if (jsonSource != null) {
            return fromJson(jsonSource);
        }
        return JsonNull.INSTANCE;
    }

    @Override
    public JsonElement getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object jsonSource = rs.getString(columnIndex);
        if (jsonSource != null) {
            return fromJson(jsonSource);
        }
        return JsonNull.INSTANCE;
    }

    @Override
    public JsonElement getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object jsonSource = cs.getString(columnIndex);
        if (jsonSource != null) {
            return fromJson(jsonSource);
        }
        return JsonNull.INSTANCE;
    }

    private JsonElement fromJson(Object source) {
        if (source instanceof PGobject && ((PGobject) source).getValue() != null) {
            return gson.toJsonTree(((PGobject) source).getValue());
        } else if (source instanceof String) {
            return gson.toJsonTree(source);
        }
        return source == null ? JsonNull.INSTANCE : gson.toJsonTree(source);
    }
}
