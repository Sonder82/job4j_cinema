package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * Класс сервис для работы с файлами
 */
@ThreadSafe
@Service
public class SimpleFileService implements FileService {

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
        byte[] content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    /**
     * Метод выполняет побайтовое чтение из файла.
     * @param path путь файла
     * @return массив байтов
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
