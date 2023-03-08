package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс репозиторий для работы с киносеансами.
 */
@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    /**
     * поле экземпляр {@link Sql2o} для работы с базой данных.
     */
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Метод выполняет поиск киносеанса в базе данных по id
     * @param id id киносеанса
     * @return возвращает киносеанс
     */
    @Override
    public Optional<FilmSession> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            FilmSession filmSession = query.setColumnMappings(
                    FilmSession.COLUMN_MAPPING).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }

    /**
     * Метод выполняет поиск всех киносеансов в базе данных.
     * @return коллекцию киносеансов
     */
    @Override
    public Collection<FilmSession> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        }
    }
}
