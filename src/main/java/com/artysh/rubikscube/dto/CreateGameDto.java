package com.artysh.rubikscube.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateGameDto {

    private UUID gameId;
}
