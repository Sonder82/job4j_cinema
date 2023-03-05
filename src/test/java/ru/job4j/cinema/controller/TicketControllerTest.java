package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private TicketService ticketService;

    private FilmSessionService filmSessionService;

    private HallService hallService;

    private TicketController ticketController;


    @BeforeEach
    public void initServices() {
        ticketService = mock(TicketService.class);
        filmSessionService = mock(FilmSessionService.class);
        hallService = mock(HallService.class);
        ticketController = new TicketController(ticketService, filmSessionService, hallService);
    }

    @Test
    public void whenPostTicketThenGetPageWithSuccess() {
        var ticket = new Ticket(1, 1, 10, 10, 1);
        ArgumentCaptor<Ticket> ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        String view = ticketController.createTicket(ticket, model);
        var actualTicket = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).isEqualTo("Вы успешно приобрели билет на 10 ряд 10 место.");
    }

    @Test
    public void whenPostTicketAtSamePlaceThenGetPageWithUnSuccess() {
        var ticket1 = new Ticket(1, 1, 10, 10, 1);
        var expectedMessage = "Не удалось приобрести билет на заданное место."
                + "Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова";
        ArgumentCaptor<Ticket> ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        String view = ticketController.createTicket(ticket1, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/unsuccessful");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenPostTicketThenRedirectToErrorPage() {
        var expectedException = new RuntimeException("Failed to write file");
        when(ticketService.save(any(Ticket.class))).thenThrow(expectedException);

        var model = new ConcurrentModel();
        String view = ticketController.createTicket(new Ticket(), model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}