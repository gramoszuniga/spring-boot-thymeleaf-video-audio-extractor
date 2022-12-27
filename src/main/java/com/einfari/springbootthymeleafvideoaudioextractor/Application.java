package com.einfari.springbootthymeleafvideoaudioextractor;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("unused")
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static final String MAC_OS_PATH = "macos";
    public static final String LINUX_PATH = "linux";
    @Value("${directory.path.ffmpeg}")
    public String FFMPEG;
    @Value("${directory.path.ffprobe}")
    public String FFPROBE;
    @Value("${directory.path.temp}")
    public String TEMP_PATH;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FFmpeg FFmpeg() {
        return FFmpeg.atPath(Path.of(FFMPEG, getOsPath()));
    }

    @Bean
    public FFprobe FFprobe() {
        return FFprobe.atPath(Path.of(FFPROBE, getOsPath()));
    }

    @Override
    public void run(String... args) throws IOException {
        Path path = Path.of(TEMP_PATH);
        FileSystemUtils.deleteRecursively(path);
        Files.createDirectories(path);
    }

    private String getOsPath() {
        if (SystemUtils.IS_OS_MAC) {
            return MAC_OS_PATH;
        }
        if (SystemUtils.IS_OS_LINUX) {
            return LINUX_PATH;
        }
        throw new RuntimeException("Operating system not supported.");
    }

}