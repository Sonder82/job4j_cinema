package ru.job4j.cinema.dto;

import java.sql.Timestamp;

/**
 * Класс DTO для отображения киносеансов
 */
public class FilmSessionDto {
    /**
     * поле id
     */
    private int id;
    /**
     * поле название фильма
     */
    private String filmName;
    /**
     * поле название зала
     */
    private String hallName;
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
    /**
     * поле id кинозала
     */
    private int hallId;

    public FilmSessionDto(int id, String filmName, String hallName, Timestamp startTime,
                          Timestamp endTime, int price, int hallId) {
        this.id = id;
        this.filmName = filmName;
        this.hallName = hallName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.hallId = hallId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
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

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }
}
