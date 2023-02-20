package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс сервис для работы с кинозалами
 */
@ThreadSafe
@Service
public class SimpleHallService implements HallService {

    /**
     * поле хранилище кинозалов
     */
    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    /**
     * Метод выполняет поиск кинозала по id
     * @param id id кинозала
     * @return кинозал, обернутый в Optional
     */
    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }

    /**
     * Метод выполняет поиск всех кинозалов
     * @return возвращает коллекцию кинозалов, обернутую в Optional
     */
    @Override
    public Collection<Hall> findAll() {
        return hallRepository.findAll();
    }
}
