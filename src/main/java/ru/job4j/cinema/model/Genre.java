package ru.job4j.cinema.model;

/**
 * Класс модель жанр
 */
public class Genre {
    /**
     * поле id
     */
    private int id;
    /**
     * поле название жанра
     */
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
