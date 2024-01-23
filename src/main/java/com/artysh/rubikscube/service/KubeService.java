package com.artysh.rubikscube.service;

import com.artysh.rubikscube.dto.KubeRotateDto;
import com.artysh.rubikscube.engine.MainKube;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KubeService {

    private final Map<UUID, MainKube> games = new HashMap<>();

    public UUID createGame(int cubeSize) {
        MainKube mainKube = new MainKube(cubeSize);
        UUID gameId = UUID.randomUUID();
        games.put(gameId, mainKube);
        return gameId;
    }

    public UUID rotateKube(UUID gameId, KubeRotateDto dto) {
        MainKube kube = games.get(gameId);
        kube.rotateKube(dto.getX(), dto.getY(), dto.getZ(), dto.getDirection());
        return gameId;
    }

    public boolean isCorrect(UUID gameId) {
        return games.get(gameId).isCorrect();
    }

}
