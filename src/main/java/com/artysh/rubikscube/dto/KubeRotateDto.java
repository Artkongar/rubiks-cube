package com.artysh.rubikscube.dto;

import com.artysh.rubikscube.enums.RotateDirection;
import lombok.Data;

@Data
public class KubeRotateDto extends Coordinates{

    private RotateDirection direction;

}
