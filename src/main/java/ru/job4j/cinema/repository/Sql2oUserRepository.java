package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Класс репозиторий для работы с пользователями в базе данных
 */
@Repository
public class Sql2oUserRepository implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oUserRepository.class.getName());

    /**
     * поле экземпляр {@link Sql2o} для работы с базой данных.
     */
    private Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {

        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO users(full_name, email, password)
                    VALUES (:fullName, :email, :password)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("fullName", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
        } catch (Sql2oException exception) {
            LOG.error("Error message: " + exception.getMessage());
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM users WHERE email = :email AND password = :password");
            query.addParameter("email", email);
            query.addParameter("password", password);
            User user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}
