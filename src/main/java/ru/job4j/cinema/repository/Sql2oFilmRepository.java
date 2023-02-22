package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс репозиторий для работы с фильмами в базе данных
 */
@ThreadSafe
@Repository
public class Sql2oFilmRepository implements FilmRepository {

    /**
     * поле экземпляр {@link Sql2o} для работы с базой данных.
     */
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Метод выполняет поиск фильма по id.
     *
     * @param id id фильма
     * @return фильм
     */
    @Override
    public Optional<Film> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            query.addParameter("id", id);
            Film film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    /**
     * Метод выполняет поиск всех фильмов
     *
     * @return коллекцию фильмов
     */
    @Override
    public Collection<Film> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
