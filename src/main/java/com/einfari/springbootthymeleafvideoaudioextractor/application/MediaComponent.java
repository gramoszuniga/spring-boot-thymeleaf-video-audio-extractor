package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.github.kokorin.jaffree.JaffreeException;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.PipeInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class MediaComponent {

    public static final String TEMP_PATH = "temp";
    private final Path FFmpegPath;
    private final Path FFprobePath;

    public String extractAudio(MultipartFile file) {
        try {
            String filename = buildFilename(file, getAudioCodecName(file));
            FFmpeg.atPath(FFmpegPath)
                    .setLogLevel(LogLevel.QUIET)
                    .setOverwriteOutput(true)
                    .addInput(PipeInput.pumpFrom(file.getInputStream()))
                    .addArguments("-map", "0:a:0")
                    .addArguments("-acodec", "copy")
                    .addOutput(UrlOutput.toPath(Path.of(TEMP_PATH, filename)))
                    .execute();
            return filename;
        } catch (IOException | JaffreeException e) {
            log.error(e.getMessage(), e);
            throw new MediaException("Audio extraction failed");
        }
    }

    public String getAudioCodecName(MultipartFile file) {
        try {
            FFprobeResult probeResult = FFprobe.atPath(FFprobePath)
                    .setShowStreams(true)
                    .setSelectStreams(StreamType.AUDIO)
                    .setLogLevel(LogLevel.QUIET)
                    .setInput(file.getInputStream())
                    .execute();
            return probeResult.getStreams().get(0).getCodecName();
        } catch (IOException | JaffreeException e) {
            log.error(e.getMessage(), e);
            throw new MediaException("Audio format could not be identified.");
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