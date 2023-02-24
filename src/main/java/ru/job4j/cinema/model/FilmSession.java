package ru.job4j.cinema.model;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Класс реализует модель сеанс фильмов
 */
public class FilmSession {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "hall_id", "hallId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    /**
     * поле id
     */
    private int id;

    /**
     * поле id фильма
     */
    private int filmId;

    /**
     * поле id зала
     */
    private int hallId;

    /**
     * поле время начала сеанса
     */
    private Timestamp startTime;

    /**
     * поле время окончания сеанса
     */
    private Timestamp endTime;

    /**
     * поле цена
     */
    private int price;

    public FilmSession() {
    }

    public FilmSession(int id, int filmId, int hallId, Timestamp startTime, Timestamp endTime, int price) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
