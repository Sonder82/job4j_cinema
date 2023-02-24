package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.HallService;

import java.util.Optional;

@Controller
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }
    /**
     * Метод извлекает кинозал из репозитория и возвращает его описание на страницу
     *
     * @param model объект Model. Он используется Thymeleaf для поиска объектов, которые нужны отобразить на виде.
     * @param id    id киносеанса
     * @return строку с ошибкой или страницу с описанием кинозала
     */
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Hall> hallOptional = hallService.findById(id);
        if (hallOptional.isEmpty()) {
            model.addAttribute("message", "Кинозал с указанным идентификатором не найден");
            return "errors/404";
        }
        var hall = hallOptional.get();
        model.addAttribute("halls", hall);
        return "halls/info";
    }
}
