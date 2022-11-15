package com.einfari.springbootthymeleafvideoaudioextractor.application;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.einfari.springbootthymeleafvideoaudioextractor.common.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-15
 **/
class StorageComponentTest {

    public static final String TEMP_PATH = "temp";
    private StorageComponent storageComponent;
    private File file;

    @BeforeEach
    void setUp() throws IOException {
        storageComponent = new StorageComponent();
        ReflectionTestUtils.setField(storageComponent, "TEMP_PATH", TEMP_PATH);
        file = File.createTempFile("tempfile", null, new File(TEMP_PATH));
        file.deleteOnExit();
    }

    @Test
    void canRead() throws MalformedURLException {
        Resource expected = new UrlResource(Path.of(TEMP_PATH).resolve(file.getName()).toUri());
        Resource actual = storageComponent.read(file.getName());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cannotReadThrowsNotFound() {
        assertThatThrownBy(() -> storageComponent.read("∅")).isInstanceOf(StorageException.class)
                .hasMessageContaining("File not found.");
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