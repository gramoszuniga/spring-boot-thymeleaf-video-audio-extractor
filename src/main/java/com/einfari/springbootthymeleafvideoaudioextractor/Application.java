package com.einfari.springbootthymeleafvideoaudioextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@SpringBootApplication
public class Application {

    public static final String FFMPEG = "/FFmpeg";
    public static final String FFPROBE = "/FFprobe";

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

}