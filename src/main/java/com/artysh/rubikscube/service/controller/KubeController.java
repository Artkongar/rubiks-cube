package com.artysh.rubikscube.service.controller;

import com.artysh.rubikscube.dto.CubeSidesDto;
import com.artysh.rubikscube.dto.KubeRotateDto;
import com.artysh.rubikscube.service.model.Game;
import com.artysh.rubikscube.service.service.KubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/kube")
@RequiredArgsConstructor
public class KubeController {

    private final KubeService kubeService;

    @GetMapping(value = "/{cubeSize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UUID initKube(@PathVariable Long cubeSize) {
        Game game = kubeService.createGame(
                CubeSidesDto.builder()
                        .size(cubeSize.intValue())
                        .build()
        );
        return game.getId();
    }

    @PostMapping(
            value = "/{gameId}/rotate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CubeSidesDto rotateKube(@PathVariable UUID gameId, @RequestBody KubeRotateDto dto) {
        return kubeService.rotateKube(gameId, dto);
    }

    @PostMapping(
            value = "/{gameId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CubeSidesDto getKubeSides(@PathVariable UUID gameId) {
        return kubeService.getKubeSides(gameId);
    }

    @GetMapping(value = "/{gameId}/check",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean checkCorrect(@PathVariable UUID gameId) {
        return kubeService.isCorrect(gameId);
    }

    @GetMapping(value = "/{gameId}/scramble",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CubeSidesDto scramble(@PathVariable UUID gameId) {
        return kubeService.scramble(gameId);
    }

}
