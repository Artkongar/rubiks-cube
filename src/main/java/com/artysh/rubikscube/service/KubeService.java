package com.artysh.rubikscube.service;

import com.artysh.rubikscube.core.MainKube;
import com.artysh.rubikscube.dto.CubeSidesDto;
import com.artysh.rubikscube.dto.KubeRotateDto;
import com.artysh.rubikscube.enums.Color;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class KubeService {

    private final Map<UUID, MainKube> games = new HashMap<>();

    public UUID createGame(int cubeSize) {
        MainKube mainKube = new MainKube(cubeSize);
        UUID gameId = UUID.randomUUID();
        games.put(gameId, mainKube);
        return gameId;
    }

    public CubeSidesDto rotateKube(UUID gameId, KubeRotateDto dto) {
        MainKube kube = games.get(gameId);
        kube.rotateKube(dto.getX(), dto.getY(), dto.getZ(), dto.getDirection());
        Map<Color, List<Color>> sides = kube.getAllColoredSide();
        return CubeSidesDto.builder()
                .gameId(gameId)
                .size(kube.getSize())
                .sides(sides)
                .build();
    }

    public boolean isCorrect(UUID gameId) {
        return games.get(gameId).isCorrect();
    }

    public CubeSidesDto getKubeSides(UUID gameId) {
        MainKube mainKube = games.get(gameId);
        return CubeSidesDto.builder()
                .gameId(gameId)
                .size(mainKube.getSize())
                .sides(mainKube.getAllColoredSide())
                .build();
    }

}
