package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс репозиторий для работы с жанрами.
 */
@ThreadSafe
@Repository
public class Sql2oGenreRepository implements GenreRepository {

    /**
     * поле экземпляр {@link Sql2o} для работы с базой данных.
     */
    private Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Метод выполняет поиск жанра в базе данных по id
     * @param id id жанра
     * @return возвращает жанр
     */
    @Override
    public Optional<Genre> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM genres WHERE id = :id");
            Genre genre = query.addParameter("id", id).executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }

    /**
     * Метод выполняет поиск всех жанров в базе данных.
     * @return коллекцию жанров
     */
    @Override
    public Collection<Genre> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM genres");
            return query.executeAndFetch(Genre.class);
        }
    }
}
