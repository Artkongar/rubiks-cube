package com.artysh.rubikscube.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Color {

    RED(1),
    WHITE(2),
    ORANGE(3),
    YELLOW(4),
    BLUE(5),
    GREEN(6);

    @Getter
    private int sideNumber;
}
