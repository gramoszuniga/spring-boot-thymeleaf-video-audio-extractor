package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.github.kokorin.jaffree.JaffreeException;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-14
 **/
@ExtendWith(MockitoExtension.class)
class MediaComponentTest {

    @Mock
    private FFmpeg ffmpeg;
    @Mock
    private FFprobe ffprobe;
    private MediaComponent mediaComponent;

    @BeforeEach
    void setUp() {
        mediaComponent = new MediaComponent(ffmpeg, ffprobe);
    }

    @Disabled
    @Test
    void canExtractAudio() {

    }

    @Test
    void canGetAudioStreams() {
        FFprobeResult expected = new FFprobeResult(null);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "content".getBytes());
        when(ffprobe.setShowStreams(true)).thenReturn(ffprobe);
        when(ffprobe.setSelectStreams(StreamType.AUDIO)).thenReturn(ffprobe);
        when(ffprobe.setLogLevel(LogLevel.QUIET)).thenReturn(ffprobe);
        when(ffprobe.setInput(any(InputStream.class))).thenReturn(ffprobe);
        when(ffprobe.execute()).thenReturn(expected);
        FFprobeResult actual = mediaComponent.getAudioStreams(mockMultipartFile);
        verify(ffprobe, times(1)).setShowStreams(eq(true));
        verify(ffprobe, times(1)).setSelectStreams(eq(StreamType.AUDIO));
        verify(ffprobe, times(1)).setLogLevel(eq(LogLevel.QUIET));
        verify(ffprobe, times(1)).setInput(any(InputStream.class));
        verify(ffprobe, times(1)).execute();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotGetAudioStreamsThrows() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "content".getBytes());
        when(ffprobe.setShowStreams(true)).thenReturn(ffprobe);
        when(ffprobe.setSelectStreams(StreamType.AUDIO)).thenReturn(ffprobe);
        when(ffprobe.setLogLevel(LogLevel.QUIET)).thenReturn(ffprobe);
        when(ffprobe.setInput(any(InputStream.class))).thenReturn(ffprobe);
        when(ffprobe.execute()).thenThrow(JaffreeException.class);
        assertThatThrownBy(() -> mediaComponent.extractAudio(mockMultipartFile)).isInstanceOf(MediaException.class)
                .hasMessageContaining("Audio format could not be identified.");
        verify(ffprobe, times(1)).setShowStreams(eq(true));
        verify(ffprobe, times(1)).setSelectStreams(eq(StreamType.AUDIO));
        verify(ffprobe, times(1)).setLogLevel(eq(LogLevel.QUIET));
        verify(ffprobe, times(1)).setInput(any(InputStream.class));
        verify(ffprobe, times(1)).execute();
    }

    @Test
    void canBuildFilename() {
        String expected = "name.m4a";
        String filename = "name.mp4";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(filename, filename, null, "content".getBytes());
        String actual = mediaComponent.buildFilename(mockMultipartFile, "aac");
        assertThat(actual).endsWith(expected);
    }

    @Test
    void canGetAudioFileExtensionM4a() {
        String expected = ".m4a";
        String codecName = "aac";
        String actual = mediaComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAudioFileExtensionMp3() {
        String expected = ".mp3";
        String codecName = "mp3";
        String actual = mediaComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAudioFileExtensionOpus() {
        String expected = ".opus";
        String codecName = "opus";
        String actual = mediaComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAudioFileExtensionOgg() {
        String expected = ".ogg";
        String codecName = "vorbis";
        String actual = mediaComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotGetAudioFileExtensionThrows() {
        String codecName = "";
        assertThatThrownBy(() -> mediaComponent.getAudioFileExtension(codecName)).isInstanceOf(MediaException.class)
                .hasMessageContaining("Audio format not supported.");
    }

}