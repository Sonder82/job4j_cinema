package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * Класс контроллер для работы с билетами.
 */
@ThreadSafe
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    private final FilmSessionService filmSessionService;

    private final HallService hallService;

    public TicketController(TicketService ticketService,
                            FilmSessionService filmSessionService, HallService hallService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    /**
     * Метод извлекает киносеанс из репозитория и возвращает на страницу покупки билета
     *
     * @param model объект Model. Он используется Thymeleaf для поиска объектов, которые нужны отобразить на виде.
     * @param id    id киносеанса
     * @return строку с ошибкой или страницу с выбором киносеанса
     */
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        Optional<FilmSessionDto> filmSessionOptional = filmSessionService.findById(id);
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("message", "Киносеанс с указанным идентификатором не найден");
            return "errors/404";
        }

        var ticket = new Ticket();
        ticket.setSessionId(filmSessionOptional.get().getId());
        var user = (User) session.getAttribute("user");
        ticket.setUserId(user.getId());

        var hallOptional = hallService.findById(filmSessionOptional.get().getHallId());
        model.addAttribute("filmSessions", filmSessionOptional.get());
        model.addAttribute("ticket", ticket);
        model.addAttribute("rowCounts", ticketService.rowList(hallOptional));
        model.addAttribute("placeCounts", ticketService.placeList(hallOptional));
        return "tickets/create";
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket, Model model) {
        try {
            var optionalTicket = ticketService.save(ticket);
            if (optionalTicket.isEmpty()) {
                model.addAttribute("message", "Не удалось приобрести билет на заданное место."
                        + "Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова");
                return "tickets/unsuccessful";
            }
            model.addAttribute("message", "Вы успешно приобрели билет на "
                    + ticket.getRowNumber() + "ряд" + ticket.getPlaceNumber() + "место.");
            return "tickets/success";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}
