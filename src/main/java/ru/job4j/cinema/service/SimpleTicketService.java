package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

/**
 * Класс сервис для работы с билетами
 */
@ThreadSafe
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
}
