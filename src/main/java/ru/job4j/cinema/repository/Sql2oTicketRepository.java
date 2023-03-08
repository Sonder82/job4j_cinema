package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

/**
 * Класс репозиторий для работы с билетами.
 */
@Repository
public class Sql2oTicketRepository implements TicketRepository {

    /**
     * поле экземпляр {@link Sql2o} для работы с базой данных.
     */
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Метод выполняет запись билета {@link Ticket} в базу данных
     *
     * @param ticket билет
     * @return сохраненный билет в базе данных
     */
    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO tickets(session_id, row_number, place_number, user_id)
                    VALUES (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;
            Query query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
        } catch (Sql2oException exception) {
            System.out.println("Error message: " + exception.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Метод выполняет поиск билета в базе данных по id киносеанса, ряду и месту в зале.
     *
     * @param sessionId   id сеанса
     * @param rowNumber   ряд
     * @param placeNumber место
     * @return возвращает билет
     */
    @Override
    public Optional<Ticket> findBySessionIdRowAndPlace(int sessionId, int rowNumber, int placeNumber) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(
                    "SELECT * FROM tickets WHERE session_id = :sessionId AND row_number = :rowNumber AND place_number = :placeNumber");
            query.addParameter("sessionId", sessionId);
            query.addParameter("rowNumber", rowNumber);
            query.addParameter("placeNumber", placeNumber);
            Ticket ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }

    }
}
