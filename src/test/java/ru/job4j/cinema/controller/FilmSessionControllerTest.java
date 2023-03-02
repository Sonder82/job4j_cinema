package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {

    private FilmSessionService filmSessionService;

    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }
    @Test
    void whenRequestFilmSessionListPageThenGetPageWithFilmSessions() {
        var filmSession1 = new FilmSessionDto(1, "film1", "hall1",
                Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00",
                        2023, 2, 17)), Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",
                2023, 2, 17)), 100, 1);
        var filmSession2 = new FilmSessionDto(2, "film2", "hall2",
                Timestamp.valueOf(String.format("%04d-%02d-%02d 02:00:00",
                        2023, 2, 17)), Timestamp.valueOf(String.format("%04d-%02d-%02d 03:00:00",
                2023, 2, 17)), 100, 2);
        List<FilmSessionDto> expectedFilmSessions = List.of(filmSession1, filmSession2);
        when(filmSessionService.findAll()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        String view = filmSessionController.getFilmSessions(model);
        var actualFilmSessions = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("filmSessions/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }
}