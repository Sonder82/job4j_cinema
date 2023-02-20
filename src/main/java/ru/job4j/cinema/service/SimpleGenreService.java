package ru.job4j.cinema.service;


import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс сервис для работы с жанрами
 */
@Service
public class SimpleGenreService implements GenreService {

    /**
     * поле экземпляр {@link GenreRepository} .
     */
    private final GenreRepository genreRepository;

    public SimpleGenreService(GenreRepository sql2oGenreRepository) {
        this.genreRepository = sql2oGenreRepository;
    }

    /**
     * Метод выполняет поиск жанра фильма по id
     * @param id id жанра
     * @return жанр фильма, обернутый в Optional
     */
    @Override
    public Optional<Genre> findById(int id) {
        return genreRepository.findById(id);
    }

    /**
     * Метод выполняет поиск всех жанров фильма
     * @return коллекцию жанров, обернутых в Optional
     */
    @Override
    public Collection<Genre> findAll() {
        return genreRepository.findAll();
    }
}
