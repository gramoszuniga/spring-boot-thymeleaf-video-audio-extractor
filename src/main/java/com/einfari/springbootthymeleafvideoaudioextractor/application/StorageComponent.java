package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.einfari.springbootthymeleafvideoaudioextractor.common.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-25
 **/
@RequiredArgsConstructor
@Slf4j
@Component
public class StorageComponent {

    @Value("${directory.path.temp}")
    public String TEMP_PATH;

    public Resource read(String filename) {
        try {
            Resource resource = new UrlResource(Path.of(TEMP_PATH).resolve(filename).toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new StorageException("File not found.");
            }
            return resource;
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            throw new StorageException("File cannot be read.");
        }
    }

    public String buildFilename(MultipartFile file, String codecName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Instant.now().toEpochMilli());
        stringBuilder.append("_");
        stringBuilder.append(Path.of(Objects.requireNonNull(file.getOriginalFilename())).getFileName());
        stringBuilder.delete(stringBuilder.lastIndexOf("."), stringBuilder.length());
        stringBuilder.append(getAudioFileExtension(codecName));
        return stringBuilder.toString();
    }

    public String getAudioFileExtension(String codecName) {
        return switch (codecName) {
            case "aac" -> ".m4a";
            case "mp3" -> ".mp3";
            case "opus" -> ".opus";
            case "vorbis" -> ".ogg";
            default -> throw new MediaException("Audio format not supported.");
        };
    }

}