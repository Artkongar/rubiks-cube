package com.artysh.rubikscube.service.repositor;

import com.artysh.rubikscube.service.model.History;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HistoryDao {

    private final SessionFactory sessionFactory;

    public History getLastHistoryByGameId(UUID id) {
        Session session = sessionFactory.openSession();
        Query<History> query = session.createQuery("""
                select h
                from com.artysh.rubikscube.service.model.History h
                where h.game.id = :gameId
                order by h.version desc
                """,
                History.class
        );
        query.setParameter("gameId", id);
        query.setFetchSize(1);
        History lastHistory = query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return lastHistory;
    }

    public History getHistoryByGameIdAndVersion(UUID gameId, Integer version) {
        Session session = sessionFactory.openSession();
        Query<History> query = session.createQuery("""
                select h
                from com.artysh.rubikscube.service.model.History h
                where h.game.id = :gameId and h.version = :version
                order by h.version desc
                """,
                History.class
        );
        query.setParameter("gameId", gameId);
        query.setParameter("version", version);
        query.setFetchSize(1);
        History history = query.getResultList().stream().findFirst().orElse(null);
        session.close();
        return history;
    }

}
