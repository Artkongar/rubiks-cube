package com.artysh.rubikscube.service.service;

import com.artysh.rubikscube.core.MainKube;
import com.artysh.rubikscube.dto.CubeColorDto;
import com.artysh.rubikscube.dto.CubeSidesDto;
import com.artysh.rubikscube.dto.KubeRotateDto;
import com.artysh.rubikscube.dto.enums.Color;
import com.artysh.rubikscube.dto.enums.RotateDirection;
import com.artysh.rubikscube.service.mapper.GameMapper;
import com.artysh.rubikscube.service.mapper.HistoryMapper;
import com.artysh.rubikscube.service.model.Game;
import com.artysh.rubikscube.service.model.History;
import com.artysh.rubikscube.service.service.game.GameService;
import com.artysh.rubikscube.service.service.history.HistoryService;
import com.artysh.rubikscube.service.utils.jackson.JacksonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class KubeService {

    private final Map<UUID, MainKube> games = new HashMap<>();
    private final GameService gameService;
    private final HistoryService historyService;
    private final GameMapper gameMapper;
    private final HistoryMapper historyMapper;

    public Game createGame(CubeSidesDto cubeCreateDto) {
        int cubeSize = cubeCreateDto.getSize();
        MainKube mainKube = new MainKube(cubeSize);

        Game game = gameMapper.cubeSidesDtoToGame(cubeSize, mainKube.getAllColoredSide());
        games.put(game.getId(), mainKube);
        return gameService.saveGame(game);
    }

    public CubeSidesDto rotateKube(UUID gameId, KubeRotateDto dto) {
        MainKube kube = games.get(gameId);
        kube.rotateKube(dto.getX(), dto.getY(), dto.getZ(), dto.getDirection());
        Map<Color, List<CubeColorDto>> sides = kube.getAllColoredSide();

        Game game = gameService.getById(gameId);
        History lastHistory = historyService.getLastHistoryByGameId(gameId);
        int version;
        if (lastHistory != null) {
            version = lastHistory.getVersion() + 1;
        } else {
            version = 1;
            List<History> histories = game.getHistories();
            if (histories == null) {
                game.setHistories(new ArrayList<>());
            }
        }
        History newHistory = historyMapper.historyFromCubeRotateDto(dto, version);
        newHistory.setGame(game);
        game.getHistories().add(newHistory);

        game.setGreenSide(JacksonUtils.toArrayNode(sides.get(Color.GREEN)));
        game.setRedSide(JacksonUtils.toArrayNode(sides.get(Color.RED)));
        game.setBlueSide(JacksonUtils.toArrayNode(sides.get(Color.BLUE)));
        game.setYellowSide(JacksonUtils.toArrayNode(sides.get(Color.YELLOW)));
        game.setOrangeSide(JacksonUtils.toArrayNode(sides.get(Color.ORANGE)));
        game.setWhiteSide(JacksonUtils.toArrayNode(sides.get(Color.WHITE)));

        gameService.saveGame(game);

        return CubeSidesDto.builder()
                .gameId(gameId)
                .size(kube.getSize())
                .sides(sides)
                .build();
    }

    public boolean isCorrect(UUID gameId) {
        return games.get(gameId).isCorrect();
    }

    public CubeSidesDto scramble(UUID gameId) {
        Random rnd = new Random();
        int scrambleNum = rnd.nextInt(10, 30);

        MainKube cube = games.get(gameId);
        int size = cube.getSize();

        IntStream.range(0, scrambleNum).forEach(i -> {
            RotateDirection[] rotateDirections = RotateDirection.values();
            RotateDirection rotateDirection = rotateDirections[rnd.nextInt(0, rotateDirections.length)];
            int x = rnd.nextInt(0, size);
            int y = rnd.nextInt(0, size);
            int z = rnd.nextInt(0, size);

            KubeRotateDto rotateDto = new KubeRotateDto();
            rotateDto.setDirection(rotateDirection);
            rotateDto.setX(x);
            rotateDto.setY(y);
            rotateDto.setZ(z);

            this.rotateKube(gameId, rotateDto);
        });

        return getKubeSides(gameId);
    }

    public CubeSidesDto getKubeSides(UUID gameId) {
        MainKube mainKube;
        if (games.containsKey(gameId)) {
            mainKube = games.get(gameId);
        } else {
            Game game = gameService.getById(gameId);
            mainKube = new MainKube(game.getSize());

            int version = 1;
            History history = historyService.getHistoryByGameIdAndVersion(gameId, version);
            while (history != null) {
                mainKube.rotateKube(history.getX(), history.getY(), history.getZ(), RotateDirection.valueOf(history.getRotation()));

                version++;
                history = historyService.getHistoryByGameIdAndVersion(gameId, version);
            }
            games.put(game.getId(), mainKube);
        }

        return CubeSidesDto.builder()
                .gameId(gameId)
                .size(mainKube.getSize())
                .sides(mainKube.getAllColoredSide())
                .build();
    }


}
