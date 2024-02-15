package com.artysh.rubikscube.service.utils.jackson;

import com.artysh.rubikscube.dto.CubeColorDto;
import com.artysh.rubikscube.dto.CubeSidesDto;
import com.artysh.rubikscube.service.utils.jackson.serializer.CubeColorSerializer;
import com.artysh.rubikscube.service.utils.jackson.serializer.CubeSidesSerializer;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonUtils {

    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(CubeSidesDto.class, new CubeSidesSerializer());
        module.addSerializer(CubeColorDto.class, new CubeColorSerializer());
        mapper.registerModule(module);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static ObjectNode toObjectNode(Object object) {
        return mapper.valueToTree(object);
    }

    public static ArrayNode toArrayNode(Object object) {
        return mapper.valueToTree(object);
    }

    public static ObjectReader getReaderByCodec(ObjectCodec codec) {
        if (codec instanceof ObjectReader reader) {
            return reader;
        } else if (codec instanceof JsonMapper jsonMapper) {
            return jsonMapper.reader();
        } else {
            ObjectMapper mapper = (ObjectMapper) codec;
            return mapper.reader();
        }
    }

}
