package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс сервис для работы с билетами
 */
@Service
public class SimpleTicketService implements TicketService {

    /**
     * поле хранилище билетов
     */
    private final  TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository) {
        this.ticketRepository = sql2oTicketRepository;
    }

    /**
     * Метод выполняет сохранение билета
     * @param ticket билет
     * @return билет, обернутый в Optional
     */
    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     * Метод выполняет поиск билета по сеансу, ряду и месту
     * @param filmSessionId id киносеанса
     * @param row ряд
     * @param place место
     * @return билет, обернутый в Optional
     */
    @Override
    public Optional<Ticket> findBySessionIdRowAndPlace(int filmSessionId, int row, int place) {
        return ticketRepository.findBySessionIdRowAndPlace(filmSessionId, row, place);
    }

    @Override
    public List<Integer> rowList(Optional<Hall> hall) {
        return IntStream.range(1, hall.get().getRowCount()).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Integer> placeList(Optional<Hall> hall) {
        return IntStream.range(1, hall.get().getPlaceCount()).boxed().collect(Collectors.toList());
    }
}
