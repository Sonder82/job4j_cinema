package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmSessionRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс сервис для работы с киносеансами
 */
@ThreadSafe
@Service
public class SimpleFilmSessionService implements FilmSessionService {

    /**
     * поле хранилище киносеансов
     */
    private FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oSessionRepository) {
        this.filmSessionRepository = sql2oSessionRepository;
    }

    /**
     * Метод выполняет поиск киносеанса по id
     * @param id id киносеанса
     * @return киносеанс обернутый в Optional
     */
    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    /**
     * Метод возвращает коллекцию киносеансов.
     * @return коллекцию киносеансов, обернутую в Optional
     */
    @Override
    public Collection<FilmSession> findAll() {
        return filmSessionRepository.findAll();
    }
}
