package com.artysh.rubikscube.service.utils.jackson.serializer;

import com.artysh.rubikscube.dto.Coordinates;
import com.artysh.rubikscube.dto.CubeColorDto;
import com.artysh.rubikscube.dto.enums.Color;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CubeColorSerializer extends JsonSerializer<CubeColorDto> {

    @Override
    public void serialize(CubeColorDto cubeColorDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Color cubeColor = cubeColorDto.getColor();
        Coordinates coordinates = cubeColorDto.getCoordinates();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("color", cubeColor.toString());
        jsonGenerator.writeNumberField("x", coordinates.getX());
        jsonGenerator.writeNumberField("y", coordinates.getY());
        jsonGenerator.writeNumberField("z", coordinates.getZ());
        jsonGenerator.writeEndObject();
    }

}
