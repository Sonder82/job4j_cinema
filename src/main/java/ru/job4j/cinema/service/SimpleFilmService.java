package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
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
        saveNewFile(film, image);
        return filmRepository.save(film);
    }

    private void saveNewFile(Film film, FileDto image) {
        var file = fileService.save(image);
        film.setFileId(file.getId());
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Film> fileOptional = findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        boolean isDeleted = filmRepository.deleteById(id);
        fileService.deleteById(fileOptional.get().getFileId());
        return isDeleted;
    }

    @Override
    public boolean update(Film film, FileDto image) {
        boolean isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return filmRepository.update(film);
        }
        /* если передан новый не пустой файл, то старый удаляем, а новый сохраняем*/
        int oldFileId = film.getFileId();
        saveNewFile(film, image);
        boolean isUpdated = filmRepository.update(film);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Film> findById(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }
}
