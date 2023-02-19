package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс описывает хранение фильмов в памяти сервера
 */
@ThreadSafe
@Repository
public class MemoryFilmRepository implements FilmRepository {

    private final AtomicInteger id = new AtomicInteger();

    private Map<Integer, Film> films = new ConcurrentHashMap<>();

    public MemoryFilmRepository() {
        save(new Film(0, "Чебурашка", "Фильм про Чебурашку", 2022, 1, 6, 120, 1));
    }

    /**
     * Метод выполняет сохранение фильма в хранилище
     * @param film {@link Film}
     * @return {@link Film}
     */
    @Override
    public Film save(Film film) {
        film.setId(id.incrementAndGet());
        films.put(film.getId(), film);
        return film;
    }

    /**
     * Метод выполняет удаление фильма по id
     * @param id id фильма
     * @return boolean логику
     */
    @Override
    public boolean deleteById(int id) {
        return films.remove(id) != null;
    }

    /**
     * Метод выполняет обновление фильма
     * @param film {@link Film}
     * @return boolean логику
     */
    @Override
    public boolean update(Film film) {
        return films.computeIfPresent(film.getId(), (id, oldFilm) -> new Film(
                id, film.getName(), film.getDescription(), film.getYear(), film.getGenreId(), film.getMinimalAge(),
                film.getDurationInMinutes(), film.getFileId())) != null;
    }

    /**
     * Метод выполняет поиск фильма по id
     * @param id id фильма
     * @return возвращает фильм при найденном id.
     */
    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    /**
     * Метод выполняет поиск всех фильмов в хранилище
     * @return коллекцию фильмов.
     */
    @Override
    public Collection<Film> findAll() {
        return films.values();
    }
}
