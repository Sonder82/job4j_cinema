package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final FileService fileService;

    public SimpleFilmService(FilmRepository filmRepository, FileService fileService) {
        this.filmRepository = filmRepository;
        this.fileService = fileService;
    }

    @Override
    public Film save(Film film, FileDto image) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Film> fileOptional = filmRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        boolean isDeleted = filmRepository.deleteById(id);
        fileService.deleteById(fileOptional.get().getFileId());
        return isDeleted;
    }

    @Override
    public boolean update(Film film, FileDto image) {
        return false;
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<FilmDto> findAll() {
        return null;
    }
}
