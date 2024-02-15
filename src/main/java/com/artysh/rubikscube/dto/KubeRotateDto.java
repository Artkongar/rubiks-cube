package com.artysh.rubikscube.dto;

import com.artysh.rubikscube.dto.enums.RotateDirection;
import lombok.Data;

@Data
public class KubeRotateDto extends Coordinates {

    private RotateDirection direction;

}
