package com.artysh.rubikscube.service.mapper;

import com.artysh.rubikscube.dto.KubeRotateDto;
import com.artysh.rubikscube.service.model.History;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface HistoryMapper {

    @Mapping(target = "rotation", expression = "java(dto.getDirection().toString())")
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    History historyFromCubeRotateDto(KubeRotateDto dto, Integer version);

}
