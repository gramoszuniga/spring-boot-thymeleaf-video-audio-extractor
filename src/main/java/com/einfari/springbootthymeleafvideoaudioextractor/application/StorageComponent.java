package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-25
 **/
@RequiredArgsConstructor
@Slf4j
@Component
public class StorageComponent {

    @Value("${temp.directory.path}")
    public String TEMP_PATH;

    public Resource read(String filename) {
        try {
            Resource resource = new UrlResource(Path.of(TEMP_PATH).resolve(filename).toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new StorageException("File is not found");
            }
            return resource;
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            throw new StorageException("File cannot be read.");
        }
    }

}