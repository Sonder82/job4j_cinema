package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс сервис для работы с фильмами
 */
@ThreadSafe
@Service
public class SimpleFilmService implements FilmService {

    /**
     * поле хранилище фильмов
     */
    private final FilmRepository filmRepository;

    /**
     * поле хранилище жанров
     */
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository genreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * Метод используется для поиска фильма по id.
     * После того как фильм будет найден, он будет преoбразован в DTO{@link FilmDto}
     * @param id id фильма
     * @return возвращает FilmDTO/
     */
    @Override
    public Optional<FilmDto> findById(int id) {
       Optional<Film> filmOptional = filmRepository.findById(id);
       if (filmOptional.isEmpty()) {
           return Optional.empty();
       }
       var film = filmOptional.get();
        return Optional.of(convertToDto(film));
    }

    /**
     * Метод выполняет поиск названия жанра в фильме,
     * для дальнейшей передачи в FilmDTO.
     * @param film фильм
     * @return название жанра
     */
    private String foundGenreName(Film film) {
        String message = "Данный жанр отсутствует";
        var optionalGenre = genreRepository.findById(film.getGenreId());
        if (optionalGenre.isEmpty()) {
            return message;
        }
        return optionalGenre.get().getName();
    }

    /**
     * Метод используется для преобразования фильма {@link Film}
     * в FilmDTO {@link FilmDto}
     * @param film фильм
     * @return возвращает FilmDTO {@link FilmDto}
     */
    private FilmDto convertToDto(Film film) {
        return new FilmDto(film.getId(), film.getName(), film.getDescription(), film.getYear(),
                film.getMinimalAge(), film.getDurationInMinutes(), foundGenreName(film));
    }

    /**
     * Метод выполняет поиск всех фильмов в репозитории фильмов.
     * В дальнейшем происходит преобразование коллекции фильмов
     * в коллекцию FilmDTO.
     * @return коллекцию FilmDTO.
     */
    @Override
    public Collection<FilmDto> findAll() {
        return filmRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
