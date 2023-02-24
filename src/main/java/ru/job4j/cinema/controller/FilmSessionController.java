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

    /**
     * Метод извлекает киносеанс из репозитория и возвращает на страницу покупки билета
     *
     * @param model объект Model. Он используется Thymeleaf для поиска объектов, которые нужны отобразить на виде.
     * @param id    id киносеанса
     * @return строку с ошибкой или страницу с выбором киносеанса
     */
    @GetMapping("/{id}")
    public String getByIdFilmSession(Model model, @PathVariable int id) {
        Optional<FilmSessionDto> filmSessionOptional = filmSessionService.findById(id);
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("message", "Киносеанс с указанным идентификатором не найден");
            return "errors/404";
        }
        var filmSession = filmSessionOptional.get();
        model.addAttribute("filmSessions", filmSession);
        return "redirect:/index";
    }
}
