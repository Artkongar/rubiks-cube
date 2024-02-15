package com.artysh.rubikscube.dto;

import com.artysh.rubikscube.dto.enums.Color;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CubeColorDto {

    private Color color;

    private Coordinates coordinates;

}
