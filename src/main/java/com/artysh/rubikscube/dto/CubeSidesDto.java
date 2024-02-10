package com.artysh.rubikscube.dto;

import com.artysh.rubikscube.enums.Color;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class CubeSidesDto {

    private UUID gameId;
    private Integer size;
    private Map<Color, List<Map.Entry<Color, Coordinates>>> sides;

}
