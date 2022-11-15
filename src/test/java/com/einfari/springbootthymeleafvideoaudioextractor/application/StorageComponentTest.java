package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-15
 **/
class StorageComponentTest {

    private StorageComponent storageComponent;

    @BeforeEach
    void setUp() {
        storageComponent = new StorageComponent();
    }

    @Test
    void canBuildFilename() {
        String expected = "name.m4a";
        String filename = "name.mp4";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(filename, filename, null, "content".getBytes());
        String actual = storageComponent.buildFilename(mockMultipartFile, "aac");
        assertThat(actual).endsWith(expected);
    }

    @Test
    void canGetAudioFileExtensionM4a() {
        String expected = ".m4a";
        String codecName = "aac";
        String actual = storageComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAudioFileExtensionMp3() {
        String expected = ".mp3";
        String codecName = "mp3";
        String actual = storageComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAudioFileExtensionOpus() {
        String expected = ".opus";
        String codecName = "opus";
        String actual = storageComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canGetAudioFileExtensionOgg() {
        String expected = ".ogg";
        String codecName = "vorbis";
        String actual = storageComponent.getAudioFileExtension(codecName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotGetAudioFileExtensionThrows() {
        String codecName = "";
        assertThatThrownBy(() -> storageComponent.getAudioFileExtension(codecName)).isInstanceOf(MediaException.class)
                .hasMessageContaining("Audio format not supported.");
    }

}