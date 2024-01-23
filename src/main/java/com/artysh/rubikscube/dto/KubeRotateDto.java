package com.artysh.rubikscube.dto;

import com.artysh.rubikscube.enums.RotateDirection;
import lombok.Data;

@Data
public class KubeRotateDto {

    private int x;
    private int y;
    private int z;
    private RotateDirection direction;

}
