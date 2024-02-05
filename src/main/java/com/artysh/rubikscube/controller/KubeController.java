package com.artysh.rubikscube.controller;

import com.artysh.rubikscube.dto.CubeSidesDto;
import com.artysh.rubikscube.dto.KubeRotateDto;
import com.artysh.rubikscube.service.KubeService;
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
        return kubeService.createGame(cubeSize.intValue());
    }

    @PostMapping(
            value = "/{cubeId}/rotate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CubeSidesDto rotateKube(@PathVariable UUID cubeId, @RequestBody KubeRotateDto dto) {
        return kubeService.rotateKube(cubeId, dto);
    }

    @PostMapping(
            value = "/{cubeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CubeSidesDto getKubeSides(@PathVariable UUID cubeId) {
        return kubeService.getKubeSides(cubeId);
    }

    @GetMapping(value = "/{gameId}/check",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean checkCorrect(@PathVariable UUID gameId) {
        return kubeService.isCorrect(gameId);
    }

}
