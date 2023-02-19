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
     * Метод выполняет сохранение фильма в базу данных
     *
     * @param film фильм
     * @return сохраненный фильм
     */
    @Override
    public Film save(Film film) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
                    VALUES (:name, :description, :year, :genreId, :minimalAge, :durationInMinutes, :fileId)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return film;
        }
    }

    /**
     * Метод выполняет удаление фильма из базы данных по id
     *
     * @param id id фильма
     * @return boolean логику
     */
    @Override
    public boolean deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("DELETE FROM films WHERE id = :id");
            query.addParameter("id", id);
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    /**
     * Метод выполняет обновление данных фильма
     *
     * @param film фильм
     * @return boolean логику
     */
    @Override
    public boolean update(Film film) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    UPDATE films
                    SET name = :name, description = :description, year = :year, genre_id = :genreId,
                    minimal_age = :minimalAge, duration_in_minutes = :durationInMinutes, file_id = :fileId
                    WHERE id = :id
                    """;
            Query query = connection.createQuery(sql)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
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
