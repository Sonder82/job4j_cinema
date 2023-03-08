package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс сервис для работы с киносеансами
 */
@Service
public class SimpleFilmSessionService implements FilmSessionService {

    /**
     * поле хранилище киносеансов
     */
    private final FilmSessionRepository filmSessionRepository;

    private final FilmRepository filmRepository;

    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    FilmRepository filmRepository, HallRepository hallRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    /**
     * Метод выполняет поиск киносеанса по id
     * @param id id киносеанса
     * @return киносеанс обернутый в Optional
     */
    @Override
    public Optional<FilmSessionDto> findById(int id) {
        Optional<FilmSession> optionalFilmSession = filmSessionRepository.findById(id);
        if (optionalFilmSession.isEmpty()) {
            return Optional.empty();
        }
        var filmSession = optionalFilmSession.get();
        return Optional.of(convertToDto(filmSession));
    }

    /**
     * Метод выполняет поиск названия фильма,
     * для дальнейшей передачи в FilmSessionDTO.
     * @param filmSession киносеанс
     * @return название фильма
     */
    private String filmName(FilmSession filmSession) {
        String message = "Данный фильм отсутствует";
        var filmOptional = filmRepository.findById(filmSession.getFilmId());
        if (filmOptional.isEmpty()) {
            return message;
        }
        return filmOptional.get().getName();
    }

    /**
     * Метод выполняет поиск названия зала,
     * для дальнейшей передачи в FilmSessionDTO.
     * @param filmSession киносеанс
     * @return название зала
     */
    private String hallName(FilmSession filmSession) {
        String message = "Данный зал отсутствует";
        var hallOptional = hallRepository.findById(filmSession.getHallId());
        if (hallOptional.isEmpty()) {
            return message;
        }
        return hallOptional.get().getName();
    }

    /**
     * Метод выполняет конвертацию в {@link FilmSessionDto}
     * @param filmSession киносеанс
     * @return результат конвертации в виде объекта {@link FilmSessionDto}
     */
    private FilmSessionDto convertToDto(FilmSession filmSession) {
        return new FilmSessionDto(filmSession.getId(), filmName(filmSession), hallName(filmSession),
                filmSession.getStartTime(), filmSession.getEndTime(), filmSession.getPrice(), filmSession.getHallId());
    }

    /**
     * Метод возвращает коллекцию киносеансов с использованием DTO {@link FilmSessionDto}.
     * @return коллекцию {@link FilmSessionDto}
     */
    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
