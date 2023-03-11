package ru.job4j.cinema.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Класс сервис для работы с файлами
 */
@Service
public class SimpleFileService implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleFileService.class.getName());
    /**
     * поле объект fileRepository
     */
    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        Optional<byte[]> content = Optional.of(readFileAsBytes(fileOptional.get().getPath())).get();
        if (content.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FileDto(fileOptional.get().getName(), content.get()));
    }

    /**
     * Метод выполняет побайтовое чтение из файла.
     *
     * @param path путь файла
     * @return массив байтов
     */
    private Optional<byte[]> readFileAsBytes(String path) {
        try {
            return Optional.of(Files.readAllBytes(Path.of(path)));
        } catch (IOException e) {
            LOG.error("Error message: " + e.getMessage());
        }
        try {
            return Optional.of(Files.readAllBytes(Path.of("files/def_pic")));
        } catch (IOException e) {
            LOG.error("Error message: " + e.getMessage());
        }
        return Optional.empty();
    }
}
