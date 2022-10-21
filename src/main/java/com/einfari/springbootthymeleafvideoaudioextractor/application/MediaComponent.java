package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@Slf4j
@Component
public class MediaComponent {

    public String getAudioCodecName(MultipartFile file) {
        String codecName = null;
        try {
            URI uri = Objects.requireNonNull(this.getClass().getResource("/FFprobe")).toURI();
            Path path = Paths.get(uri);
            FFprobeResult probeResult = FFprobe.atPath(path)
                    .setShowStreams(true)
                    .setSelectStreams(StreamType.AUDIO)
                    .setLogLevel(LogLevel.QUIET)
                    .setInput(file.getInputStream())
                    .execute();
            codecName = probeResult.getStreams().get(0).getCodecName();
        } catch (NullPointerException | IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
        return codecName;
    }

}