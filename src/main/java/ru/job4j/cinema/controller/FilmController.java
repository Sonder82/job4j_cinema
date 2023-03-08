package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;

/**
 * Клаcc контроллер для работы с фильмами
 */
@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;


    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * Метод используется для вывода информации о всех фильмах
     * @param model {@link Model}
     * @return отображение информации о всех фильмах
     */
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films/list";
    }

    @GetMapping("/{id}")
    public String getInfoFilmPage(Model model, @PathVariable int id) {
        var filmOptional = filmService.findById(id);
        if (filmOptional.isEmpty()) {
            model.addAttribute("message", "Фильм не найден");
            return "errors/404";
        }
        model.addAttribute("film", filmOptional.get());
        return "films/info";
    }
}

