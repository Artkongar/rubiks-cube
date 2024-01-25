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

    public CubeSidesDto createGame(int cubeSize) {
        MainKube mainKube = new MainKube(cubeSize);
        UUID gameId = UUID.randomUUID();
        games.put(gameId, mainKube);
        Map<Integer, List<Color>> sides = mainKube.getAllColoredSide();
        return CubeSidesDto.builder()
                .gameId(gameId)
                .sides(sides)
                .build();
    }

    public CubeSidesDto rotateKube(UUID gameId, KubeRotateDto dto) {
        MainKube kube = games.get(gameId);
        kube.rotateKube(dto.getX(), dto.getY(), dto.getZ(), dto.getDirection());
        Map<Integer, List<Color>> sides = kube.getAllColoredSide();
        return CubeSidesDto.builder()
                .gameId(gameId)
                .sides(sides)
                .build();
    }

    public boolean isCorrect(UUID gameId) {
        return games.get(gameId).isCorrect();
    }

}
