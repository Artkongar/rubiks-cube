package com.artysh.rubikscube.controller;

import com.artysh.rubikscube.dto.CreateGameDto;
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
    public CreateGameDto initKube(@PathVariable Long cubeSize) {
        UUID gameId = kubeService.createGame(cubeSize.intValue());
        return CreateGameDto.builder()
                .gameId(gameId)
                .build();
    }

    @PostMapping(
            value = "/{cubeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CreateGameDto rotateKube(@PathVariable UUID cubeId, @RequestBody KubeRotateDto dto) {
        UUID gameId = kubeService.rotateKube(cubeId, dto);
        return CreateGameDto.builder()
                .gameId(gameId)
                .build();
    }

    @GetMapping(value = "/{gameId}/check",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean checkCorrect(@PathVariable UUID gameId) {
        return kubeService.isCorrect(gameId);
    }

}
