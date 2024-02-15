package com.artysh.rubikscube.service.usertypes;

import com.artysh.rubikscube.service.utils.jackson.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class JsonbTypes implements UserType<ArrayNode> {

    private final ObjectMapper mapper = JacksonUtils.getMapper();

    @Override
    public ArrayNode nullSafeGet(
            ResultSet resultSet,
            int index,
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object object
    ) throws SQLException {
        String value = resultSet.getString(index);
        if (value != null && !value.equals("\"null\"")) {
            try {
                mapper.readValue(value, ArrayNode.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void nullSafeSet(
            PreparedStatement preparedStatement,
            ArrayNode object,
            int index,
            SharedSessionContractImplementor sharedSessionContractImplementor
    ) {
        int type = Types.OTHER;
        if (object == null) {
            preparedStatement.setNull(index, type);
            return;
        }
        String jsonString = mapper.writeValueAsString(object);
        preparedStatement.setObject(index, jsonString, type);
    }

    @SneakyThrows
    @Override
    public ArrayNode deepCopy(ArrayNode o) {
        return mapper.readValue(
                mapper.writeValueAsString(o), ArrayNode.class
        );
    }

    @Override
    public int getSqlType() {
        return Types.JAVA_OBJECT;
    }

    @Override
    public Class<ArrayNode> returnedClass() {
        return ArrayNode.class;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public boolean equals(ArrayNode o1, ArrayNode o2) {
        return ObjectUtils.nullSafeEquals(o1, o2);
    }

    @Override
    public int hashCode(ArrayNode o) {
        return Objects.hash(o);
    }

    @Override
    public ArrayNode replace(ArrayNode jsonNodes, ArrayNode j1, Object o) {
        return deepCopy(jsonNodes);
    }

    @Override
    public Serializable disassemble(ArrayNode o) {
        return null;
    }

    @Override
    public ArrayNode assemble(Serializable serializable, Object o) {
        return null;
    }

}
