package com.artysh.rubikscube.service.utils.jackson.serializer;

import com.artysh.rubikscube.dto.CubeColorDto;
import com.artysh.rubikscube.dto.CubeSidesDto;
import com.artysh.rubikscube.dto.enums.Color;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CubeSidesSerializer extends JsonSerializer<CubeSidesDto> {

    @Override
    public void serialize(CubeSidesDto cubeSidesDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonGenerator.getCodec();
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", cubeSidesDto.getGameId().toString());
        jsonGenerator.writeNumberField("size", cubeSidesDto.getSize());

        jsonGenerator.writeFieldName("sides");
        jsonGenerator.writeStartArray();
        for (Map.Entry<Color, List<CubeColorDto>> entry : cubeSidesDto.getSides().entrySet()) {
            Color sideColor = entry.getKey();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("sideColor", sideColor.toString());
            List<CubeColorDto> side = entry.getValue();
            jsonGenerator.writeFieldName("cubes");
            jsonGenerator.writeStartArray();
            for (CubeColorDto colorWithCoordinates : side) {
                jsonGenerator.writeObject(mapper.writeValueAsString(colorWithCoordinates));
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

}
