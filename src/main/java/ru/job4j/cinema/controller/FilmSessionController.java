package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;

import java.util.Optional;

/**
 * Класс контроллер для работы с киносеансами
 */
@ThreadSafe
@Controller
@RequestMapping("/filmSessions")
public class FilmSessionController {

    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    /**
     * Метод выполняет вывод представления всех киносеансов и связанных с ним фильмов.
     * @param model {@link Model}
     * @return отображение всех киносеансов
     */
    @GetMapping
    public String getFilmSessions(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "filmSessions/list";
    }

}
