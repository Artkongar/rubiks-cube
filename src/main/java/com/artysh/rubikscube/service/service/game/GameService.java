package com.artysh.rubikscube.service.service.game;

import com.artysh.rubikscube.service.model.Game;
import com.artysh.rubikscube.service.repositor.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public Game getById(UUID id) {
        return gameRepository.findById(id).orElse(null);
    }
}
