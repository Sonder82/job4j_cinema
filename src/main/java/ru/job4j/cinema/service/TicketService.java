package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findBySessionIdRowAndPlace(int filmSessionId, int row, int place);

}
