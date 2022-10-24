package com.einfari.springbootthymeleafvideoaudioextractor.application;

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
import java.nio.file.Paths;

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

    public String extractAudio(MultipartFile file) throws IOException {
        String filename = "temp";
        String codecName = getAudioCodecName(file);
        String audioFileExtension = getAudioFileExtension(codecName);
        FFmpeg.atPath(FFmpegPath)
                .setLogLevel(LogLevel.QUIET)
                .setOverwriteOutput(true)
                .addInput(PipeInput.pumpFrom(file.getInputStream()))
                .addArguments("-map", "0:a:0")
                .addArguments("-acodec", "copy")
                .addOutput(UrlOutput.toPath(Paths.get(TEMP_PATH, filename + audioFileExtension)))
                .execute();
        return filename + audioFileExtension;
    }

    public String getAudioCodecName(MultipartFile file) throws IOException {
        FFprobeResult probeResult = FFprobe.atPath(FFprobePath)
                .setShowStreams(true)
                .setSelectStreams(StreamType.AUDIO)
                .setLogLevel(LogLevel.QUIET)
                .setInput(file.getInputStream())
                .execute();
        return probeResult.getStreams().get(0).getCodecName();
    }

    public String getAudioFileExtension(String codecName) {
        return switch (codecName) {
            case "aac" -> ".m4a";
            case "mp3" -> ".mp3";
            case "opus" -> ".opus";
            case "vorbis" -> ".ogg";
            default -> throw new IllegalArgumentException("Audio format not supported.");
        };
    }

}