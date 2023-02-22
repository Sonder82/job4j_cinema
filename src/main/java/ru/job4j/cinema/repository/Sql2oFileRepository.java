package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Optional;

/**
 * Класс репозиторий для работы с файлами в базе данных.
 */

@Repository
public class Sql2oFileRepository implements FileRepository {

    /**
     * поле экземпляр {@link Sql2o} для работы с базой данных.
     */
    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Метод выполняет поиск файла по id  в базе данных
     * @param id id файла
     * @return файл
     */
    @Override
    public Optional<File> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM files WHERE id = :id");
            File file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }
}
