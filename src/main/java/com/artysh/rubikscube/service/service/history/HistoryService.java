package com.artysh.rubikscube.service.service.history;

import com.artysh.rubikscube.service.model.History;
import com.artysh.rubikscube.service.repositor.HistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryDao historyDao;


    public History getLastHistoryByGameId(UUID gameId) {
        return historyDao.getLastHistoryByGameId(gameId);
    }

    public History getHistoryByGameIdAndVersion(UUID gameId, Integer version) {
        return historyDao.getHistoryByGameIdAndVersion(gameId, version);
    }

}
