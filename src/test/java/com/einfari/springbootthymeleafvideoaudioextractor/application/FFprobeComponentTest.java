package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.github.kokorin.jaffree.JaffreeException;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-15
 **/
@ExtendWith(MockitoExtension.class)
class FFprobeComponentTest {

    @Mock
    private FFprobe ffprobe;
    private FFprobeComponent FFprobeComponent;

    @BeforeEach
    void setUp() {
        FFprobeComponent = new FFprobeComponent(ffprobe);
    }

    @Test
    void canGetAudioStreams() {
        List<Stream> expected = new ArrayList<>();
        FFprobeResult ffprobeResult = new FFprobeResult(new MockProbeData());
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "content".getBytes());
        when(ffprobe.setShowStreams(true)).thenReturn(ffprobe);
        when(ffprobe.setSelectStreams(StreamType.AUDIO)).thenReturn(ffprobe);
        when(ffprobe.setLogLevel(LogLevel.QUIET)).thenReturn(ffprobe);
        when(ffprobe.setInput(any(InputStream.class))).thenReturn(ffprobe);
        when(ffprobe.execute()).thenReturn(ffprobeResult);
        List<Stream> actual = FFprobeComponent.getAudioStreams(mockMultipartFile);
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
        assertThatThrownBy(() -> FFprobeComponent.getAudioStreams(mockMultipartFile)).isInstanceOf(MediaException.class)
                .hasMessageContaining("Audio format could not be identified.");
        verify(ffprobe, times(1)).setShowStreams(eq(true));
        verify(ffprobe, times(1)).setSelectStreams(eq(StreamType.AUDIO));
        verify(ffprobe, times(1)).setLogLevel(eq(LogLevel.QUIET));
        verify(ffprobe, times(1)).setInput(any(InputStream.class));
        verify(ffprobe, times(1)).execute();
    }

}