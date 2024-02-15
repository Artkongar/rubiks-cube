package com.artysh.rubikscube.service.mapper;

import com.artysh.rubikscube.dto.CubeColorDto;
import com.artysh.rubikscube.dto.enums.Color;
import com.artysh.rubikscube.service.model.Game;
import com.artysh.rubikscube.service.utils.jackson.JacksonUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {JacksonUtils.class, Color.class, UUID.class})
public interface GameMapper {

    @Mapping(target = "redSide", expression = "java(JacksonUtils.toArrayNode(cubeSides.get(Color.RED)))")
    @Mapping(target = "whiteSide", expression = "java(JacksonUtils.toArrayNode(cubeSides.get(Color.WHITE)))")
    @Mapping(target = "greenSide", expression = "java(JacksonUtils.toArrayNode(cubeSides.get(Color.GREEN)))")
    @Mapping(target = "yellowSide", expression = "java(JacksonUtils.toArrayNode(cubeSides.get(Color.YELLOW)))")
    @Mapping(target = "blueSide", expression = "java(JacksonUtils.toArrayNode(cubeSides.get(Color.BLUE)))")
    @Mapping(target = "orangeSide", expression = "java(JacksonUtils.toArrayNode(cubeSides.get(Color.ORANGE)))")
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    Game cubeSidesDtoToGame(Integer size, Map<Color, List<CubeColorDto>> cubeSides);

}
