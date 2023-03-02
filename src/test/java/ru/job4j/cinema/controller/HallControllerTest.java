package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.HallService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HallControllerTest {

    private HallService hallService;

    private HallController hallController;


    @BeforeEach
    public void initServices() {
        hallService = mock(HallService.class);
        hallController = new HallController(hallService);
    }
    @Test
    public void whenRequestIdThenGetPageWithHall() {
        int searchId = 1;
        var hall = new Hall(1, "test1", 10, 10, "desc1");
        when(hallService.findById(searchId)).thenReturn(Optional.of(hall));

        var model = new ConcurrentModel();
        String view = hallController.getById(model, searchId);
        var actualHall = model.getAttribute("halls");

        assertThat(view).isEqualTo("halls/info");
        assertThat(actualHall).isEqualTo(hall);
    }

    @Test
    public void whenRequestIdThenGetPageWithError() {
        int notExistID = 2;
        var expectedMessage = "Кинозал с указанным идентификатором не найден";
        when(hallService.findById(notExistID)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        String view = hallController.getById(model, notExistID);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}