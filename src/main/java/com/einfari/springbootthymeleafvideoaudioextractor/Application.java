package com.einfari.springbootthymeleafvideoaudioextractor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@SuppressWarnings("unused")
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${resource.ffmpeg.path}")
    public String FFMPEG;
    @Value("${resource.ffprobe.path}")
    public String FFPROBE;
    @Value("${temp.directory.path}")
    public String TEMP_PATH;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Path FFmpegPath() throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(this.getClass().getResource(FFMPEG)).toURI());
    }

    @Bean
    public Path FFprobePath() throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(this.getClass().getResource(FFPROBE)).toURI());
    }

    @Override
    public void run(String... args) throws IOException {
        Path path = Path.of(TEMP_PATH);
        FileSystemUtils.deleteRecursively(path);
        Files.createDirectories(path);
    }

}