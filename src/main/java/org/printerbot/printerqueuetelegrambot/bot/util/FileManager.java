package org.printerbot.printerqueuetelegrambot.bot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.printerbot.printerqueuetelegrambot.bot.config.BotProperties;
import org.printerbot.printerqueuetelegrambot.model.dto.QueueDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileManager {

	private final BotProperties botProperties;

	@Setter
	@Getter
	private String lastAccessedFilePath;

	public SendDocument getLastAccessedDocument(String chatId) {
		InputStream inputStream;
		InputFile inputFile;
		try {
			inputStream = new FileInputStream(lastAccessedFilePath);
			inputFile = new InputFile(inputStream, extractFileNameFromLocalPath(lastAccessedFilePath));
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return new SendDocument(chatId, inputFile);
	}

	public void deleteFile(String path) {
		Path filePath = Path.of(path);
		try {
			Files.delete(filePath);
			log.info("File on path {} deleted", filePath);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void deleteFile(QueueDto queueDto) {
		if (queueDto == null || queueDto.getStlModelPath() == null) {
			return;
		}
		Path filePath = Path.of(queueDto.getStlModelPath());
		try {
			Files.delete(filePath);
			log.info("File on path {} deleted", filePath);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public String downloadFile(File file, String fileName, String fileExtension) {
		String fileTelegramPath = file.getFileUrl(botProperties.getToken());
		Path serverPath;
		LocalDate currentDate = LocalDate.now();

		try (InputStream in = new URL(fileTelegramPath).openStream()) {
			String uploadPath = createDirectoryIfNotExists(Path.of(botProperties.getStlPath() + currentDate));

			fileName = renameFileIfExists(fileName, fileExtension);
			serverPath = Path.of(uploadPath, fileName);

			Files.copy(in, serverPath, StandardCopyOption.REPLACE_EXISTING);
			log.info("file '{}' downloaded in: {} ",fileName, serverPath);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return serverPath.toString();
	}

	private String renameFileIfExists(String fileName, String extension) {
		String newName = fileName;
		int counter = 1;
		while (Files.exists(Path.of(botProperties.getStlPath(), newName))) {
			newName = new StringBuilder(newName).insert(newName.indexOf(extension), "(" + counter + ")").toString();
			log.info("File '{}' already exists in uploads directory. Renaming file to '{}'", fileName, newName);
			counter++;
		}
		return newName;
	}

	private String extractFileNameFromLocalPath(String localPath) {
		int lastIndexOfSlash = localPath.lastIndexOf("/");
		return localPath.substring(lastIndexOfSlash);
	}

	private String createDirectoryIfNotExists(Path path) {
		try {
			return Files.createDirectory(path).toString();
		} catch (FileAlreadyExistsException e) {
			log.info("Directory {} already exists, {}", path, e.getMessage());
			return path.toString();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
