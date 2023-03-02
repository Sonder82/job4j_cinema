package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {

    private FilmService filmService;

    private FilmController filmController;

    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    void whenRequestFilmListPageThenGetPageWithFilms() {
        var film1 = new FilmDto(1, "test1", "desc1", 2000, 1, 15, "genre", 2);
        var film2 = new FilmDto(2, "test2", "desc2", 2000, 1, 15, "genre", 2);
        List<FilmDto> expectedFilms = List.of(film1, film2);
        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        String view = filmController.getAll(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }

    @Test
    public void whenRequestIdThenGetPageWithFilm() {
        int searchId = 1;
        var film = new FilmDto(1, "test1", "desc1", 2000, 1, 15, "genre", 2);
        when(filmService.findById(searchId)).thenReturn(Optional.of(film));

        var model = new ConcurrentModel();
        String view = filmController.getInfoFilmPage(model, searchId);
        var actualFilm = model.getAttribute("film");

        assertThat(view).isEqualTo("films/info");
        assertThat(actualFilm).isEqualTo(film);
    }

    @Test
    public void whenRequestIdThenGetPageWithError() {
        int notExistID = 2;
        var expectedMessage = "Фильм не найден";
        when(filmService.findById(notExistID)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        String view = filmController.getInfoFilmPage(model, notExistID);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}