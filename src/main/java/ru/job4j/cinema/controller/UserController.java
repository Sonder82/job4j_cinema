package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Класс контроллер для работы с пользователями.
 */

@Controller
@RequestMapping("/users")
public class UserController {

    /**
     * Поле {@link UserService} объект класса Сервиса для работы с пользователями
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод служит для отображения страницы с регистрацией пользователя
     *
     * @return возвращает страницу с регистрацией
     */
    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    /**
     * Метод служит для передачи информации о пользователе при регистрации
     * @param model {@link Model}
     * @param user пользователь
     * @return при вводе данных пользователя с уже имеющимся email происходит перенаправление на
     * страницу ошибки. При успешной регистрации перенаправляется на начальную страницу.
     */
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        Optional<User> optionalUser = userService.save(user);
        if (optionalUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "errors/404";
        }
        return "redirect:/index";
    }

    /**
     * Метод служит для отображения страницы ввода данных для входа в аккаунт.
     * @return возвращает страницу с вводом данных
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    /**
     * Метод служит для входа пользователя в свой аккаунт
     * @param model {@link Model}
     * @param user пользователь
     * @return при вводе данных пользователя с уже имеющимся email происходит перенаправление на
     * страницу ошибки. При успешной регистрации перенаправляется на страницу с вакансиями.
     */
    @PostMapping("/login")
    public String loginUser(Model model, @ModelAttribute User user, HttpServletRequest request) {
        var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    /**
     * Метод используется для выхода пользователя из системы.
     * @param session {@link HttpSession}
     * @return возвращает отображение входа пользователя в аккаунт
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
