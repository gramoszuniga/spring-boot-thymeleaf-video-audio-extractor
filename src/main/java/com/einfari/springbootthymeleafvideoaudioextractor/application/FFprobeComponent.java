package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.github.kokorin.jaffree.JaffreeException;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-15
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class FFprobeComponent {

    private final FFprobe ffprobe;

    public List<Stream> getAudioStreams(MultipartFile file) {
        try {
            FFprobeResult ffprobeResult = ffprobe.setShowStreams(true)
                    .setSelectStreams(StreamType.AUDIO)
                    .setLogLevel(LogLevel.QUIET)
                    .setInput(file.getInputStream())
                    .execute();
            return ffprobeResult.getStreams();
        } catch (IOException | JaffreeException e) {
            log.error(e.getMessage(), e);
            throw new MediaException("Audio format could not be identified.");
        }
    }

}