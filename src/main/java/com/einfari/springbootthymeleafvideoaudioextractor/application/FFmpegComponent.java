package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.github.kokorin.jaffree.JaffreeException;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.PipeInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.github.kokorin.jaffree.ffprobe.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class FFmpegComponent {

    private final FFprobeComponent FFprobeComponent;
    private final StorageComponent storageComponent;
    private final FFmpeg ffmpeg;
    @Value("${directory.path.temp}")
    public String TEMP_PATH;

    public List<String> extractAudio(MultipartFile file) {
        try {
            List<String> filenameList = new ArrayList<>();
            for (Stream stream : FFprobeComponent.getAudioStreams(file)) {
                String filename = storageComponent.buildFilename(file, stream.getCodecName());
                ffmpeg.setLogLevel(LogLevel.QUIET)
                        .setOverwriteOutput(true)
                        .addInput(PipeInput.pumpFrom(file.getInputStream()))
                        .addArguments("-map", "0:a:" + (stream.getIndex() - 1))
                        .addArguments("-acodec", "copy")
                        .addOutput(UrlOutput.toPath(Path.of(TEMP_PATH, filename)))
                        .execute();
                filenameList.add(filename);
            }
            return filenameList;
        } catch (IOException | JaffreeException e) {
            log.error(e.getMessage(), e);
            throw new MediaException("Audio extraction failed.");
        }
    }

}