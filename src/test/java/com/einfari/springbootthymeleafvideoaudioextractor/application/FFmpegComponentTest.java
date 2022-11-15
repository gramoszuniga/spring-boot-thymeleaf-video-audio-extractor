package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.github.kokorin.jaffree.JaffreeException;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.Input;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.github.kokorin.jaffree.ffprobe.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-14
 **/
@ExtendWith(MockitoExtension.class)
class FFmpegComponentTest {

    @Mock
    private FFprobeComponent FFprobeComponent;
    @Mock
    private StorageComponent storageComponent;
    @Mock
    private FFmpeg ffmpeg;
    private FFmpegComponent FFmpegComponent;

    @BeforeEach
    void setUp() {
        FFmpegComponent = new FFmpegComponent(FFprobeComponent, storageComponent, ffmpeg);
        ReflectionTestUtils.setField(FFmpegComponent, "TEMP_PATH", "temp");
    }

    @Test
    void canExtractAudio() {
        String filename = "name.mp4";
        List<String> expected = List.of(filename);
        List<Stream> streamList = List.of(new Stream(new MockProbeData()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(filename, filename, null, "content".getBytes());
        when(FFprobeComponent.getAudioStreams(mockMultipartFile)).thenReturn(streamList);
        when(storageComponent.buildFilename(mockMultipartFile, streamList.get(0).getCodecName())).thenReturn(filename);
        when(ffmpeg.setLogLevel(LogLevel.QUIET)).thenReturn(ffmpeg);
        when(ffmpeg.setOverwriteOutput(true)).thenReturn(ffmpeg);
        when(ffmpeg.addInput(any(Input.class))).thenReturn(ffmpeg);
        when(ffmpeg.addArguments("-map", "0:a:" + (streamList.get(0).getIndex() - 1))).thenReturn(ffmpeg);
        when(ffmpeg.addArguments("-acodec", "copy")).thenReturn(ffmpeg);
        when(ffmpeg.addOutput(any(UrlOutput.class))).thenReturn(ffmpeg);
        List<String> actual = FFmpegComponent.extractAudio(mockMultipartFile);
        verify(FFprobeComponent, times(1)).getAudioStreams(eq(mockMultipartFile));
        verify(storageComponent, times(1)).buildFilename(eq(mockMultipartFile), eq("codecName"));
        verify(ffmpeg, times(1)).setLogLevel(eq(LogLevel.QUIET));
        verify(ffmpeg, times(1)).setOverwriteOutput(eq(true));
        verify(ffmpeg, times(1)).addInput(any(Input.class));
        verify(ffmpeg, times(1)).addArguments(eq("-map"), eq("0:a:" + (streamList.get(0).getIndex() - 1)));
        verify(ffmpeg, times(1)).addArguments(eq("-acodec"), eq("copy"));
        verify(ffmpeg, times(1)).addOutput(any(UrlOutput.class));
        verify(ffmpeg, times(1)).execute();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotExtractAudioThrows() {
        String filename = "name.mp4";
        List<Stream> streamList = List.of(new Stream(new MockProbeData()));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(filename, filename, null, "content".getBytes());
        when(FFprobeComponent.getAudioStreams(mockMultipartFile)).thenReturn(streamList);
        when(storageComponent.buildFilename(mockMultipartFile, streamList.get(0).getCodecName())).thenReturn(filename);
        when(ffmpeg.setLogLevel(LogLevel.QUIET)).thenReturn(ffmpeg);
        when(ffmpeg.setOverwriteOutput(true)).thenReturn(ffmpeg);
        when(ffmpeg.addInput(any(Input.class))).thenReturn(ffmpeg);
        when(ffmpeg.addArguments("-map", "0:a:" + (streamList.get(0).getIndex() - 1))).thenReturn(ffmpeg);
        when(ffmpeg.addArguments("-acodec", "copy")).thenReturn(ffmpeg);
        when(ffmpeg.addOutput(any(UrlOutput.class))).thenReturn(ffmpeg);
        when(ffmpeg.execute()).thenThrow(JaffreeException.class);
        assertThatThrownBy(() -> FFmpegComponent.extractAudio(mockMultipartFile)).isInstanceOf(MediaException.class)
                .hasMessageContaining("Audio extraction failed.");
        verify(FFprobeComponent, times(1)).getAudioStreams(eq(mockMultipartFile));
        verify(storageComponent, times(1)).buildFilename(eq(mockMultipartFile), eq("codecName"));
        verify(ffmpeg, times(1)).setLogLevel(eq(LogLevel.QUIET));
        verify(ffmpeg, times(1)).setOverwriteOutput(eq(true));
        verify(ffmpeg, times(1)).addInput(any(Input.class));
        verify(ffmpeg, times(1)).addArguments(eq("-map"), eq("0:a:" + (streamList.get(0).getIndex() - 1)));
        verify(ffmpeg, times(1)).addArguments(eq("-acodec"), eq("copy"));
        verify(ffmpeg, times(1)).addOutput(any(UrlOutput.class));
        verify(ffmpeg, times(1)).execute();
    }

}